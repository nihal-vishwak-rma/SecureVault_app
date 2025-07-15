
package com.myproject_app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static final String url = "jdbc:mysql://localhost:9999/YOUR_DATABSE";
    private static final String username = "YOUR_USERNAME";
    private static final String password = "YOUR_PASSWORD";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }
}

