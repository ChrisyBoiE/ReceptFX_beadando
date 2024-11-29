package com.recept.recept;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlite:C:/adatbazis/adatok.db");
        config.setDriverClassName("org.sqlite.JDBC");
        config.setMaximumPoolSize(1); // Csak egy kapcsolat engedélyezett egyszerre
        config.setAutoCommit(true);

        dataSource = new HikariDataSource(config);

    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
