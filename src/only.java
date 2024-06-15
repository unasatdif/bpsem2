import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class only {
    static final String DB_URL = "jdbc:mysql://localhost/bpdb";
        static final String USER = "root";
        static final String PASS = "Adrena4Line";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            printWelcomeMessage();
            int mainChoice = getMainChoice(scanner);

            switch (mainChoice) {
                case 1:
                    register(scanner);
                    break;
                case 2:
                    lookupInfo(scanner);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void printWelcomeMessage() {
        System.out.println("Welcome to the Hackathon registration app. Please choose an option:");
        System.out.println("1 - Register");
        System.out.println("2 - Lookup info");
        System.out.print("Enter your choice: ");
    }

    private static int getMainChoice(Scanner scanner) {
        return scanner.nextInt();
    }

    private static void register(Scanner scanner) {
        scanner.nextLine();  // Consume newline
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = conn.createStatement();
     ) {		      
        System.out.print("Vul de volgende gegevens in om te registreren: ");
        // Execute a query
        System.out.println("Inserting records into the table...");          
        String sql = 'insert into person(voornaam,achternaam,geboorte_datum,leeftijd,team_id) values'
        stmt.executeUpdate(sql);
        String sql = 'insert into team(naam) values'
        stmt.executeUpdate(sql);
        String sql = 'insert into contact_info(contact_id,email,mobiel_nummer,adres) values'
        stmt.executeUpdate(sql);
        String sql = 'insert into ict_vaardigheid(omschrijving,student_id) values'
        stmt.executeUpdate(sql);
        System.out.println("Gegevens succesvol geregistreerd...");   	  
     } catch (SQLException e) {
        e.printStackTrace();
     } 
  }
}
        String name = scanner.nextLine();
        System.out.println("Bedankt voor het registreren, " + voornaam + "!");
    }

    private static void lookupInfo(Scanner scanner) {
        printLookupMenu();
        char lookupChoice = getLookupChoice(scanner);

        switch (lookupChoice) {
            case 'a':
                System.out.println("Showing teammates:");
                String sql = 'select person.voornaam, person.achternaam, team.naam team from person inner join team on person.team_id = team.id;'
                stmt.executeUpdate(sql);
                break;
            case 'b':
                System.out.println("Showing teammates (including vaardigheden):");
                String sql= 'select person.voornaam, persoon.acternaam, team.naam team, ict_vaardigheid.omschrijving from person inner join team on person.student_id = team.id inner join ict_vaardigheid on person.student_id = ict_vaardigheid.student_id'
                stmt.executeUpdate(sql);
                break;
            case 'c':
                System.out.println("Showing teammates (including adres):");
                String sql = 'select person.voornaam, person.achternaam, team.naam team, contact_info.adres from person inner join team on person.student_id = team.id inner join contact_info on person.student_id = contact_info.contact_id'
                stmt.executeUpdate(sql);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void printLookupMenu() {
        System.out.println("Lookup info:");
        System.out.println("a - Teammates");
        System.out.println("b - Teammates (including vaardigheden)");
        System.out.println("c - Teammates (including adres)");
        System.out.print("Enter your choice: ");
    }

    private static char getLookupChoice(Scanner scanner) {
        return scanner.next().charAt(0);
    }
}
