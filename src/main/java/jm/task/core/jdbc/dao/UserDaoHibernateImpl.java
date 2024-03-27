package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();
    private Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(
                    "create table if not exists Users " +
                            "(id bigint primary key auto_increment, " +
                            "firstName varchar(40) not null, " +
                            "lastName varchar(40) not null, " +
                            "age tinyint not null check (age >= 0));"
            ).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Проблема при создании таблицы.");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("drop table if exists users;").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Проблема при удалении таблицы.");
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();

        } catch (Exception e) {
            System.out.println("Проблема при добавлении сущности в таблицу.");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Проблема при удалении сущности по id ");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            list = session.createQuery("from User", User.class).list();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Проблема при получении всего списка пользователей.");
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("truncate table users;").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Проблема при очистке списка пользователей.");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
