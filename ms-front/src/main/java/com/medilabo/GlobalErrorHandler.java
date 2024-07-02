package com.medilabo;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
@Slf4j
public class GlobalErrorHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public void handleUnauthorizedException(HttpClientErrorException e, HttpServletResponse response) throws Exception {
        log.info("ERROR: {} => redirect to login", e.getMessage());
        Cookie deleteServletCookie = new Cookie("token", null);
        deleteServletCookie.setMaxAge(0);
        response.addCookie(deleteServletCookie);
        response.sendRedirect("/login");
    }
}
