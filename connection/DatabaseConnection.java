package GeniusLumen.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection
{

        private Connection connection;

        public DatabaseConnection() {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gl", "root", "");
                System.out.println("Connected to the database!");
            } catch (SQLException e) {
                System.out.println("Failed to connect to the database!");
                e.printStackTrace();
            }
        }

        public Connection getConnection() {
            return connection;
        }

        public void closeConnection() {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                    System.out.println("Connection closed.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

 }

