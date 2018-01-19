package org.seckill.seckill.service;

import org.seckill.seckill.dto.Exposer;
import org.seckill.seckill.dto.SeckillExecution;
import org.seckill.seckill.entity.Seckill;
import org.seckill.seckill.exception.RepeatKillExecption;
import org.seckill.seckill.exception.SeckillCloseExecption;
import org.seckill.seckill.exception.SeckillException;
import sun.rmi.runtime.Log;

import java.util.List;

/**
 * 业务接口：站在使用者角度设计接口
 * 1.方法定义力度。
 * 2.参数。
 * 3.返回类型
 */

public interface SeckillService {

    /**
     * 查询所有的秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启输出秒杀接口地址
     * 否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
        throws SeckillException,RepeatKillExecption,SeckillCloseExecption;

    /**
     * 储存过程执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5);

}
