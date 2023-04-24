package org.example;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ResultTreeType;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            Connection connection = ConnectionUtils.getConnection();
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into user(name, age) values (?, ?)");
                preparedStatement.setString(1, "ben");
                preparedStatement.setInt(2, 18);
                int resultSet = preparedStatement.executeUpdate();
//                while (resultSet.next()) {
//                    int id = resultSet.getInt("id");
//                    String name = resultSet.getString("name");
//                    int age = resultSet.getInt("age");
//                    log.info("id = {}, name = {}, age = {}", id, name, age);
//                }
                connection.commit();
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                throw new RuntimeException(e);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
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