package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.createSQLQuery(
                    "create table if not exists Users " +
                            "(id bigint primary key auto_increment, " +
                            "firstName varchar(40) not null, " +
                            "lastName varchar(40) not null, " +
                            "age tinyint not null check (age >= 0));"
            );
        } catch (Exception e) {

        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.createSQLQuery("drop table users;");
        } catch (Exception e) {

        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = Util.getSessionFactory().openSession()){
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();

        } catch(Exception e){

        }
    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }
}
