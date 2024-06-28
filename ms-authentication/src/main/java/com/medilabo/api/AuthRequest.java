package com.medilabo.api;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
