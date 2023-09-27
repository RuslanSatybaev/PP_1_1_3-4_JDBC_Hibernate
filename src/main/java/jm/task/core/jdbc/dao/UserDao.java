package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;

public interface UserDao {
    String DELETE_FROM_USERS = "DELETE FROM users";
    String DELETE_BY_ID = "DELETE FROM users WHERE id = ?";
    String INSERT_USER = "INSERT INTO users (name, last_name, age) VALUES (?,?,?)";
    String DROP_TABLE = "DROP TABLE IF EXISTS users";
    String CREATE_SQL = """
            CREATE TABLE IF NOT EXISTS users (
                id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
                name VARCHAR(255),
                last_name VARCHAR(30),    age TINYINT);""";
    String SELECT_FROM_USERS = "SELECT * FROM users";

    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);

    void removeUserById(long id);

    List<User> getAllUsers();

    void cleanUsersTable();
}
