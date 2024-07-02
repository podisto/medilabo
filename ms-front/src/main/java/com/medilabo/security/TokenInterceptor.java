package com.medilabo.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        if (request.getServletPath().contains("/login")) {
            log.info("pass thru");
            return true;
        }

        log.info("executing token interceptor");
        String token = (String) request.getSession().getAttribute("token");
        log.info("token retrieved from session = {}", token);

        if (!StringUtils.hasText(token)) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }

/*    private String getCookie(HttpServletRequest request) {
        if (request == null || request.getCookies() == null) {
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("token"))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }*/
}
