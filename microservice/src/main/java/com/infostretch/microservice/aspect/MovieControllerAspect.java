package com.infostretch.microservice.aspect;

import com.infostretch.microservice.client.SubscriptionClient;
import com.infostretch.microservice.view.ErrorResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class MovieControllerAspect {

    @NonNull
    private SubscriptionClient subscriptionClient;

    @Around("execution(* com.infostretch.microservice.controller.*.* (..))")
    public Object myAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        final String requestId = UUID.randomUUID().toString();
//        Thread currentThread = Thread.currentThread();
//        final String currentThreadName = currentThread.getName();
//        currentThread.setName(requestId);
        long startTime = System.currentTimeMillis();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            String value = request.getHeader(header);
            headers.put(header.toLowerCase(), value);
        }
        String url = request.getRequestURI() + (null == request.getQueryString() ? "" : ("?" + request.getQueryString()));

        log.info("Request - RequestId: {}, API: {} {}, Headers: {}, Arguments: {}", requestId, request.getMethod(), url, headers, Arrays.toString(joinPoint.getArgs()));
        boolean isAllowedToProceed = true;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        if (methodSignature.getMethod().isAnnotationPresent(PremiumContent.class)) {
            if (headers.containsKey("authorization")) {
                String userId = headers.get("authorization");
                isAllowedToProceed = subscriptionClient.isValidSubscriber(userId);
            } else {
                isAllowedToProceed = false;
            }
        }
        Object output;
        if (isAllowedToProceed) {
            output = joinPoint.proceed();
        } else {
            output = ErrorResponse.unauthorized().toResponseEntity();
        }
        log.info("Response - RequestId: {}, Response: {}, Time taken: {}ms", requestId, output, System.currentTimeMillis() - startTime);
//        currentThread.setName(currentThreadName);
        return output;
    }

}
