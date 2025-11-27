package com.xperiencehr.timetracking.adapter.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationLogger {

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        log.info("Authentication successful for user: {}", event.getAuthentication().getName());
    }

    @EventListener
    public void onFailure(AuthenticationFailureBadCredentialsEvent event) {
        log.warn("Authentication failed for user: {}", event.getAuthentication().getName());
    }

    @EventListener
    public void onAuthorizationDenied(AuthorizationDeniedEvent event) {
        log.warn("Authorization denied for user: {}", 
                 event.getAuthentication().get().getName());
    }
}
