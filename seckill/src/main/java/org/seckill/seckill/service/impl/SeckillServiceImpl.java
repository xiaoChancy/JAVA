package org.seckill.seckill.service.impl;

import org.apache.commons.collections.MapUtils;
import org.seckill.seckill.dao.SeckillDao;
import org.seckill.seckill.dao.SuccessKilledDao;
import org.seckill.seckill.dao.cache.RedisDao;
import org.seckill.seckill.dto.Exposer;
import org.seckill.seckill.dto.SeckillExecution;
import org.seckill.seckill.entity.Seckill;
import org.seckill.seckill.entity.SuccessKilled;
import org.seckill.seckill.enums.SeckillStatEnum;
import org.seckill.seckill.exception.RepeatKillExecption;
import org.seckill.seckill.exception.SeckillCloseExecption;
import org.seckill.seckill.exception.SeckillException;
import org.seckill.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String slat = "aliegQ#$^#%&%$&";

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao redisDao;

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,4);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        //优化点：缓存优化
        //利用Redis优化数据库操作
        //先从Redis缓存中获取seckill对象
        Seckill seckill = redisDao.getSeckill(seckillId);
        if(seckill == null) {
            //如果Redis中没有，则从数据库中获取seckill对象
            seckill = seckillDao.queryById(seckillId);
            if(seckill == null) {
                //如果数据库中也没有，则说明没有该商品信息，返回false
                return new Exposer(false, seckillId);
            }else {
                //如果数据库中有该商品信息，将该信息存入Redis，以便用户刷新页面后直接从Redis中获取。
                String result = redisDao.putSeckill(seckill);   //返回OK
            }
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date noewTime = new Date();

        //判断秒杀未开启和已结束
        if(noewTime.getTime() < startTime.getTime() || noewTime.getTime() > endTime.getTime()){
            return new Exposer(false,seckillId,noewTime.getTime(),startTime.getTime(),endTime.getTime());
        }

        String md5 = getMd5(seckillId);
        return new Exposer(true,md5,seckillId);
    }

    private String getMd5(long seckillId){
        String base = seckillId + "/" + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    @Override
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillExecption, SeckillCloseExecption {

        if(md5 == null || !md5.equals(getMd5(seckillId)) ){
            throw  new SeckillException("秒杀不生效，数据被篡改");
        }
//        执行秒杀逻辑
        Date nowTime = new Date();
        try {
            //记录购买行为
            int insertCount = successKilledDao.insertCuccessKilled(seckillId,userPhone);
            if(insertCount <= 0){
                throw new RepeatKillExecption("重复秒杀");
            }else{
                //减库存
                int updateCount = seckillDao.reduceNumber(seckillId,nowTime);
                if(updateCount <= 0){
                    //没有更新到记录，秒杀结束 ， rollback
                    throw  new SeckillCloseExecption("没有更新到记录");
                }else {
                    //秒杀成功 ， commit
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS,successKilled);
                }
            }
        } catch (SeckillCloseExecption e1){
            throw e1;
        } catch (RepeatKillExecption e2){
            throw e2;
        } catch (Exception e){
            logger.error(e.getMessage(),e);
            throw  new SeckillException("seckill inner error " + e.getMessage());
        }
    }

    @Override
    public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5) {
        if(md5 == null || !md5.equals(getMd5(seckillId)) ){
            return new SeckillExecution(seckillId,SeckillStatEnum.DATA_REWRITE);
        }
        Date killTime = new Date();

        Map<String ,Object> map = new HashMap<String ,Object>();
        map.put("seckillId",seckillId);
        map.put("phone",userPhone);
        map.put("killTime",killTime);
        map.put("result",null);

        try {
            seckillDao.killByProcedure(map);
            int result = MapUtils.getInteger(map,"result",-2);
            if(result == 1){
                SuccessKilled sk = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                return new SeckillExecution(seckillId,SeckillStatEnum.SUCCESS,sk);
            }else {
                return new SeckillExecution(seckillId,SeckillStatEnum.stateOf(result));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new SeckillExecution(seckillId,SeckillStatEnum.INNER_ERROR);
        }
    }
}
