package com.medilabo.exception;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
@Slf4j
public class GlobalErrorHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public void handleUnauthorizedException(HttpClientErrorException e, HttpSession session, HttpServletResponse response) throws Exception {
        log.info("ERROR: {} => redirect to login", e.getMessage());
        session.invalidate();
        response.sendRedirect("/login");
    }
}
