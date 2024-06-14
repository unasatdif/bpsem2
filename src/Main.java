
import java.sql.SQLException;
import java.util.Scanner;

// Een simpele UI middels scanner en printlines 
public class Main {
    public static void main(String[] args) throws SQLException {
        try {
            new Registration();
        } catch (SQLException e) {
            System.out.println("Foutmelding: connectie met de database mislukt. Controleer de database gegevens.");
            return; // Stop het programma bij fout
        }
        Registration registration = new Registration();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welkom bij de Hackathon registratie app.");
            System.out.print("Selecteer uit de volgende opties: ");
            System.out.println("\n1. Registreer");
            System.out.println("2. Informatie opzoeken");
            System.out.println("3. Afsluiten");
            System.out.print("Maak uw keuze: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Onverwacht gedrag voorkomen door het achtergebleven newline character te
                                // consumeren

            switch (choice) {
                case 1:
                    System.out.print("Voer uw voornaam in: ");
                    String username = scanner.nextLine();
                    System.out.print("Voer uw achternaam in: ");
                    String password = scanner.nextLine();
                    System.out.print("Voer uw geboortedatum in (yyyy-mm-dd): ");
                    String geboortedatum = scanner.nextLine();
                    System.out.print("Voer uw favoriete ICT vaardigheid in: ");
                    String favorieteIT = scanner.nextLine();
                    registration.registerUser(username, password, geboortedatum, favorieteIT);
                    break;
                case 2:
                    System.out.println("Kies welk informatie u wilt weergeven:");
                    System.out.println("1. Naam en achternaam van teamleden");
                    System.out.println("2. Verblijfplaats van teamleden");
                    System.out.println("3. Favoriete ICT vaardigheid van teamleden");
                    System.out.println("4. Lijst van registranten");
                    System.out.print("Maak uw keuze: ");
                    int viewChoice = scanner.nextInt();
                    scanner.nextLine();

                    switch (viewChoice) {
                        case 1:
                            registration.viewInfo1("Teamleden");
                            break;
                        case 2:
                            registration.viewInfo2("teamleden_adres");
                            break;
                        case 3:
                            registration.viewInfo3("teamleden_vaardigheden");
                            break;
                        case 4:
                            registration.listUsers();
                        default:
                            System.out.println("Ongeldige keus. Probeer het nog eens.");
                    }
                    break;
                case 3:
                    System.out.println("Tot ziens...");
                    return;
                default:
                    System.out.println("Ongeldige keus. Probeer het nog eens.");
            }
        }
    }
}