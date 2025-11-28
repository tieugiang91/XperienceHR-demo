package com.xperiencehr.timetracking.adapter.web.exception;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDenied(AccessDeniedException ex) {
        log.error("Access denied: {}", ex.getMessage());
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", "You don't have permission to access this resource.");
        mav.setStatus(HttpStatus.FORBIDDEN);
        return mav;
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public void handleMissingResource(NoResourceFoundException ex, HttpServletResponse response) {
        log.debug("Static resource not found: {}", ex.getResourcePath());
        response.setStatus(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(Exception ex) {
        log.error("Unhandled exception occurred", ex);
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", "An unexpected error occurred. Please contact support if the problem persists.");
        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return mav;
    }
}
