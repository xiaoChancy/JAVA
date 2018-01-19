package com.jyhd.black.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component  //表示把这个文件引入到srping容器里
public class HttpAspect {

    //日志
    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    //表示会先执行下面的方法在执行注解里的东西 * 表示拦截类里所有方法
    @Pointcut("execution(public * com.jyhd.black.controller.*.*(..))")
    public void log() { }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint)
    {
        logger.info("开始拦截");
        //记录日志
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder .getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//
//        //url
//        logger.info("url={}",request.getRequestURI());
//        //method
//        logger.info("method={}",request.getMethod());
//        //ip
//        logger.info("id={}",request.getRemoteAddr());
//        //类方法
//        logger.info("class_method={}",joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
//        //参数
//        logger.info("args={}",joinPoint.getArgs());
    }

    //表示会后执行下面的方法
    @After("log()")
    public void doAfter()
    {
        logger.info("结束拦截");
    }

    //接收到控制器返回的值
//    @AfterReturning(returning = "object",pointcut = "log()")
//    public void doAfterReturning(Object object)
//    {
//        logger.info("response={}",object.toString());
//    }
}
