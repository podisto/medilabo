package com.medilabo;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
@RequestMapping("login")
public class LoginController {

    private final RestTemplate restTemplate;
    private final TokenContextHolder tokenContextHolder;

    @Value("${auth-uri}")
    private String authUri;

    public LoginController(@Qualifier("restTemplate") RestTemplate restTemplate, TokenContextHolder tokenContextHolder) {
        this.restTemplate = restTemplate;
        this.tokenContextHolder = tokenContextHolder;
    }

    @GetMapping
    public String login(Model model) {
        log.info("display login page");
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping
    public String doLogin(@ModelAttribute LoginForm loginForm, Model model, HttpServletResponse response) {
        log.info("process login for user {}", loginForm.getUsername());
        AccessToken token = restTemplate.postForObject(authUri, loginForm, AccessToken.class);
        log.info("### TOKEN {}", token);
        tokenContextHolder.setToken(token.getToken());
        Cookie cookie = new Cookie("token", token.getToken());
        cookie.setMaxAge(86400);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
