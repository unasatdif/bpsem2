/*import util & jdbc libraries */
import java.util.Scanner;
import java.sql.*;

public class HackathonRegistrationApp {
    // Database connection settings
    static final String DB_URL = "jdbc:mariadb://localhost:3306/bpdb";
    static final String DB_USER = "dimi";
    static final String DB_PASS = "dimi";
    Connection db_con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);

    public class Team{
        private int id; 
        private String naam;
    }

    public class Person{
        private int studentId;
        private String voornaam;
        private String achternaam;
        private String geboorteDatum;
        private int leeftijd; 
        private int teamId;

    }

    public class IctVaardigheid{
        private int id;
        private String omschrijving;
        private String studentId;
    }

    public class ContactInfo{
        private int contactId;
        private String email;
        private String telefoonNummer;
        private String mobielNummer;
        private String adres;
    }
    

    public static void main(String[] args) throws Exception {
        System.out.println("Hello World");
    }
}
