package com.example.games_project.model.entities;

import com.example.games_project.model.entities.ItemAttribute.Result;
import com.example.games_project.security.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "games")
@Getter
@Setter
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "result")
    @Enumerated(EnumType.STRING)
    private Result result;
    @Column(name = "time")
    private Integer time;
    @Column(name = "moves")
    private Integer moves;
    @ManyToOne(cascade = CascadeType.PERSIST)
    User user;
}
