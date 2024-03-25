package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соединения с БД
    private static String hostName = "localhost";
    private static String dbName = "users";
    private static String userName = "root";
    private static String password = "password";
    private static SessionFactory sessionFactory;

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
                    "jdbc:mysql://" + hostName + ":3306/"
                            + dbName, userName, password
            );
        } catch (SQLException e) {
            throw new SQLException("Ошибка при получении Connection", e);
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() throws Exception {
        if (sessionFactory == null) {
            try {
                Configuration config = new Configuration()
                        .addAnnotatedClass(User.class)
                        .setProperty(Environment.URL, "jdbc:mysql://"+hostName+":3306/"+dbName)
                        .setProperty(Environment.DRIVER, "com.mysql.cj.jdbc.Driver")
                        .setProperty(Environment.USER, userName)
                        .setProperty(Environment.PASS, password)
                        .setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread")
                        .setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect")
                        .setProperty(Environment.SHOW_SQL, "true");
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(config.getProperties()).build();
                return sessionFactory = config.buildSessionFactory();
            } catch (Exception e) {
                System.out.println("Ошибка при создании SessionFactory");
                throw new Exception("Ошибка при получении Connection", e);
            }
        }
        return sessionFactory;
    }
}
