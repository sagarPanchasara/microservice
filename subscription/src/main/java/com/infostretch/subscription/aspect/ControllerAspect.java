package com.infostretch.subscription.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ControllerAspect {

    @Around("execution (* com.infostretch.subscription.controller.*.*(..))")
    public Object aspect(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object output = joinPoint.proceed();
        log.info("Request: {}, Time taken {}ms", joinPoint.getSignature().getName(), System.currentTimeMillis() - startTime);
        return output;
    }

}
