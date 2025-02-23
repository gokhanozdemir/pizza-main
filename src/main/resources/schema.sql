-- src/main/resources/schema.sql
CREATE SCHEMA IF NOT EXISTS pizza;

DROP TABLE IF EXISTS pizza.users;

CREATE TABLE pizza.users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);