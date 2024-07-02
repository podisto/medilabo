package com.medilabo.controller;

import com.medilabo.config.GatewayProperties;
import com.medilabo.dto.LoginForm;
import com.medilabo.dto.AccessToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
@RequestMapping
public class LoginController {

    private final RestTemplate restTemplate;
    private final GatewayProperties properties;

    public LoginController(@Qualifier("restTemplate") RestTemplate restTemplate, GatewayProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    @GetMapping("/login")
    public String login(Model model) {
        log.info("display login page");
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@ModelAttribute LoginForm loginForm, Model model, HttpServletRequest request) {
        log.info("process login for user {}", loginForm.getUsername());
        AccessToken accessToken = restTemplate.postForObject(properties.getAuthUri(), loginForm, AccessToken.class);
        log.info("Access Token retrieved= {}", accessToken);
        HttpSession session = request.getSession();
        assert accessToken != null;
        session.setAttribute("token", accessToken.getToken());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }
}
