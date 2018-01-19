package org.seckill.seckill.dto;


import org.seckill.seckill.entity.SuccessKilled;
import org.seckill.seckill.enums.SeckillStatEnum;

/**
 * 封装秒杀执行后的结果
 */
public class SeckillExecution {

    //id
    private long seckillId;

    // 秒杀执行状态
    private int state;

    //状态表示
    private String atateInfo;

    //秒杀成功对象
    private SuccessKilled successKilled;

    public SeckillExecution(long seckillId, SeckillStatEnum statEnum, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = statEnum.getState();
        this.atateInfo = statEnum.getStagteInfo();
        this.successKilled = successKilled;
    }

    public SeckillExecution(long seckillId, SeckillStatEnum statEnum) {
        this.seckillId = seckillId;
        this.state = statEnum.getState();
        this.atateInfo = statEnum.getStagteInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getAtateInfo() {
        return atateInfo;
    }

    public void setAtateInfo(String atateInfo) {
        this.atateInfo = atateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", atateInfo='" + atateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }
}
