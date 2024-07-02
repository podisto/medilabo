package com.medilabo.api;

import com.medilabo.security.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthResource {

    public final AuthService authService;
    private final AuthenticationManager authManager;

    @PostMapping
    public ResponseEntity<AuthResponse> getToken(@RequestBody AuthRequest authRequest) {
        log.info("attempting to generate a token for {}", authRequest.getUsername());
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            String token = authService.generateToken(authRequest.getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
        } else {
            throw new RuntimeException("User invalid access ");
        }
    }

    @GetMapping("/validate")
        public ResponseEntity<Boolean> validateToken(@RequestParam("token") String token) {
        log.info("attempting to validate the token {}", token);
        try {
            boolean result = authService.validateToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.info("token invalid: " +e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(false);
        }
    }
}
