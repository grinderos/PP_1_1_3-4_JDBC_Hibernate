package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    public void createUsersTable() throws SQLException {
        try (Connection connection = Util.getBDConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("create table if not exists Users " +
                    "(id bigint primary key auto_increment, \n" +
                    "firstName varchar(40) not null,\n" +
                    "lastName varchar(40) not null,\n" +
                    "age tinyint not null check (age > 0));");
        } catch (SQLException sqex) {
//            throw sqex;
        }
    }

    public void dropUsersTable() throws SQLException {
        try (Connection connection = Util.getBDConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("drop table users;");
        } catch (SQLException sqex) {
//            throw sqex;
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try (Connection connection = Util.getBDConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO users(firstName, lastName, age) value ('"+name+"', '"+lastName+"', "+age+");");
        } catch (SQLException sqex) {
//            throw sqex;
        }
        System.out.println("User с именем — "+name+" добавлен в базу данных");
    }

    public void removeUserById(long id) throws SQLException {
        try (Connection connection = Util.getBDConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("delete from users where id="+id+";");
        } catch (SQLException sqex) {
//            throw sqex;
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> list = new ArrayList<>();
        try (Connection connection = Util.getBDConnection(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from users;")) {
            while (resultSet.next()) {
                User user = new User(resultSet.getString("firstName"),
                        resultSet.getString("lastName"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                list.add(user);
            }
        } catch (SQLException sqex) {
//            throw sqex;
        }

        return list;
    }

    public void cleanUsersTable() throws SQLException {
        try (Connection connection = Util.getBDConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("truncate table users;");
        } catch (SQLException sqex) {
//            throw sqex;
        }
    }
}
