package com.example.beadando.controller;

import com.example.beadando.entity.User;
import com.example.beadando.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Bejelentkezés oldal
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Regisztráció oldal
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Regisztráció feldolgozása
    @PostMapping("/register")
    public String registerSubmit(User user) {
        // Jelszó titkosítása és mentés
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_VISITOR"); // Alapértelmezett szerepkör
        userRepository.save(user);
        return "redirect:/login?success";
    }
}