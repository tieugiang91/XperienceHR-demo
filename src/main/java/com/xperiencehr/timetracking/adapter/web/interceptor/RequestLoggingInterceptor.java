package com.xperiencehr.timetracking.adapter.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class RequestLoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        
        log.info("HTTP Request started: {} {} from {}", 
                 request.getMethod(), 
                 request.getRequestURI(),
                 request.getRemoteAddr());
        
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                                Object handler, Exception ex) {
        Long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        
        log.info("HTTP Request completed: {} {} - Status: {} - Duration: {} ms",
                 request.getMethod(),
                 request.getRequestURI(),
                 response.getStatus(),
                 executionTime);
        
        if (ex != null) {
            log.error("Request completed with exception", ex);
        }
    }
}
