import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample {
   static final String DB_URL = "jdbc:mysql://localhost/bpdb";
   static final String USER = "root";
   static final String PASS = "Adrena4Line";
   static final String QUERY = "SELECT * FROM person WHERE voornaam = 'adit'";

   public static void main(String[] args) {
      // Open a connection
      try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(QUERY)) {
           
         while (rs.next()) {
            // Display values
            String voornaam = rs.getString("voornaam");
            int leeftijd = rs.getInt("leeftijd"); 
            System.out.println("Voornaam: " + voornaam + ", Leeftijd: " + leeftijd);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } 
   }
}
