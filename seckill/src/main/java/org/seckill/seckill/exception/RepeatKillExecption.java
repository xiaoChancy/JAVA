package org.seckill.seckill.exception;


/**
 * 重复秒杀
 */
public class RepeatKillExecption extends RuntimeException{

    public RepeatKillExecption(String message) {
        super(message);
    }

    public RepeatKillExecption(String message, Throwable cause) {
        super(message, cause);
    }
}
