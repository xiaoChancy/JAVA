package org.seckill.seckill.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.seckill.seckill.entity.SuccessKilled;

@Mapper
public interface SuccessKilledDao {

    /**
     *  插入购买明细，可过滤重复
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertCuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * 根据id查询successkillen 并携带秒杀产品对象实体
     * @param scckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("scckillId") long scckillId, @Param("userPhone") long userPhone);

}
