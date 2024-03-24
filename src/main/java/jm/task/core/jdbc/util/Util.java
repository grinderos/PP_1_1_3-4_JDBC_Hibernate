package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static String hostName = "localhost";
    private static String dbName = "users";
    private static String userName = "root";
    private static String password = "Gr1423571";
    public static void setConnectionParameters(String host_name, String db_name, String user_login, String user_password){
        hostName = host_name;
        dbName = db_name;
        userName = user_login;
        password = user_password;
    }
    public static Connection getBDConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://" + hostName + ":3306/" + dbName, userName, password);
    }
    public static Connection getBDConnection(String hostName, String dbName, String userName, String password) throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://" + hostName + ":3306/" + dbName, userName, password);
    }
}
