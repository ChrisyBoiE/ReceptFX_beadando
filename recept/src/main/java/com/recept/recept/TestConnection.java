package com.recept.recept;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Kapcsolódás sikeres!");
            } else {
                System.out.println("Kapcsolódás sikertelen.");
            }
        } catch (SQLException e) {
            System.err.println("Hiba történt az adatbázis kapcsolat során:");
            e.printStackTrace();
        }
    }
}
