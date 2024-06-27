package com.example.games_project.controller;

import com.example.games_project.dao.game.GameService;
import com.example.games_project.model.entities.ItemAttribute.Result;
import com.example.games_project.security.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class ResultsController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public String resultsPage(Model model) {
        DecimalFormat df = new DecimalFormat("#.##");

        // Tic Tac Toe
        Map<User, Integer> userWinsTicTacToe = gameService.listOfResult("Tic Tac Toe");
        List<String> usersTicTacToe = new ArrayList<>();
        List<Integer> winsTicTacToe = new ArrayList<>();
        if (!userWinsTicTacToe.isEmpty()) {
            for (Map.Entry<User, Integer> entry : userWinsTicTacToe.entrySet()) {
                User user = entry.getKey();
                Integer userWins = entry.getValue();
                usersTicTacToe.add(user.getUsername());
                winsTicTacToe.add(userWins);
            }
        }
        model.addAttribute("usersTicTacToe", usersTicTacToe);
        model.addAttribute("winsTicTacToe", winsTicTacToe);

        List<Integer> totalResultsTicTacToe = new ArrayList<>();
        int totalGamesTicTacToe = 0;
        if (!userWinsTicTacToe.isEmpty()) {
            for (Map.Entry<User, Integer> entry : userWinsTicTacToe.entrySet()) {
                User user = entry.getKey();
                Integer userWins = entry.getValue();
                int userTotalResults = gameService.numbersOfResults("Tic Tac Toe", user.getId(), Result.win) +
                        gameService.numbersOfResults("Tic Tac Toe", user.getId(), Result.lose) +
                        gameService.numbersOfResults("Tic Tac Toe", user.getId(), Result.tie);
                totalGamesTicTacToe += userTotalResults;
                totalResultsTicTacToe.add(userTotalResults);
            }
        }
        List<Double> winPercentagesTicTacToe = new ArrayList<>();
        for (int i = 0; i < totalResultsTicTacToe.size(); i++) {
            double winPercentTicTacToe = (double) winsTicTacToe.get(i) / totalResultsTicTacToe.get(i) * 100;
            String formattedWinPercentTicTacToe = df.format(winPercentTicTacToe).replace(",", ".");
            winPercentagesTicTacToe.add(Double.parseDouble(formattedWinPercentTicTacToe));
        }
        model.addAttribute("winPercentagesTicTacToe", winPercentagesTicTacToe);

        // Memory Game
        Map<User, Integer> userWinsMemory = gameService.listOfResult("Memory Game");
        List<String> usersMemory = new ArrayList<>();
        List<Integer> winsMemory = new ArrayList<>();
        if (!userWinsMemory.isEmpty()) {
            for (Map.Entry<User, Integer> entry : userWinsMemory.entrySet()) {
                User user = entry.getKey();
                Integer userWins = entry.getValue();
                usersMemory.add(user.getUsername());
                winsMemory.add(userWins);
            }
        }
        model.addAttribute("usersMemory", usersMemory);
        model.addAttribute("winsMemory", winsMemory);

        Map<User, Integer> userBestTime = gameService.listOfMinTime("Memory Game");
        List<String> usersBestTime = new ArrayList<>();
        List<Integer> times = new ArrayList<>();
        if (!userBestTime.isEmpty()) {
            for (Map.Entry<User, Integer> entry : userBestTime.entrySet()) {
                User user = entry.getKey();
                Integer userTimes = entry.getValue();
                usersBestTime.add(user.getUsername());
                times.add(userTimes);
            }
        }
        model.addAttribute("usersBestTime", usersBestTime);
        model.addAttribute("times", times);

        Map<User, Integer> userBestMoves = gameService.listOfMinMoves("Memory Game");
        List<String> usersBestMoves = new ArrayList<>();
        List<Integer> moves = new ArrayList<>();
        if (!userBestMoves.isEmpty()) {
            for (Map.Entry<User, Integer> entry : userBestMoves.entrySet()) {
                User user = entry.getKey();
                Integer userMoves = entry.getValue();
                usersBestMoves.add(user.getUsername());
                moves.add(userMoves);
            }
        }
        model.addAttribute("usersBestMoves", usersBestMoves);
        model.addAttribute("moves", moves);

        List<Integer> totalResultsMemory = new ArrayList<>();
        int totalGamesMemory = 0;
        if (!userWinsMemory.isEmpty()) {
            for (Map.Entry<User, Integer> entry : userWinsMemory.entrySet()) {
                User user = entry.getKey();
                Integer userWins = entry.getValue();
                int userTotalResults = gameService.numbersOfResults("Memory Game", user.getId(), Result.win) +
                        gameService.numbersOfResults("Memory Game", user.getId(), Result.lose);
                totalGamesMemory += userTotalResults;
                totalResultsMemory.add(userTotalResults);
            }
        }
        List<Double> winPercentagesMemory = new ArrayList<>();
        for (int i = 0; i < totalResultsMemory.size(); i++) {
            double winPercentMemory = (double) winsMemory.get(i) / totalResultsMemory.get(i) * 100;
            String formattedWinPercentMemory = df.format(winPercentMemory).replace(",", ".");
            winPercentagesMemory.add(Double.parseDouble(formattedWinPercentMemory));
        }
        model.addAttribute("winPercentagesMemory", winPercentagesMemory);

        // Hangman
        Map<User, Integer> userWinsHangman = gameService.listOfResult("Hangman");
        List<String> usersHangman = new ArrayList<>();
        List<Integer> winsHangman = new ArrayList<>();
        if (!userWinsHangman.isEmpty()) {
            for (Map.Entry<User, Integer> entry : userWinsHangman.entrySet()) {
                User user = entry.getKey();
                Integer userWins = entry.getValue();
                usersHangman.add(user.getUsername());
                winsHangman.add(userWins);
            }
        }
        model.addAttribute("usersHangman", usersHangman);
        model.addAttribute("winsHangman", winsHangman);

        List<Integer> totalResultsHangman = new ArrayList<>();
        int totalGamesHangman = 0;
        if (!userWinsHangman.isEmpty()) {
            for (Map.Entry<User, Integer> entry : userWinsHangman.entrySet()) {
                User user = entry.getKey();
                Integer userWins = entry.getValue();
                int userTotalResults = gameService.numbersOfResults("Hangman", user.getId(), Result.win) +
                        gameService.numbersOfResults("Hangman", user.getId(), Result.lose);
                totalGamesHangman += userTotalResults;
                totalResultsHangman.add(userTotalResults);
            }
        }
        List<Double> winPercentagesHangman = new ArrayList<>();
        for (int i = 0; i < totalResultsHangman.size(); i++) {
            double winPercentHangman = (double) winsHangman.get(i) / totalResultsHangman.get(i) * 100;
            String formattedWinPercentHangman = df.format(winPercentHangman).replace(",", ".");
            winPercentagesHangman.add(Double.parseDouble(formattedWinPercentHangman));
        }
        model.addAttribute("winPercentagesHangman", winPercentagesHangman);

        return "index";
    }
}
