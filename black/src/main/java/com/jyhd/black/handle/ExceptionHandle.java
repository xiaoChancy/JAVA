package com.jyhd.black.handle;

import com.jyhd.black.aspect.HttpAspect;
import com.jyhd.black.domain.Result;
import com.jyhd.black.exception.BlackExcption;
import com.jyhd.black.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle {

    //日志
    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    //声明要捕获Exception这个类
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e)
    {
        if(e instanceof BlackExcption){
            BlackExcption excption = (BlackExcption)e;
            return ResultUtil.error(excption.getCode(),excption.getMessage());
        }else{
            logger.error("【系统异常】",e);
            return ResultUtil.error(-1,e.getMessage());
        }
    }

}
