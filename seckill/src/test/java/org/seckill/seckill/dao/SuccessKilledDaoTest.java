package org.seckill.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.seckill.entity.SuccessKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SuccessKilledDaoTest {

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertCuccessKilled() throws Exception {

        long id = 1001L;
        long phone = 153866546115L;
        int insert = successKilledDao.insertCuccessKilled(id,phone);
        System.out.println(insert);

    }

    @Test
    public void queryByIdWithSeckill() throws Exception {

        long id = 1001L;
        long phone = 153866546115L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id,phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());

    }

}