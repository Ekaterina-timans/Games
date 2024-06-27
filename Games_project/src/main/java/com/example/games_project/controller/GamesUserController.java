package com.example.games_project.controller;

import com.example.games_project.dao.game.GameRepository;
import com.example.games_project.dao.game.GameService;
import com.example.games_project.dao.user.UserService;
import com.example.games_project.model.entities.Game;
import com.example.games_project.security.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/games")
@RequiredArgsConstructor
public class GamesUserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private GameService gameService;

    private final GameRepository gameRepository;

    @GetMapping
    public String showGamesForUser(@RequestParam("username") String username, Model model) {
        User user = userService.findByUsername(username);
        if (user != null) {
            List<Game> games = gameRepository.findByUser(user);
            model.addAttribute("games", games);
            model.addAttribute("username", username);

            int totalGames = games.size();
            model.addAttribute("totalGames", totalGames);

            return "games";
        } else {
            return "error";
        }
    }

    @GetMapping("/byGame")
    public String showGamesForUserByGame(@RequestParam("username") String username, @RequestParam("game") String gameName, Model model) {
        User user = userService.findByUsername(username);
        if (user != null) {
            List<Game> games = gameRepository.findByUserAndName(user, gameName);
            model.addAttribute("games", games);
            model.addAttribute("username", username);
            int totalGames = games.size();
            model.addAttribute("totalGames", totalGames);
            return "games";
        } else {
            return "error";
        }
    }


    @PostMapping("/delete")
    public String deleteGame(@RequestParam("id") int id, @RequestParam("username") String username, Model model) {
        gameService.deleteGameById(id);
        return showGamesForUser(username, model);
    }
}
