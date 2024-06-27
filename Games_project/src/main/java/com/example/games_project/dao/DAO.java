package com.example.games_project.dao;

import java.util.List;

public interface DAO<T> {
    T save(T t);
}
