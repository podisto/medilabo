package com.medilabo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.medilabo.GatewayUriProperties;
import com.medilabo.dto.LoginDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final RestTemplate restTemplate;

    private final GatewayUriProperties properties;

    public void connect(LoginDetails loginDetails) {
        restTemplate.postForObject(properties.getAuthUri(), loginDetails, String.class);

    }

}
