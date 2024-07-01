import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
   // private static final String DB_PATH = "c:\Users\sneha\OneDrive\Documents\SQL Server Management Studio");
   // jdbc:sqlite:bank.db";
   private static final String DB_PATH = "C:\Users\SQL "SQL Server Management Studio\SQLQuery1.sql";
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_PATH);
            } catch (SQLException e) {
                System.err.println("Error connecting to the database.");
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void main(String[] args) {
        DatabaseConnection.getConnection();
    }
}