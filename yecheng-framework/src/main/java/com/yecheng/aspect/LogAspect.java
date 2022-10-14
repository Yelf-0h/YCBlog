package com.yecheng.aspect;

import com.alibaba.fastjson.JSON;
import com.yecheng.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Yelf
 * @create 2022-10-14-19:23
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

//   可以先定义切点然后再方法上使用 @Around("pt()") 进行绑定 也可以像我下面绑定切点的方法绑定
//    @Pointcut("@annotation(com.yecheng.annotation.SystemLog)")
//    public void pt(){}

    @Around("@annotation(systemLog)")
    public Object printLog(ProceedingJoinPoint joinPoint, SystemLog systemLog) throws Throwable {
        Object proceed;
        try {
            handleBefore(joinPoint,systemLog);
            proceed = joinPoint.proceed();
            handleAfter(proceed);

        } finally {
            // 结束后换行
            log.info("==============End==============" + System.lineSeparator());
        }
        return proceed;
    }



    private void handleBefore(ProceedingJoinPoint joinPoint, SystemLog systemLog) {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        log.info("==============Start==============");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}",systemLog.BusinessName());
        // 打印 Http method
        log.info("HTTP Method    : {}",request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}",joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName());
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteAddr());
        // 打印请求入参
        log.info("Request Args   : {}",JSON.toJSONString(joinPoint.getArgs()));

    }

    private void handleAfter(Object proceed) {
        // 打印出参
        log.info("Response       : {}",JSON.toJSONString(proceed));
    }
}
