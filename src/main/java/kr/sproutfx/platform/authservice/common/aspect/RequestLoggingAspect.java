package kr.sproutfx.platform.authservice.common.aspect;

import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Joiner;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class RequestLoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private String paramMapToString(Map<String, String[]> paramMap) {
        return paramMap
            .entrySet()
            .stream()
            .map(entry -> String.format("%s: %s", entry.getKey(), Joiner.on(",").join(entry.getValue())))
            .collect(Collectors.joining(", "));
    }
      
    @Pointcut("within(kr.sproutfx.platform.authservice.api.controller..*)")
    public void onRequest() {}

    @Around("kr.sproutfx.platform.authservice.common.aspect.RequestLoggingAspect.onRequest()")
    public Object doLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request =
            ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Map<String, String[]> paramMap = request.getParameterMap();
        String params = "";
        if (paramMap.isEmpty() == false) {
            params = " { " + paramMapToString(paramMap) + " }";
        }

        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed(joinPoint.getArgs());
        } finally {
            long end = System.currentTimeMillis();
            logger.debug("Request: {} {}{} < {} ({}ms)", request.getMethod(), request.getRequestURI(), params, request.getRemoteHost(), end - start);
        } 
    }
}