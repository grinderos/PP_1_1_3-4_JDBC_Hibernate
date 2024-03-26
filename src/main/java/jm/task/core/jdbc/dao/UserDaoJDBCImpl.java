package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getBDConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "create table if not exists Users " +
                            "(id bigint primary key auto_increment, " +
                            "firstName varchar(40) not null, " +
                            "lastName varchar(40) not null, " +
                            "age tinyint not null check (age >= 0));"
            );
        } catch (SQLException sqex) {
            System.out.println("Ошибка при создании таблицы в базе");
            sqex.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getBDConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("drop table users;");
        } catch (SQLException sqex) {
            System.out.println("Ошибка при удалении таблицы из базы");
            sqex.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getBDConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "INSERT INTO users(firstName, lastName, age)" +
                            " value ('" + name + "', '" + lastName + "', " + age + ");"
            );
        } catch (SQLException sqex) {
            System.out.println("Ошибка при добавлении пользователя в таблицу");
            sqex.printStackTrace();
        }
        System.out.println("User с именем — " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getBDConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("delete from users where id=" + id + ";");
        } catch (SQLException sqex) {
            System.out.println("Ошибка при удалении пользователя из таблицы");
            sqex.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Connection connection = Util.getBDConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from users;")) {
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")
                );
                user.setId(resultSet.getLong("id"));
                list.add(user);
            }
        } catch (SQLException sqex) {
            System.out.println("Ошибка при получении списка пользователей");
            sqex.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getBDConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("truncate table users;");
        } catch (SQLException sqex) {
            System.out.println("Ошибка при очистке таблицы");
            sqex.printStackTrace();
        }
    }
}
