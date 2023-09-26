package jm.task.core.jdbc.util;


import jm.task.core.jdbc.exception.StorageException;
import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class SqlHelper {
    public static final String DELETE_FROM_USERS = "DELETE FROM users";
    public static final String DELETE_BY_ID = "DELETE FROM users WHERE id = ?";
    public static final String INSERT_USER = "INSERT INTO users (name, last_name, age) VALUES (?,?,?)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS users";
    public static final String CREATE_SQL = """
            CREATE TABLE IF NOT EXISTS users (
                id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
                name VARCHAR(255),
                last_name VARCHAR(30),    age TINYINT);""";
    public static final String SELECT_FROM_USERS = "SELECT * FROM users";

    public void execute(String sql) {
        try (PreparedStatement ps = executeUpdate(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement executeUpdate(String sql) {
        PreparedStatement ps = null;
        try {
            Connection connection = Util.getMySQLConnection();
            ps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }
    public void exHibernate(String sql) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<User> query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }
}
