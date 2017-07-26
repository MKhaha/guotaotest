package com.mmall.aop;

import com.mmall.common.ServerResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * Created by Administrator on 2017-7-26.
 */
@Aspect
@Component
public class TimeConsumingAspectj {

    Logger logger = LoggerFactory.getLogger(LogAspectj.class);


    @Pointcut("execution(* com.mmall.controller.*Controller.*(..))")
    public void pointcut(){}

    @Around("pointcut()")
    public Object methodInvokeExpiredTime(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 开始
        Object retVal = pjp.proceed();
        stopWatch.stop();
        // 结束
        logger.warn("方法:"+ methodName +" 耗时:"+ stopWatch.getTotalTimeMillis());
        return retVal;
    }
}
