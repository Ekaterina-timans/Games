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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/tic-tac-toe")
public class TicTacToeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @GetMapping
    public String ticTacToePage(Model model, Authentication auth) {
        model.addAttribute("username", auth.getName());
        return "tic_tac_toe";
    }

    @PostMapping
    public String ticTacToe(Principal principal, @RequestParam String gameResult) {
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
        game.setName("Tic Tac Toe");
        game.setResult(result);
        gameRepository.save(game);

        return "redirect:/tic-tac-toe";
    }

}
