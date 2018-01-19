package org.seckill.seckill.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillDaoTest {

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void reduceNumber() throws Exception {
       int updateCount = seckillDao.reduceNumber(1000L, new Date());
        System.out.println("updateCount="+updateCount);

    }

    @Test
    public void queryById() throws Exception {
        int id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill);
        Assert.assertNotNull(seckill);
    }

    @Test
    public void queryAll() throws Exception {
        /**
         * nested exception is org.apache.ibatis.binding.BindingException:
         * Parameter 'offset' not found. Available parameters are [arg1, arg0, param1, param2]
         * java 没有保存形参的记录 queryAll(int offset,int limit);
         */

        List<Seckill> seckill = seckillDao.queryAll(0,10);
        Assert.assertNotNull(seckill);

    }

}