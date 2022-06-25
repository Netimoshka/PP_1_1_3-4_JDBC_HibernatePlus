package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HyberUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.HyberUtil.getSessionFactory;


public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS user (" +
                    "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, " +
                    "lastName VARCHAR(50) NOT NULL, " +
                    "age INT NOT NULL)");

            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createSQLQuery("drop table if exists user").addEntity(User.class);
        query.executeUpdate();
        transaction.commit();
        session.close();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = HyberUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = HyberUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.load(User.class, id));
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction getAll = null;
        List<User> usersList = null;
        try (Session session = HyberUtil.getSessionFactory().openSession()) {
            getAll = session.beginTransaction();
            Query query = session.createQuery("FROM User");
            usersList = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (getAll != null) {
                getAll.rollback();
            }
            e.printStackTrace();
        }
        return usersList;
    }
    @Override
    public void cleanUsersTable() {
        try (Session session = HyberUtil.getSessionFactory().openSession()) {
            Transaction tr = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE Users", User.class).executeUpdate();
            tr.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
    }
}