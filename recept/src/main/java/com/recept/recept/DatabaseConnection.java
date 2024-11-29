package com.recept.recept;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Adatbázis elérési útvonala
    private static final String URL = "jdbc:sqlite:C:/adatbazis/adatok.db";

    public static Connection getConnection() throws SQLException {
        try {
            // Biztosítjuk, hogy a JDBC driver betöltődik
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Az SQLite JDBC driver nem található.", e);
        }
        // Kapcsolat az adatbázishoz
        return DriverManager.getConnection(URL);
    }

    public class TestConnection {
        public static void main(String[] args) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                if (conn != null) {
                    System.out.println("Kapcsolódás sikeres!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
