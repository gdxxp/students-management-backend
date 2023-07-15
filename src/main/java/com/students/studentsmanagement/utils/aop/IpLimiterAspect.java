package com.students.studentsmanagement.utils.aop;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.RateLimiter;
import com.students.studentsmanagement.constant.ResponseCodes;
import com.students.studentsmanagement.exception.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;

@Order(5)
@Aspect
@RestController
public class IpLimiterAspect {

    private volatile double DEFAULT_LIMITER_COUNT_PER_SECOND = 0.5;

    Cache<String, RateLimiter> limiterCatch = CacheBuilder.newBuilder().expireAfterAccess(Duration.ofMinutes(10)).build();

    @Resource
    private HttpServletRequest httpServletRequest;

    @Around("execution(* com.students.studentsmanagement.controller..*.*(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // nginx提供ip
        String ip = httpServletRequest.getHeader("X-Real-IP");

        Signature signature = proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String methodName = proceedingJoinPoint.getTarget().getClass().getName() + "." + methodSignature.getName();
        String recordKey = ip + "->" + methodName;
        // 此处不需要考虑并发get，底层已lock创建
        RateLimiter rateLimiter = limiterCatch.get(recordKey, () -> RateLimiter.create(DEFAULT_LIMITER_COUNT_PER_SECOND));

        if (! rateLimiter.tryAcquire()) {
            throw new BusinessException(ResponseCodes.FAIL, "操作过快！");
        }
        return proceedingJoinPoint.proceed();
    }
}
