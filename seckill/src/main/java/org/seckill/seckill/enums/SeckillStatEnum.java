package org.seckill.seckill.enums;



public enum SeckillStatEnum {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPEAT_KILL(-1,"重复秒杀"),
    INNER_ERROR(-2,"系统异常"),
    DATA_REWRITE(-3,"数据篡改"),

    ;

    private int state;

    private String stagteInfo;

    SeckillStatEnum(int state, String stagteInfo) {
        this.state = state;
        this.stagteInfo = stagteInfo;
    }

    public int getState() {
        return state;
    }

    public String getStagteInfo() {
        return stagteInfo;
    }

    public static SeckillStatEnum stateOf(int index){
        for (SeckillStatEnum state : values()){
            if(state.getState() == index){
                return state;
            }
        }
        return null;
    }


}
