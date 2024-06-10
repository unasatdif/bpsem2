import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    // Database URL, username, and password
    private static final String URL = "jdbc:mysql://localhost:3306/beroepsproduct_semester_2_blok_1";
    private static final String USER = "root";
    private static final String PASSWORD = "Password";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Create a statement object to perform a query
            statement = connection.createStatement();

            // Execute a query and get a result set
            resultSet = statement.executeQuery("SELECT * FROM Persoon");

            // Process the result set
            while (resultSet.next()) {
                System.out.println("Column Value: " + resultSet.getString("Studenten_nummer"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
