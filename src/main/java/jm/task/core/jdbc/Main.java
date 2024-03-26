package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        String hostName = "localhost";
        String dbName = "users";
        String userName = "root";
        String password = "password";
        Util.setConnectionParameters(hostName, dbName, userName, password);

        UserServiceImpl usi = new UserServiceImpl();

        usi.createUsersTable();

        usi.saveUser("James", "Bond", (byte) 66);
        usi.saveUser("Tony", "Stark", (byte) 55);
        usi.saveUser("Ithan", "Hunt", (byte) 44);
        usi.saveUser("Ostin", "Powers", (byte) 33);

        List<User> list = usi.getAllUsers();
        System.out.println(list);

        usi.cleanUsersTable();
        usi.dropUsersTable();
    }
}
