package com.xperiencehr.timetracking.adapter.web.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                log.warn("404 error occurred: {}", request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));
                model.addAttribute("message", "The page you're looking for doesn't exist.");
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                log.warn("403 error occurred: {}", request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));
                model.addAttribute("message", "You don't have permission to access this resource.");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                log.error("500 error occurred: {}", request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));
                model.addAttribute("message", "An internal server error occurred.");
            } else {
                log.error("Error occurred with status {}: {}", statusCode, request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));
                model.addAttribute("message", "An error occurred while processing your request.");
            }
        }
        
        if (exception != null) {
            log.error("Exception details", (Throwable) exception);
        }
        
        return "error";
    }
}
