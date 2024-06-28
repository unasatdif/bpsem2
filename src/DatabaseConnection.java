import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Het aanmaken van een class om voor de connectie te zorgen (Referentie: JDBC W3schoolblogs en tutorialspoint)
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/beroepsproduct";
    private static final String USER = "root";
    private static final String PASSWORD = "****";
    private static Connection connection;

    public static Connection getConnection() throws SQLException{
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                // Error handeling voor mislukte database connectie
                if (e.getErrorCode() == 1045) { // Standaard MySQL error code voor "Geen toegang voor gebruiker"
                    System.out.println("Foutmelding: Verkeerde username of password.");
                } else {
                    System.out.println("Foutmelding: connectie met de database mislukt: " + e.getMessage());
                }
                throw e; // Gooi dit om gebruik te worden in de main methode
            }
        }
        return connection;
    }
}
