package com.infostretch.microservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@Aspect
@Component
public class MovieControllerAspect {

    @Around("execution(* com.infostretch.microservice.controller.*.* (..))")
    public Object myAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        final String requestId = UUID.randomUUID().toString();
        Thread currentThread = Thread.currentThread();
        final String currentThreadName = currentThread.getName();
        currentThread.setName(requestId);
        long startTime = System.currentTimeMillis();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> map = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            String value = request.getHeader(header);
            map.put(header, value);
        }
        String url = request.getRequestURI() + (null == request.getQueryString() ? "" : ("?" + request.getQueryString()));

        log.info("Request - RequestId: {}, API: {} {}, Headers: {}, Arguments: {}", requestId, request.getMethod(), url, map, Arrays.toString(joinPoint.getArgs()));
        Object output = joinPoint.proceed();
        log.info("Response - RequestId: {}, Response: {}, Time taken: {}ms", requestId, output, System.currentTimeMillis() - startTime);
        currentThread.setName(currentThreadName);
        return output;
    }

}
