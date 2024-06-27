package com.example.games_project.dao.game;

import com.example.games_project.model.entities.Game;
import com.example.games_project.model.entities.ItemAttribute.Result;
import com.example.games_project.security.entity.User;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameServiceImplementation implements GameService {
    private final GameRepository repo;
    @Override
    public Game save(Game game) {
        try {
            return repo.save(game);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public List<Game> findByUser(User user) {
        return repo.findByUser(user);
    }
    @Override
    public void deleteGameById(int id) {
        Game game = repo.findById(id);
        if (game != null) {
            repo.delete(game);
        }
    }

    @Override
    public int numbersOfResults(String name, int id, Result result) {
        Integer userResults = repo.countFindAllByNameAndUser_IdAndResult(name, id, result);
        return userResults;
    }
    @Override
    public int findMinTime(String name, int id) {
        List<Game> userGames = repo.findAllByNameAndUser_Id(name, id);
        if (userGames.isEmpty()) {
            return 0;
        }
        // Нахождение минимального значения time
        int minTime = userGames.stream()
                .mapToInt(Game::getTime)
                .min()
                .getAsInt();

        return minTime;
    }
    @Override
    public int findMinMoves(String name, int id) {
        List<Game> userGames = repo.findAllByNameAndUser_Id(name, id);
        if (userGames.isEmpty()) {
            return 0;
        }
        int minMoves = userGames.stream()
                .mapToInt(Game::getMoves)
                .min()
                .getAsInt();

        return minMoves;
    }
    @Override
    public Map<User, Integer> listOfResult(String name) {
        // Находим все игры с указанным наименованием
        List<Game> games = repo.findAllByName(name);
        // Создаем мапу для хранения пользователей и количества их побед
        Map<User, Integer> userWinsCountMap = new HashMap<>();
        // Перебираем все игры и считаем победы для каждого пользователя
        for (Game game : games) {
            User user = game.getUser();
            if (game.getResult() == Result.win) {
                userWinsCountMap.put(user, userWinsCountMap.getOrDefault(user, 0) + 1);
            }
        }
        // Сортируем мапу по количеству побед
        Map<User, Integer> sortedUserWinsCountMap = userWinsCountMap.entrySet()
                .stream()
                .sorted(Map.Entry.<User, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return sortedUserWinsCountMap;
    }
    @Override
    public Map<User, Integer> listOfMinTime(String name) {
        List<Game> games = repo.findAllByName(name);
        Map<User, Integer> usersMinTimeMap = new HashMap<>();
        for (Game game : games) {
            User user = game.getUser();
            Integer time = game.getTime();
            // Если пользователь еще не добавлен в мапу или его текущее время меньше сохраненного минимального
            if (!usersMinTimeMap.containsKey(user) || time < usersMinTimeMap.get(user)) {
                usersMinTimeMap.put(user, time);
            }
        }
        Map<User, Integer> sortedUsersMinTimeMap = usersMinTimeMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return sortedUsersMinTimeMap;
    }
    @Override
    public Map<User, Integer> listOfMinMoves(String name) {
        List<Game> games = repo.findAllByName(name);
        Map<User, Integer> usersMinMovesMap = new HashMap<>();
        for (Game game : games) {
            User user = game.getUser();
            Integer moves = game.getMoves();
            if (!usersMinMovesMap.containsKey(user) || moves < usersMinMovesMap.get(user)) {
                usersMinMovesMap.put(user, moves);
            }
        }
        Map<User, Integer> sortedUsersMinMovesMap = usersMinMovesMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return sortedUsersMinMovesMap;
    }
}
