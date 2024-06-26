import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class only {
    static final String DB_URL = "jdbc:mysql://localhost:3306/bpdb";
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
                case 3:
                    exit = true;
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
        System.out.println("3 - Exit");
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

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            // Insert into team table
            String insertTeam = "INSERT INTO team (naam) VALUES (?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertTeam, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, teamNaam);
                pstmt.executeUpdate();

                // Get the generated team_id
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int team_id = rs.getInt(1);

                        String insertPerson = "INSERT INTO person (voornaam, achternaam, geboorte_datum, leeftijd, team_id) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement pstmtPerson = conn.prepareStatement(insertPerson, Statement.RETURN_GENERATED_KEYS)) {
                            pstmtPerson.setString(1, voornaam);
                            pstmtPerson.setString(2, achternaam);
                            pstmtPerson.setString(3, geboorteDatum);
                            pstmtPerson.setInt(4, leeftijd);
                            pstmtPerson.setInt(5, team_id);
                            pstmtPerson.executeUpdate();

                            // Get the generated student_id
                            int student_id;
                            try (ResultSet rsPerson = pstmtPerson.getGeneratedKeys()) {
                                if (rsPerson.next()) {
                                    student_id = rsPerson.getInt(1);
                                } else {
                                    throw new SQLException("Failed to retrieve student_id.");
                                }
                            }

                            String insertContact = "INSERT INTO contact_info (email, mobiel_nummer, adres, contact_id) VALUES (?, ?, ?, ?)";
                            try (PreparedStatement pstmtContact = conn.prepareStatement(insertContact)) {
                                pstmtContact.setString(1, email);
                                pstmtContact.setString(2, mobielNummer);
                                pstmtContact.setString(3, adres);
                                pstmtContact.setInt(4, student_id); // Use student_id as contact_id
                                pstmtContact.executeUpdate();
                            }


                            String insertSkill = "INSERT INTO ict_vaardigheid (omschrijving, student_id) VALUES (?, ?)";
                            try (PreparedStatement pstmtSkill = conn.prepareStatement(insertSkill)) {
                                pstmtSkill.setString(1, vaardigheid);
                                pstmtSkill.setInt(2, student_id);
                                pstmtSkill.executeUpdate();
                            }
                        }

                        System.out.println("Gegevens succesvol geregistreerd...");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Bedankt voor het registreren, " + voornaam + "!");
    }

    private static void lookupInfo(Scanner scanner) {
        System.out.print("Enter the team name: ");
        scanner.nextLine(); // Consume newline
        String teamName = scanner.nextLine();

        printLookupMenu();
        char lookupChoice = getLookupChoice(scanner);

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            switch (lookupChoice) {
                case 'a':
                    System.out.println("Teamleden namen:");
                    String sqlA = "SELECT person.voornaam, person.achternaam, team.naam AS team FROM person " +
                                  "INNER JOIN team ON person.team_id = team.id " +
                                  "WHERE team.naam = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(sqlA)) {
                        pstmt.setString(1, teamName);
                        displayResults(pstmt.executeQuery());
                    }
                    break;
                case 'b':
                    System.out.println("Teamleden en vaardigheden:");
                    String sqlB = "SELECT person.voornaam, person.achternaam, team.naam AS team, ict_vaardigheid.omschrijving " +
                                  "FROM person " +
                                  "INNER JOIN team ON person.team_id = team.id " +
                                  "INNER JOIN ict_vaardigheid ON person.student_id = ict_vaardigheid.student_id " +
                                  "WHERE team.naam = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(sqlB)) {
                        pstmt.setString(1, teamName);
                        displayResults(pstmt.executeQuery());
                    }
                    break;
                case 'c':
                    System.out.println("Teamleden en adres:");
                    String sqlC = "SELECT person.voornaam, person.achternaam, team.naam AS team, contact_info.adres " +
                                  "FROM person " +
                                  "INNER JOIN team ON person.team_id = team.id " +
                                  "INNER JOIN contact_info ON person.student_id = contact_info.contact_id " +
                                  "WHERE team.naam = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(sqlC)) {
                        pstmt.setString(1, teamName);
                        displayResults(pstmt.executeQuery());
                    }
                    break;
                case 'd':
                    System.out.println("Totaal aantal registranten:");
                    String sqlD = "SELECT COUNT(student_id) AS totaal_registranten FROM person";
                    try (PreparedStatement pstmt = conn.prepareStatement(sqlD)) {
                        displaySingleResult(pstmt.executeQuery());
                    }
                    break;
                case 'e':
                    System.out.println("Registranten zonder groep:");
                    String sqlE = "SELECT COUNT(student_id) FROM person WHERE team_id IS NULL";
                    try (PreparedStatement pstmt = conn.prepareStatement(sqlE)) {
                        displaySingleResult(pstmt.executeQuery());
                    }
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

    private static void displaySingleResult(ResultSet rs) throws SQLException {
        if (rs.next()) {
            int count = rs.getInt(1);
            System.out.println("Totaal aantal registranten: " + count);
        }
    }

    private static void printLookupMenu() {
        System.out.println("Lookup info:");
        System.out.println("a - Teammates");
        System.out.println("b - Teammates (including vaardigheden)");
        System.out.println("c - Teammates (including adres)");
        System.out.println("d - Totaal aantal registranten");
        System.out.println("e - Registranten zonder groep");
        System.out.print("Enter your choice: ");
    }

    private static char getLookupChoice(Scanner scanner) {
        return scanner.next().charAt(0);
    }
}
