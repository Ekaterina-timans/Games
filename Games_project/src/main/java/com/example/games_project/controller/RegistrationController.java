package com.example.games_project.controller;

import com.example.games_project.dao.user.UserService;
import com.example.games_project.security.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;
    private final UserDetailsService userDetailsService;

    @GetMapping
    public String registrationPage() {
        return "registration";
    }

    @PostMapping
    public String register(@RequestParam String username, @RequestParam String password, Model model) {
        User registered = userService.save(new User(username, password));

        if (registered == null) {
            model.addAttribute("error", true);
            model.addAttribute("type", "username");
            return "registration";
        }
        else {
            UserDetails user = userDetailsService.loadUserByUsername(username);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities())
            );
            return "redirect:/profile";
        }


    }
}
