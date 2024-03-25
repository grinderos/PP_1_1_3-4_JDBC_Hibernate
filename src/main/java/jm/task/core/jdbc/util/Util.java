package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    // реализуйте настройку соединения с БД
    private static String hostName = "localhost";
    private static String dbName = "users";
    private static String userName = "root";
    private static String password = "password";

    public static void setConnectionParameters(String host_name, String db_name, String user_login, String user_password) {
        hostName = host_name;
        dbName = db_name;
        userName = user_login;
        password = user_password;
    }

    public static Connection getBDConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://"+ hostName + ":3306/"
                            + dbName, userName, password
            );
        } catch (SQLException e) {
            throw new SQLException("Ошибка при получении Connection", e);
        }
        return connection;
    }

    public static Connection getBDConnection(String hostName, String dbName,
                                             String userName, String password) throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://"
                + hostName + ":3306/" + dbName, userName, password);
    }
}
