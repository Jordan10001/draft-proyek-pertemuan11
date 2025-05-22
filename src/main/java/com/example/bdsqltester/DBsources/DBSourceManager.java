package com.example.bdsqltester.DBsources;

import com.example.bdsqltester.Alert.AlertClass;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBSourceManager {

    private static final HikariDataSource userData;
    private static final HikariDataSource branchAdminData;
    private static final HikariDataSource centralAdminData;

    static {
        userData = createDataSource("jdbc:postgresql://localhost:5432/postgres","postgres", "admin");
        branchAdminData = createDataSource("jdbc:postgresql://localhost:5432/postgres","postgres", "admin");
        centralAdminData = createDataSource("jdbc:postgresql://localhost:5432/postgres","postgres", "admin");
    }

    private static HikariDataSource createDataSource(String jdbc,String username, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbc);
        config.setUsername(username);
        config.setPassword(password);
//        config.addDataSourceProperty("cachePrepStmts", "true");
//        config.addDataSourceProperty("prepStmtCacheSize", "250");
//        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return new HikariDataSource(config);
    }

    public static Connection getUserConnection() throws SQLException {
        try{
            Connection conn = userData.getConnection();
            System.out.println("Connection established: " + conn);
            System.out.println("✅ Connected to database PostgreSQL!");
            return conn;
        } catch (SQLException e) {
            AlertClass.ErrorAlert("❌Connection Error", "Database Connection Error", e.getMessage());
            return null;
        }
    }


    public static Connection getBranchAdminConnection() throws SQLException {
        return branchAdminData.getConnection();
    }
    public static Connection getCentralAdminConnection() throws SQLException {
        return centralAdminData.getConnection();
    }

}
