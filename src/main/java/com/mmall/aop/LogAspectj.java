package com.mmall.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017-7-26.
 */

@Component
@Aspect
public class LogAspectj {

    Logger logger = LoggerFactory.getLogger(LogAspectj.class);


    @Pointcut("execution(* com.mmall.controller.*Controller.*(..))")
    public void pointcut(){}


    @Before("pointcut()")
    public void BeforeMethod(JoinPoint point){
        String methodName = point.getSignature().getName();//调用的接口名称
        Object[] objects = point.getArgs();
        String args = "";
        for (Object o :objects){
            args  += o.toString()+";   ";
        }
        logger.warn("进入方法:"+ methodName +"  传入参数为:"+ args);
    }

    @AfterReturning(pointcut = "pointcut()",returning = "result")
    public void AfterReturningMethod(JoinPoint point,Object result){
        String methodName = point.getSignature().getName();//调用的接口名称
        Object[] objects = point.getArgs();
        String args = "";
        for (Object o :objects){
            args  += o.toString()+";   ";
        }
        logger.warn("退出方法:"+ methodName +"  返回值为:"+ result);
    }

    @AfterThrowing(pointcut = "pointcut()",throwing = "ex")
    public void AfterThrowingMethod(JoinPoint point,Exception ex){
        String methodName = point.getSignature().getName();
        logger.warn("方法:"+ methodName +"  抛出"+ ex +"异常");
    }

}
