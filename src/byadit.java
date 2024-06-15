import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class byadit {
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
        scanner.nextLine(); // Consume newline

        System.out.print("Enter first name: ");
        String voornaam = scanner.nextLine();
        System.out.print("Enter last name: ");
        String achternaam = scanner.nextLine();
        System.out.print("Enter birth date (YYYY-MM-DD): ");
        String geboorteDatum = scanner.nextLine();
        System.out.print("Enter age: ");
        int leeftijd = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter team name: ");
        String teamNaam = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter mobile number: ");
        String mobielNummer = scanner.nextLine();
        System.out.print("Enter address: ");
        String adres = scanner.nextLine();
        System.out.print("Enter ICT skill description: ");
        String vaardigheid = scanner.nextLine();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String insertPerson = "INSERT INTO person (voornaam, achternaam, geboorte_datum, leeftijd, team_id) VALUES ('"
                    + voornaam + "', '" + achternaam + "', '" + geboorteDatum + "', " + leeftijd + ", (SELECT id FROM team WHERE naam = '" + teamNaam + "'))";
            stmt.executeUpdate(insertPerson);

            String insertTeam = "INSERT INTO team (naam) VALUES ('" + teamNaam + "')";
            stmt.executeUpdate(insertTeam);

            String insertContact = "INSERT INTO contact_info (email, mobiel_nummer, adres) VALUES ('"
                    + email + "', '" + mobielNummer + "', '" + adres + "')";
            stmt.executeUpdate(insertContact);

            String insertSkill = "INSERT INTO ict_vaardigheid (omschrijving, student_id) VALUES ('"
                    + vaardigheid + "', (SELECT id FROM person WHERE voornaam = '" + voornaam + "'))";
            stmt.executeUpdate(insertSkill);

            System.out.println("Gegevens succesvol geregistreerd...");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Bedankt voor het registreren, " + voornaam + "!");
    }

    private static void lookupInfo(Scanner scanner) {
        printLookupMenu();
        char lookupChoice = getLookupChoice(scanner);

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            switch (lookupChoice) {
                case 'a':
                    System.out.println("Showing teammates:");
                    String sqlA = "SELECT person.voornaam, person.achternaam, team.naam AS team FROM person " +
                                  "INNER JOIN team ON person.team_id = team.id";
                    displayResults(stmt.executeQuery(sqlA));
                    break;
                case 'b':
                    System.out.println("Showing teammates (including vaardigheden):");
                    String sqlB = "SELECT person.voornaam, person.achternaam, team.naam AS team, ict_vaardigheid.omschrijving " +
                                  "FROM person " +
                                  "INNER JOIN team ON person.team_id = team.id " +
                                  "INNER JOIN ict_vaardigheid ON person.student_id = ict_vaardigheid.student_id";
                    displayResults(stmt.executeQuery(sqlB));
                    break;
                case 'c':
                    System.out.println("Showing teammates (including adres):");
                    String sqlC = "SELECT person.voornaam, person.achternaam, team.naam AS team, contact_info.adres " +
                                  "FROM person " +
                                  "INNER JOIN team ON person.team_id = team.id " +
                                  "INNER JOIN contact_info ON person.student_id = contact_info.contact_id";
                    displayResults(stmt.executeQuery(sqlC));
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayResults(ResultSet rs) throws SQLException {
        while (rs.next()) {
            String voornaam = rs.getString("voornaam");
            String achternaam = rs.getString("achternaam");
            String team = rs.getString("team");
            String extra = "";
            if (rs.getMetaData().getColumnCount() > 3) {
                extra = ", " + rs.getString(4);
            }
            System.out.println("Voornaam: " + voornaam + ", Achternaam: " + achternaam + ", Team: " + team + extra);
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
