
/*import util & jdbc libraries */
import java.util.Scanner;
import java.sql.*;

public class HackathonRegistrationApp {
    // Static Strings for Database connection settings
    static final String DB_URL = "jdbc:mariadb://localhost:3306/bpdb"; // change to your db url
    static final String DB_USER = "dimi"; // change to your db username
    static final String DB_PASS = "dimi"; // change to your db user password

    // queries used to select info from the database 
    static final String SQL_1 = "select count(student_id) 'totaal registraties'," + 
                                "count(team_id) 'registanten in een team',"+
                                "count(student_id)- count(team_id) 'registranten zonder team'"+
                                "from overzicht;"; //  entry statistics (number of entries, number of entries with a team, number of entries without a team)

    static final String SQL_2 = "select count(id) from team;"; //total number of teams

    static final String SQL_3 = "select team_id, team, count(student_id) 'aantal leden'"+
                                "from overzicht"+
                                "where team is not null"+
                                "group by team" +
                                "order by team_id;"; // list of teams including team member count
                                
    static final String SQL_4 = "select concat(voornaam,' ',achternaam) naam, adres, ict_vaardigheid 'favoriete persoonlijke ict vaardigheid' "+
                                "from overzicht"+
                                "where team = '?';"; // list of team members from a specfic team substitute team name for ?

    static final String SQL_5 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer"+
                                "from overzicht; " ; // list of signups

    static final String SQL_6 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer"+
                                "from overzicht"+
                                "where voornaam like ?; " ; // search list of signups on firstname

    static final String SQL_7 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer"+
                                "from overzicht"+
                                "where achternaam like ?; " ; // search list of signups on lastname

    static final String SQL_8 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer"+
                                "from overzicht"+
                                "where leeftijd like ?; " ; // search list of signups on age

    static final String SQL_9 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer"+
                                "from overzicht"+
                                "where geboorte_datum like ?; " ; // search list of signups on date of birth

    static final String SQL_10 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer"+
                                "from overzicht"+
                                "where geboorte_datum like ?; " ; // search list of signups on date of birth


    static final String SQL_11 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer"+
                                 "from overzicht"+
                                 "where email like ?; " ; // search list of signups on email

    static final String SQL_12 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer"+
                                 "from overzicht"+
                                 "where adres like ?; " ; // search list of signups on adres

    static final String SQL_13 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer"+
                                 "from overzicht"+
                                 "where mobielnummer like ?; " ; // search list of signups on mobilenumber

    static final String SQL_14 = "insert into team(naam)"+
                                 "values"+
                                 "(?);" ; // insert into team

    static final String SQL_15 = "insert into person"+
                                 "values"+
                                 "(?,?,?,?,?,?);" ; // insert into person

    static final String SQL_16 = "insert into ict_vaardigheid"+
                                 "values"+
                                 "(?,?);" ; // insert into ict_vaardigheid
                                 
    static final String SQL_17 = "insert into contact_info"+
                                 "values"+
                                 "(?,?,?,?,?);" ; // insert into contact_info

    // queries used to insert info into the database

    // static Strings for console menu
    static final String welcome = "Welcome to the Hackathon Registration console%n";
    static final String exit =  "Thank you for using the console%nSee you next time%n";
    static final String Main_Menu = "What would you like to do:%n"+
            "1 - Register%n2 - Lookup info%n3 - Exit%n"; // Main menu passed to printf
    static final String Sub_Menu = "What would you like to lookup?%n" +
            "a - Signup statistics%nb - List of signups%n" +
            "c - List of signups no team listed%n" +
            "d - List team info%ne - List info on specific signup%n"+
            "f - Back to main menu%n";  // Submenu string passed to printf

    int mainMenuChoice;
    char subMenuChoice;
    static boolean quit = false;
    static boolean returnToMainMenu = false;
    String person[]; // array for person objects
    static Scanner user_input = new Scanner(System.in);

    public static int getMainMenuChoice(Scanner user_input) {
        return user_input.nextInt();
    }

    /*
     * public static int setMainMenuChoice(Scanner user_input) {
     * mainMenuChoice = user_input.nextInt();
     * }
     */

    public static char getSubMenuChoice(Scanner user_input) {
        return user_input.next().charAt(0);
    }

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

        System.out.printf(welcome);
        mainMenu();
    }

    public static void mainMenu() {
        System.out.printf(Main_Menu);
        int mainMenuChoice = getMainMenuChoice(user_input);

        while (!quit) {

            switch (mainMenuChoice) {
                case 1:
                    System.out.println("you chose 1");
                    System.out.printf(Main_Menu);
                    mainMenuChoice = getMainMenuChoice(user_input);

                    break;

                case 2:
                    System.out.println("you chose 2");
                    subMenu();

                    break;

                case 3:
                    System.out.printf(exit);
                    quit = true;
                    break;

                default:
                    System.out.println("Enter your choice");
                    break;
            }
        }
    }

    public static void subMenu() {

        System.out.printf(Sub_Menu);
        char subMenuChoice = getSubMenuChoice(user_input);

        while (!returnToMainMenu) {

            switch (subMenuChoice) {
                case 'a':
                    System.out.println("you chose a");
                    System.out.printf(Sub_Menu);
                    subMenuChoice = getSubMenuChoice(user_input);
                    break;

                case 'b':
                    System.out.println("you chose b");
                    System.out.printf(Sub_Menu);
                    subMenuChoice = getSubMenuChoice(user_input);
                    break;

                case 'c':
                    System.out.println("you chose c");
                    System.out.printf(Sub_Menu);
                    subMenuChoice = getSubMenuChoice(user_input);
                    break;

                case 'd':
                    System.out.println("you chose d");
                    System.out.printf(Sub_Menu);
                    subMenuChoice = getSubMenuChoice(user_input);
                    break;

                case 'e':
                    System.out.println("you chose e");
                    System.out.printf(Sub_Menu);
                    subMenuChoice = getSubMenuChoice(user_input);
                    break;

                case 'f':
                    returnToMainMenu = true;
                    mainMenu();
                    break;

            }
        }
    }

    public static void showSignUpStatistics(){}
    public static void showAllSignUps(){}
    public static void showNoTeamSignUps(){}
    public static void showTeams(){}
    public static void showTeamInfo(){}
    public static void lookUpInfoBy(){}
    public static void SignUp(){}

}
