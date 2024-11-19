package main.java.DAO.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectionBD {

    private static Connection connection;

    private ConectionBD(Connection connection) {

    }

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = initConnection();
        } else if (connection != null && connection.isClosed()) {
            connection = initConnection();
        }
        return connection;
    }

    private static Connection initConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/Modulo_29", "postgres", "Pokemon43210.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
