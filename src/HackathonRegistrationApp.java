
/*import util & jdbc libraries */
import java.util.Scanner;
import java.sql.*;

public class HackathonRegistrationApp {
    // Static Strings for Database connection settings
    static final String DB_URL = "jdbc:mariadb://localhost:3306/bpdb"; // change to your db url
    static final String DB_USER = "dimi"; // change to your db username
    static final String DB_PASS = "dimi"; // change to your db user password

    // static Strings for console menu
    static final String MainMenu = "Welcome to the Hackathon Registration console%n" +
            "What would you like to do:%n" +
            "1 - Register%n2 - Lookup info%n3 - Exit%n"; // Main menu passed to printf
    static final String SubMenu = "What would you like to lookup?%n" +
            "a - Registration statistics%nb - List of all entries%n" +
            "c - List of entries without a team%n" +
            "d - List of team members from a specific team%ne - Back to main menu%n"; // Submenu string passed to printf

    public class Person {

        private int studentId;
        private String voornaam;
        private String achternaam;
        private String geboorteDatum;
        private int leeftijd;
        private int teamId;
        private String ictVaardigheid;
        private String email;
        private String telefoonNummer;
        private String mobielNummer;
        private String adres;

        public Person(int studentId, String voornaam, String achternaam, String geboorteDatum, int leeftijd, int teamId,
                String ictVaardigheid, String email, String telefoonNummer, String mobielNummer, String adres) {
            this.studentId = studentId;
            this.voornaam = voornaam;
            this.achternaam = achternaam;
            this.geboorteDatum = geboorteDatum;
            this.leeftijd = leeftijd;
            this.teamId = teamId;
            this.ictVaardigheid = ictVaardigheid;
            this.email = email;
            this.telefoonNummer = telefoonNummer;
            this.mobielNummer = mobielNummer;
            this.adres = adres;
        }

        @Override
        public String toString() {
            return "Person [studentId=" + studentId + ", voornaam=" + voornaam + ", achternaam=" + achternaam
                    + ", geboorteDatum=" + geboorteDatum + ", leeftijd=" + leeftijd + ", teamId=" + teamId
                    + ", ictVaardigheid=" + ictVaardigheid + ", email=" + email + ", telefoonNummer=" + telefoonNummer
                    + ", mobielNummer=" + mobielNummer + ", adres=" + adres + "]";
        }

        public int getStudentId() {
            return studentId;
        }

        public void setStudentId(int studentId) {
            this.studentId = studentId;
        }

        public String getVoornaam() {
            return voornaam;
        }

        public void setVoornaam(String voornaam) {
            this.voornaam = voornaam;
        }

        public String getAchternaam() {
            return achternaam;
        }

        public void setAchternaam(String achternaam) {
            this.achternaam = achternaam;
        }

        public String getGeboorteDatum() {
            return geboorteDatum;
        }

        public void setGeboorteDatum(String geboorteDatum) {
            this.geboorteDatum = geboorteDatum;
        }

        public int getLeeftijd() {
            return leeftijd;
        }

        public void setLeeftijd(int leeftijd) {
            this.leeftijd = leeftijd;
        }

        public int getTeamId() {
            return teamId;
        }

        public void setTeamId(int teamId) {
            this.teamId = teamId;
        }

        public String getIctVaardigheid() {
            return ictVaardigheid;
        }

        public void setIctVaardigheid(String ictVaardigheid) {
            this.ictVaardigheid = ictVaardigheid;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTelefoonNummer() {
            return telefoonNummer;
        }

        public void setTelefoonNummer(String telefoonNummer) {
            this.telefoonNummer = telefoonNummer;
        }

        public String getMobielNummer() {
            return mobielNummer;
        }

        public void setMobielNummer(String mobielNummer) {
            this.mobielNummer = mobielNummer;
        }

        public String getAdres() {
            return adres;
        }

        public void setAdres(String adres) {
            this.adres = adres;
        }

    }

    public static void main(String[] args) throws Exception {

        String person[]; // array for person objects
        Scanner user_input = new Scanner(System.in);
        System.out.printf(MainMenu);
        int MainMenuChoice; 
        char SubMenuChoice;

        MainMenuChoice = user_input.nextInt();
        while (MainMenuChoice != 3) {
            //user_input.nextLine();
            switch (user_input.nextLine()) {
                case 1:
                    System.out.println("you chose 1");
                    break;

                case 2:
                    System.out.println("you chose 2");
                    break;

                default:
                    System.out.println("Enter your choice");
                    break;
            }
        }

        System.out.printf(SubMenu);
    }

}
