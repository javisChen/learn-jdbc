package org.example;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            Connection connection = ConnectionUtils.getConnection();
            try {
                Statement statement = connection.createStatement();
//                executeUpdate(statement);
                executeQuery(statement);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
            }
        }
        System.out.println("");
    }

    private static int executeUpdate(Statement statement) throws SQLException {
        // Statement：用来执行SQL语句的
        return statement.executeUpdate("insert into user (name, age) values ('ben', 18);");
    }

    private static void executeQuery(Statement statement) throws SQLException {
        // Statement：用来执行SQL语句的
        ResultSet resultSet = statement.executeQuery("select * from user");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            log.info("id = {}, name = {}, age = {}", id, name, age);
        }
    }
}