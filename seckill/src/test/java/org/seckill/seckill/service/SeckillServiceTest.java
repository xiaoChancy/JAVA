package org.seckill.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.seckill.dao.SeckillDao;
import org.seckill.seckill.dao.SuccessKilledDao;
import org.seckill.seckill.dto.Exposer;
import org.seckill.seckill.dto.SeckillExecution;
import org.seckill.seckill.entity.Seckill;
import org.seckill.seckill.exception.RepeatKillExecption;
import org.seckill.seckill.exception.SeckillCloseExecption;
import org.seckill.seckill.service.impl.SeckillServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.Executor;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillServiceTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SeckillServiceImpl seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> seckills = seckillService.getSeckillList();
        logger.info("list={}",seckills);
    }

    @Test
    public void getById() throws Exception {

        long id = 1000L;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}",seckill);

    }

    @Test
    public void exportSeckillUrl() throws Exception {

        long id = 1001L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        //是否开启秒杀
        if(exposer.isExposed()){
            logger.info("exposer={}",exposer);
            try {
                long phone = 15386654615L;
                String md5 = exposer.getMd5();
                SeckillExecution seckill = seckillService.executeSeckill(id,phone,md5);
                logger.info("seckill={}",seckill);
            }catch (RepeatKillExecption e){
                logger.error(e.getMessage());
            }catch (SeckillCloseExecption e){
                logger.error(e.getMessage());
            }
        }else{
            //秒杀未开启
            logger.warn("exposer={}",exposer);
        }


    }

    @Test
    public void executeSeckill() throws Exception {


    }

    @Test
    public void executeSeckillProcedure() throws Exception {
        long seckillId = 1003L;
        long phone = 13514555651L;

        Exposer exposer = seckillService.exportSeckillUrl(seckillId);

        if(exposer.isExposed()){
            String md5 = exposer.getMd5();
            SeckillExecution seckillExecution = seckillService.executeSeckillProcedure(seckillId,phone,md5);

            logger.info(seckillExecution.getAtateInfo());

        }

    }

}