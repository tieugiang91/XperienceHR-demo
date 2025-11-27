package com.xperiencehr.timetracking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TimeTrackingApplication extends SpringBootServletInitializer {

    private static final Logger log = LoggerFactory.getLogger(TimeTrackingApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TimeTrackingApplication.class);
    }

    public static void main(String[] args) {
        log.info("Starting XperienceHR Time Tracking Application");
        SpringApplication.run(TimeTrackingApplication.class, args);
        log.info("Application started successfully");
    }
}
