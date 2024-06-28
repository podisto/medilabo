package com.medilabo.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;

    public String generateToken(String username) {
        log.info("generating token");
        return jwtService.generateToken(username);
    }

    public boolean validateToken(String token) {
        log.info("validate token");
        return jwtService.validateToken(token);
    }
}
