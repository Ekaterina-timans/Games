package com.example.games_project.dao.game;

import com.example.games_project.dao.DAO;
import com.example.games_project.model.entities.Game;
import com.example.games_project.model.entities.ItemAttribute.Result;
import com.example.games_project.security.entity.User;

import java.util.List;
import java.util.Map;

public interface GameService extends DAO<Game> {
    int numbersOfResults(String name, int id, Result result);
    int findMinTime(String name, int id);
    int findMinMoves(String name, int id);
    Map<User, Integer> listOfResult(String name);
    Map<User, Integer> listOfMinTime(String name);
    Map<User, Integer> listOfMinMoves(String name);
    List<Game> findByUser(User user);
    void deleteGameById(int id);
}
