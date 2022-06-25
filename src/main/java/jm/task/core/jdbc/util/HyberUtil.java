package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;


public class HyberUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/users";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root";

    private static SessionFactory sessionFactory;
    private static Connection connection;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();

                settings.put(Environment.DRIVER, DRIVER);
                settings.put(Environment.URL, URL);
                settings.put(Environment.USER, USER_NAME);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Properties properties = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("database.properties"))) {
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password1 = properties.getProperty("password");
        try {
            connection = DriverManager.getConnection(url, username, password1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
