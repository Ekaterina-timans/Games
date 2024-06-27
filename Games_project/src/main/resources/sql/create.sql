DROP TABLE IF EXISTS games;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    username varchar(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role varchar(10) NOT NULL
);

CREATE TABLE games
(
    id   SERIAL PRIMARY KEY,
    name varchar(50) NOT NULL,
    result VARCHAR(10) NOT NULL,
    time int NULL,
    moves int NULL,
    user_id int NOT NULL REFERENCES users (id)
);