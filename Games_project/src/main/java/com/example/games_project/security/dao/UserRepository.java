package com.example.games_project.security.dao;

import com.example.games_project.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    @Query("SELECT u FROM User u")
    List<User> getUsersByUsername();
    void deleteByUsername(String username);
}
