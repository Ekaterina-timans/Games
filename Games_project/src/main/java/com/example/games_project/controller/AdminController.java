package com.example.games_project.controller;

import com.example.games_project.dao.game.GameRepository;
import com.example.games_project.dao.user.UserService;
import com.example.games_project.model.entities.Game;
import com.example.games_project.security.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @Autowired
    private GameRepository gameRepository;

    @GetMapping
    public String adminPage(Model model, @RequestParam(value = "searchUsername", required = false) String searchUsername) {
        List<String> usernames;
        if (searchUsername != null && !searchUsername.isEmpty()) {
            usernames = userService.getUsernamesByPartOfName(searchUsername);
        } else {
            usernames = userService.getAllUsernames();
        }

        int totalUsers = usernames.size();
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("usernames", usernames);
        return "admin";
    }

    @GetMapping("/search")
    public String searchByUsername(Model model, @RequestParam(value = "searchUsername", required = false) String searchUsername) {
        List<String> usernames = userService.getUsernamesByPartOfName(searchUsername);
        model.addAttribute("usernames", usernames);
        return "admin";
    }

    @PostMapping("/delete/{username}")
    @Transactional
    public String deleteUser(@PathVariable String username) {
        userService.deleteByUsername(username);
        return "redirect:/admin";
    }
}
