package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        String hostName = "localhost";
        String dbName = "users";
        String userName = "root";
        String password = "password";
        Util.setConnectionParameters(hostName, dbName, userName, password);

        UserServiceImpl usi = new UserServiceImpl();
        usi.createUsersTable();
        usi.saveUser("Ivan", "Ivanov", (byte)66);
        usi.saveUser("Petr", "Petrov", (byte)55);
        usi.saveUser("Sidor", "Sidorov", (byte)44);
        usi.saveUser("Ostin", "Powers", (byte)33);
        List<User> list = usi.getAllUsers();
        System.out.println(list);
        usi.cleanUsersTable();
        usi.dropUsersTable();
    }
}
