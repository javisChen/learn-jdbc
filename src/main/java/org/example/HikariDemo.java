package org.example;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import com.zaxxer.hikari.pool.HikariPool;
import com.zaxxer.hikari.util.FastList;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

@Slf4j
public class HikariDemo {

    static String url = "jdbc:mysql://localhost:3306/db";
    static String user = "root";
    static String pwd = "root";

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        FastList<Integer> objects = new FastList<>(Integer.class, 100_00000);
        for (int i = 0; i < 100_00000; i++) {
            objects.add(i);
        }
        System.out.println(System.currentTimeMillis() - start);
        System.out.println("=================");

        start = System.currentTimeMillis();
        ArrayList<Integer> list = new ArrayList<>(100_00000);
        for (int i = 0; i < 100_00000; i++) {
            list.add(i);
        }
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        System.out.println(list.get(500000));
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        System.out.println(objects.get(500000));
        System.out.println(System.currentTimeMillis() - start);


    }

    public static void main1(String[] args) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(user);
        hikariConfig.setPassword(pwd);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setAutoCommit(true);
        hikariConfig.setMaximumPoolSize(20);
        hikariConfig.setMinimumIdle(10);
        hikariConfig.setValidationTimeout(1000);
        hikariConfig.setIdleTimeout(30000);
        hikariConfig.setInitializationFailTimeout(10);
        hikariConfig.setConnectionInitSql("select 1");
        hikariConfig.setPoolName("db-pool");
        hikariConfig.setMaxLifetime(30000);
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        Connection connection = null;
        try {
            connection = hikariDataSource.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into user(name, age) values (?, ?)");
            preparedStatement.setString(1, "dd");
            preparedStatement.setInt(2, 18);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
//            try {
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
        }
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
