package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/users";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection(){
        Connection connection = null;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}