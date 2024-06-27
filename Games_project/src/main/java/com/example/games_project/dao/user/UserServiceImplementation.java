package com.example.games_project.dao.user;

import com.example.games_project.security.dao.UserRepository;
import com.example.games_project.security.entity.Role;
import com.example.games_project.security.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService{
    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder;

    @Override
    public User save(User user) {
        if (repo.findByUsername(user.getUsername()) == null) {
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRole(Role.ROLE_USER);
            return repo.save(user);
        }
        return null;
    }

    @Override
    public List<String> getAllUsernames() {
        List<User> users = repo.getUsersByUsername();
        List<String> usernames = users.stream().map(User::getUsername).collect(Collectors.toList());
        return usernames;
    }

    @Override
    public List<String> getUsernamesByPartOfName(String namePart) {
        List<User> users = repo.getUsersByUsername();
        List<String> usernames = users.stream()
                .filter(user -> user.getUsername().startsWith(namePart))
                .map(User::getUsername)
                .collect(Collectors.toList());
        return usernames;
    }

    @Override
    public void deleteByUsername(String username) {
        repo.deleteByUsername(username);
    }

    @Override
    public User findByUsername(String username) {
        return repo.findByUsername(username);
    }
}
