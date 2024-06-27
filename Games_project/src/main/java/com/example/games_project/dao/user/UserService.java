package com.example.games_project.dao.user;

import com.example.games_project.dao.DAO;
import com.example.games_project.security.entity.User;

import java.util.List;

public interface UserService extends DAO<User> {
    User findByUsername(String username);
    List<String> getAllUsernames();
    void deleteByUsername(String username);
    List<String> getUsernamesByPartOfName(String namePart);
}
