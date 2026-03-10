package lk.ijse.foundation_360.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {

    private static DbConnection instance;
    private Connection connection;

    private DbConnection() throws SQLException {
        Properties dbProps = new Properties();


        try (InputStream in = DbConnection.class
                .getClassLoader()
                .getResourceAsStream("database.properties")) {

            if (in == null) {
                throw new SQLException(
                    "database.properties not found! " +
                    "Make sure the file exists at src/main/resources/database.properties"
                );
            }
            dbProps.load(in);
            System.out.println("✓ database.properties loaded successfully");

        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Failed to load database.properties: " + e.getMessage(), e);
        }


        String host     = dbProps.getProperty("db.host",     "localhost");
        String port     = dbProps.getProperty("db.port",     "3306");
        String database = dbProps.getProperty("db.name",     "foundation_360");
        String user     = dbProps.getProperty("db.user",     "root");
        String password = dbProps.getProperty("db.password", "mysql");

        String url = String.format(
            "jdbc:mysql://%s:%s/%s?useSSL=%s&serverTimezone=%s&allowPublicKeyRetrieval=%s&autoReconnect=%s&maxReconnects=3",
            host, port, database,
            dbProps.getProperty("db.useSSL",                 "false"),
            dbProps.getProperty("db.serverTimezone",          "UTC"),
            dbProps.getProperty("db.allowPublicKeyRetrieval", "true"),
            dbProps.getProperty("db.autoReconnect",           "true")
        );


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Connecting to: " + host + ":" + port + "/" + database + " as " + user);

            Properties connProps = new Properties();
            connProps.setProperty("user",     user);
            connProps.setProperty("password", password);

            connection = DriverManager.getConnection(url, connProps);

            if (connection != null && !connection.isClosed()) {
                System.out.println("✓ Database connection established successfully!");
            }

        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found. Check pom.xml dependency.", e);

        } catch (SQLException e) {
            System.err.println("✗ Database connection failed!");
            System.err.println("  Host     : " + host + ":" + port);
            System.err.println("  Database : " + database);
            System.err.println("  User     : " + user);
            System.err.println("  Message  : " + e.getMessage());

            if (e.getErrorCode() == 1045) {
                System.err.println("→ Fix: Update db.user / db.password in database.properties");
            } else if (e.getMessage() != null && e.getMessage().contains("Communications link failure")) {
                System.err.println("→ Fix: Make sure MySQL is running, check db.host and db.port");
            } else if (e.getMessage() != null && e.getMessage().contains("Unknown database")) {
                System.err.println("→ Fix: Run  CREATE DATABASE " + database + ";  in MySQL");
            }
            throw e;
        }
    }



    public static DbConnection getInstance() throws SQLException {
        if (instance == null || isConnectionClosed()) {
            synchronized (DbConnection.class) {
                if (instance == null || isConnectionClosed()) {
                    instance = new DbConnection();
                }
            }
        }
        return instance;
    }

    private static boolean isConnectionClosed() {
        try {
            return instance == null
                || instance.connection == null
                || instance.connection.isClosed();
        } catch (SQLException e) {
            return true;
        }
    }



    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    public static boolean testConnection() {
        try {
            DbConnection db = getInstance();
            return db.getConnection() != null && !db.getConnection().isClosed();
        } catch (SQLException e) {
            System.err.println("✗ Connection test failed: " + e.getMessage());
            return false;
        }
    }
}
