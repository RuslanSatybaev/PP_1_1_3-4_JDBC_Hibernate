package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.exception.StorageException;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.SqlHelper;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.SqlHelper.*;

public class UserDaoJDBCImpl implements UserDao {
    SqlHelper helper = new SqlHelper();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        helper.execute(CREATE_SQL);
    }

    public void dropUsersTable() {
        helper.execute(DROP_TABLE);
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = helper.executeUpdate(INSERT_USER)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement = helper.executeUpdate(DELETE_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> members = new ArrayList<>();
        try (Connection connection = Util.getMySQLConnection()) {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery(SELECT_FROM_USERS);
            while (res.next()) {
                User user = new User();
                user.setId(res.getLong(1));
                user.setName(res.getString(2));
                user.setLastName(res.getString(3));
                user.setAge((byte) res.getInt(4));
                members.add(user);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return members;
    }

    public void cleanUsersTable() {
        helper.execute(DELETE_FROM_USERS);
    }
}
