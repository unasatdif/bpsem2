
// Import required packages
import java.sql.*;

public class HackathonRegistrationApp {
    // Database connection settings
    static final String DB_URL = "your db url";
    static final String DB_USER = "your db user";
    static final String DB_PASS = "your db password";
    Connection db_con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);

    public static void main(String[] args) throws Exception {
        try {
            //main code 
            //Open a database connection 
            

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        finally{
            // close connection afster transaction
        }
        System.out.println("Hello, World!");
    }
}
