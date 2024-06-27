package com.example.games_project.controller;

import com.example.games_project.dao.game.GameRepository;
import com.example.games_project.dao.game.GameService;
import com.example.games_project.model.entities.Game;
import com.example.games_project.model.entities.ItemAttribute.Result;
import com.example.games_project.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public String profilePage(Model model, Authentication auth) {
        Integer userId = ((User) auth.getPrincipal()).getId();

        int winsTicTacToe = gameService.numbersOfResults("Tic Tac Toe", userId, Result.win);
        int lossesTicTacToe = gameService.numbersOfResults("Tic Tac Toe", userId, Result.lose);
        int drawsTicTacToe = gameService.numbersOfResults("Tic Tac Toe", userId, Result.tie);

        int winsMemory = gameService.numbersOfResults("Memory Game", userId, Result.win);
        int lossesMemory = gameService.numbersOfResults("Memory Game", userId, Result.lose);
        int bestTime = gameService.findMinTime("Memory Game", userId);
        int bestMoves = gameService.findMinMoves("Memory Game", userId);

        int winsHangman = gameService.numbersOfResults("Hangman", userId, Result.win);
        int lossesHangman = gameService.numbersOfResults("Hangman", userId, Result.lose);

        model.addAttribute("username", auth.getName());

        model.addAttribute("winsTicTacToe", winsTicTacToe);
        model.addAttribute("lossesTicTacToe", lossesTicTacToe);
        model.addAttribute("drawsTicTacToe", drawsTicTacToe);

        model.addAttribute("winsMemory", winsMemory);
        model.addAttribute("lossesMemory", lossesMemory);
        model.addAttribute("bestTime", bestTime);
        model.addAttribute("bestMoves", bestMoves);

        model.addAttribute("winsHangman", winsHangman);
        model.addAttribute("lossesHangman", lossesHangman);

        return "profile";
    }
}
