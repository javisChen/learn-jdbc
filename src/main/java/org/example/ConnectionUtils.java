package org.example;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {

    static String url = "jdbc:mysql://localhost:3306/db";
    static String user = "root";
    static String pwd = "root";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(url, user, pwd);
            System.out.println("获取连接成功");
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("获取连接失败：" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
