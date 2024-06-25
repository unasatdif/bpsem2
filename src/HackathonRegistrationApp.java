
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
                                " where team is not null"+
                                " group by team" +
                                " order by team_id;"; // list of teams including team member count

    static final String SQL_4 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer"+
                                " from overzicht; " ; // list of signups

    static final String SQL_5 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer"+
                                " from overzicht "+ 
                                " where team_id is null; " ; // list of signups without team
                                
    static final String SQL_6 = "select team, concat(voornaam,' ',achternaam) naam, adres, ict_vaardigheid 'favoriete persoonlijke ict vaardigheid' "+
                                "from overzicht"+
                                " where team_id = ?;"; // list of team members from a specfic team substitute team name for ?

    static final String SQL_7 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer"+
                                "from overzicht"+
                                "where voornaam like ?; " ; // search list of signups on firstname

    static final String SQL_8 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer"+
                                "from overzicht"+
                                "where achternaam like ?; " ; // search list of signups on lastname

    static final String SQL_9 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer"+
                                "from overzicht"+
                                "where leeftijd like ?; " ; // search list of signups on age

    static final String SQL_10 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer"+
                                "from overzicht"+
                                "where geboorte_datum like ?; " ; // search list of signups on date of birth

    static final String SQL_11 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer"+
                                "from overzicht"+
                                "where geboorte_datum like ?; " ; // search list of signups on date of birth

    static final String SQL_12 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer"+
                                 "from overzicht"+
                                 "where email like ?; " ; // search list of signups on email

    static final String SQL_13 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer"+
                                 "from overzicht"+
                                 "where adres like ?; " ; // search list of signups on adres

    static final String SQL_14 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer"+
                                 "from overzicht"+
                                 "where mobielnummer like ?; " ; // search list of signups on mobilenumber

    static final String SQL_15 = "insert into team(naam)"+
                                 "values"+
                                 "(?);" ; // insert into team

    static final String SQL_16 = "insert into person"+
                                 "values"+
                                 "(?,?,?,?,?,?);" ; // insert into person

    static final String SQL_17 = "insert into ict_vaardigheid"+
                                 "values"+
                                 "(?,?);" ; // insert into ict_vaardigheid
                                 
    static final String SQL_18 = "insert into contact_info"+
                                 "values"+
                                 "(?,?,?,?,?);" ; // insert into contact_info

    // queries used to insert info into the database

    // static Strings for console menu
    static final String welcome = "Welcome to the Hackathon Registration console%n";
    static final String exit =  "Thank you for using the console%nSee you next time%n";
    static final String Main_Menu = "%nWhat would you like to do:%n"+
            "1 - Register%n2 - Lookup info%n3 - Exit%n"; // Main menu passed to printf
    static final String Sub_Menu = "%nWhat would you like to lookup?%n" +
            "a - Signup statistics%nb - List of signups%n" +
            "c - List of signups no team listed%n" +
            "d - List team info%ne - List info on specific signup%n"+
            "f - Back to main menu%n";  // Submenu string passed to printf
    static final String DetailedTeamInfo ="Enter the Team ID to display detailed Teaminfo%n";
    

    int mainMenuChoice;
    char subMenuChoice;
    int teamChoice;
    int searchTerm;
    String searchValue;
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
    public static int getTeamChoice(Scanner user_input) {
        return user_input.nextInt();
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
                    showSignUpStatistics();
                    System.out.printf(Sub_Menu);
                    subMenuChoice = getSubMenuChoice(user_input);
                    break;

                case 'b':
                    showAllSignUps();
                    System.out.printf(Sub_Menu);
                    subMenuChoice = getSubMenuChoice(user_input);
                    break;

                case 'c':
                    showNoTeamSignUps();
                    System.out.printf(Sub_Menu);
                    subMenuChoice = getSubMenuChoice(user_input);
                    break;

                case 'd':
                    showDetailedTeamInfo();
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

    public static void SignUp(){}
    public static void showSignUpStatistics(){
       
       try (
            // Connection and Statement objects 
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);             
            Statement stmt_1 = conn.createStatement();
            //Statement stmt_2 = conn.createStatement();
            //Statement stmt_3 = conn.createStatement();
            ResultSet rs_1 = stmt_1.executeQuery(SQL_1);
            ResultSet rs_2 = stmt_1.executeQuery(SQL_2);
            ResultSet rs_3 = stmt_1.executeQuery(SQL_3);
       ) {
            
            
            while (rs_1.next()) {
                System.out.printf("--------------------------------%n");
                System.out.println("Showing Signup statistics");
                System.out.printf("--------------------------------%n");
                System.out.printf("Total signups: %d%n",rs_1.getInt("totaal registraties"));
                System.out.printf("--------------------------------%n");
                System.out.printf("Signups in a team: %d%n", rs_1.getInt("registanten in een team"));
                System.out.printf("--------------------------------%n");
                System.out.printf("Signups without a team: %d%n", rs_1.getInt("registranten zonder team"));
            }
            rs_1.close();
            while (rs_2.next()) {
                System.out.printf("--------------------------------%n");
                System.out.printf("Total number of teams: %d%n", rs_2.getInt("count(id)"));
                System.out.printf("--------------------------------%n");
            }
            rs_2.close();

            System.out.printf("--------------------------------%n");
            System.out.printf("Team list including member count%n");
            System.out.printf("--------------------------------%n");
            System.out.printf("| %-8s | %-20s | %-3s |%n","Team ID","Team","Member count");
            while (rs_3.next()) {
                
                System.out.printf("| %-8s | %-20s | %-3s |%n",rs_3.getInt("team_id"),rs_3.getString("team"),rs_3.getInt("aantal leden"));
            }

       } catch (SQLException e) {
        // Catch errors 
            e.printStackTrace();
       }
       finally{
        //stmt_1.close();
        //rs_2.close();
        
       }
    }
    public static void showAllSignUps(){

        try(
            // Connection and Statement objects 
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt_4 = conn.createStatement();
            ResultSet rs_4 = stmt_4.executeQuery(SQL_4);
        ) {
            System.out.printf("--------------------------------%n");
            System.out.printf("Showing Signup list%n");
            System.out.printf("--------------------------------%n");
            System.out.printf("| %-8s | %-20s | %-3s | %-10s | %-10s | %-10s | %-10s |%n","Student ID","Name","Age","Date of birth","Email","Adres","Mobile number");
            while (rs_4.next()) {
                System.out.printf("| %-10s | %-20s | %-3s | %-10s | %-10s | %-10s | %-10s |%n",rs_4.getInt(1),rs_4.getString(2),rs_4.getInt(3),rs_4.getDate(4),rs_4.getString(5),rs_4.getString(6),rs_4.getString(7));
            }
            rs_4.close();
        } catch (SQLException e) {
            // Catch SQL errors
            e.printStackTrace();
        }
        finally{

        }
    }
    public static void showNoTeamSignUps(){
        try (
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt_5 = conn.createStatement();
            ResultSet rs_5 = stmt_5.executeQuery(SQL_5);
        ){
            System.out.printf("--------------------------------%n");
            System.out.printf("Showing List of Signup with no team%n");
            System.out.printf("--------------------------------%n");
            System.out.printf("| %-8s | %-20s | %-3s | %-10s | %-10s | %-10s | %-10s |%n","Student ID","Name","Age","Date of birth","Email","Adres","Mobile number");
            while (rs_5.next()) {
                System.out.printf("| %-10s | %-20s | %-3s | %-10s | %-10s | %-10s | %-10s |%n",rs_5.getInt(1),rs_5.getString(2),rs_5.getInt(3),rs_5.getDate(4),rs_5.getString(5),rs_5.getString(6),rs_5.getString(7));
            }
            rs_5.close();
        } catch (SQLException e) {
            // // Catch SQL errors
            e.printStackTrace();
        }
    }
    public static void showTeams(){
        try (
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt_1 = conn.createStatement();
            ResultSet rs_3 = stmt_1.executeQuery(SQL_3);
        ){
            System.out.printf("--------------------------------%n");
            System.out.printf("Team list including member count%n");
            System.out.printf("--------------------------------%n");
            System.out.printf("| %-8s | %-20s | %-3s |%n","Team ID","Team","Member count");
            while (rs_3.next()) {
                
                System.out.printf("| %-8s | %-20s | %-3s |%n",rs_3.getInt("team_id"),rs_3.getString("team"),rs_3.getInt("aantal leden"));
            }
            rs_3.close();
        } catch (SQLException e) {
            // // Catch SQL errors
            e.printStackTrace();
        }
    }
    
    public static void showDetailedTeamInfo(){
        showTeams();
        System.out.printf(DetailedTeamInfo);
        int teamChoice = getTeamChoice(user_input);
        
        try (
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement pstmt_1 = conn.prepareStatement(SQL_6);
            
        ){
            pstmt_1.setInt(1, teamChoice);
            ResultSet rs_6 = pstmt_1.executeQuery();
            System.out.printf("--------------------------------%n");
            rs_6.next();
            System.out.printf("Team Info for team: %s%n",rs_6.getString(1));
            System.out.printf("--------------------------------%n");
            System.out.printf("| %-8s | %-20s | %-3s |%n","Naam","Adres","Favorite ICT Skill");
            while (rs_6.next()) {
                System.out.printf("| %-8s | %-20s | %-3s |%n",rs_6.getString(2),rs_6.getString(3),rs_6.getString(4)); 
            }
            rs_6.close();

        } catch (Exception e) {
            // Catch SQL Exception
            e.printStackTrace();
        }
    }
        
    public static void lookUpInfoBy(){
        
    }
    

}
