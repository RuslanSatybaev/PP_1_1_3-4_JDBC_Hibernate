package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.exception.StorageException;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.SqlHelper;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import static jm.task.core.jdbc.util.SqlHelper.*;
import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    SqlHelper helper = new SqlHelper();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        helper.exHibernate(CREATE_SQL);
    }

    @Override
    public void dropUsersTable() {
        helper.exHibernate(DROP_TABLE);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = getSessionFactory().openSession()) {
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void cleanUsersTable() {
        helper.exHibernate(DELETE_FROM_USERS);
    }
}
