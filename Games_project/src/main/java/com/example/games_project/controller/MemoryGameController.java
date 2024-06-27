package com.example.games_project.controller;

import com.example.games_project.dao.game.GameRepository;
import com.example.games_project.model.entities.Game;
import com.example.games_project.model.entities.ItemAttribute.Result;
import com.example.games_project.security.dao.UserRepository;
import com.example.games_project.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/memory-game")
public class MemoryGameController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @GetMapping
    public String memoryGamePage(Model model, Authentication auth) {
        model.addAttribute("username", auth.getName());
        return "memory_game";
    }

    @PostMapping
    public String memoryGame(Principal principal, @RequestParam String gameResult, @RequestParam Integer gameTime, @RequestParam Integer gameMoves) {
        // Получаем текущего пользователя
        String username = principal.getName();
        User user = userRepository.findByUsername(username);

        // Сохраняем игру в бд
        Game game = new Game();

        Result result;
        switch (gameResult) {
            case "win":
                result = Result.win;
                break;
            case "lose":
                result = Result.lose;
                break;
            case "tie":
                result = Result.tie;
                break;
            default:
                result = null;
        }

        game.setUser(user);
        game.setName("Memory Game");
        game.setResult(result);
        game.setTime(gameTime);
        game.setMoves(gameMoves);
        gameRepository.save(game);

        return "redirect:/memory-game";
    }
}
