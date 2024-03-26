package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(
                    "create table if not exists Users " +
                            "(id bigint primary key auto_increment, " +
                            "firstName varchar(40) not null, " +
                            "lastName varchar(40) not null, " +
                            "age tinyint not null check (age >= 0));"
            ).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Проблема при создании таблицы.");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("drop table users;").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Проблема при удалении таблицы.");
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = sessionFactory.openSession()){
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();

        } catch(Exception e){
            System.out.println("Проблема при добавлении сущности в таблицу.");
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch(Exception e){
            System.out.println("Проблема при удалении сущности по id ");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            list = session.createQuery("from User", User.class).list();
            session.getTransaction().commit();
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Проблема при получении всего списка пользователей.");
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.createSQLQuery("truncate table users;").executeUpdate();
            session.getTransaction().commit();
        } catch(Exception e){
            System.out.println("Проблема при очистке списка пользователей.");
        }
    }
}
