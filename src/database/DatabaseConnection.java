package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // ⚠️ IMPORTANT: UPDATE THESE VALUES!
    private static final String URL = "jdbc:postgresql://localhost:16386/hospital_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123456789";  // ⚠️ CHANGE THIS!
    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to database!");

        } catch (SQLException e) {
            System.out.println("❌ Database connection failed!");
            System.out.println("Error: " + e.getMessage());
        }

        return connection;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("🔒 Connection closed.");
            } catch (SQLException e) {
                System.out.println("⚠️ Error closing connection!");
                e.printStackTrace();
            }
        }
    }
}