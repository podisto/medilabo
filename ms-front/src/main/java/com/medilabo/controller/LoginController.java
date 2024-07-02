package com.medilabo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.medilabo.dto.LoginDetails;
import com.medilabo.service.LoginService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        log.info("display login form");
        final LoginDetails loginDetails = new LoginDetails();
        model.addAttribute("loginDetails", loginDetails);
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid LoginDetails loginDetails, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "login";
        }
        log.info("logging in");
        loginService.connect(loginDetails);
        return "redirect:/index";
    }

}
