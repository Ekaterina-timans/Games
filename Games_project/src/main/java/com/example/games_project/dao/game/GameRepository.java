package com.example.games_project.dao.game;

import com.example.games_project.model.entities.Game;
import com.example.games_project.model.entities.ItemAttribute.Result;
import com.example.games_project.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    Integer countFindAllByNameAndUser_IdAndResult(String name, int id, Result result);

    List<Game> findAllByNameAndUser_Id(String name, int id);

    List<Game> findAllByName(String name);

    List<Game> findByUser(User user);
    void deleteById(int id);
    Game findById(int id);
    List<Game> findByUserAndName(User user, String name);

}
