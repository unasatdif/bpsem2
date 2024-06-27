
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

    static final String SQL_3 = "select team_id, team, count(student_id) 'aantal leden' "+
                                "from overzicht "+
                                "where team is not null "+
                                "group by team " +
                                "order by team_id;"; // list of teams including team member count

    static final String SQL_4 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer "+
                                "from overzicht; " ; // list of signups

    static final String SQL_5 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer "+
                                "from overzicht "+ 
                                "where team_id is null; " ; // list of signups without team
                                
    static final String SQL_6 = "select team, concat(voornaam,' ',achternaam) naam, adres, ict_vaardigheid 'favoriete persoonlijke ict vaardigheid' "+
                                "from overzicht "+
                                "where team_id = ?;"; // list of team members from a specfic team substitute team name for ?

    static final String SQL_7 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer "+
                                "from overzicht "+
                                "where voornaam like ?; " ; // search list of signups on firstname

    static final String SQL_8 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer "+
                                "from overzicht "+
                                "where achternaam like ?;" ; // search list of signups on lastname

    static final String SQL_9 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer "+
                                "from overzicht "+
                                "where leeftijd like ?; " ; // search list of signups on age

    static final String SQL_10 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer "+
                                "from overzicht "+
                                "where student_id = ?; " ; // search list of signups on student_id

    static final String SQL_11 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer "+
                                "from overzicht "+
                                "where geboorte_datum like ?; " ; // search list of signups on date of birth

    static final String SQL_12 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer "+
                                 "from overzicht "+
                                 "where email like ?; " ; // search list of signups on email

    static final String SQL_13 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer "+
                                 "from overzicht "+
                                 "where adres like ?; " ; // search list of signups on adres

    static final String SQL_14 = "select student_id, concat(voornaam,' ',achternaam) naam, leeftijd, geboorte_datum,email,adres,mobielnummer "+
                                 "from overzicht "+
                                 "where mobielnummer like ?; " ; // search list of signups on mobilenumber
    // queries used to insert data into the database 
    static final String SQL_15 = "insert into team(naam) "+
                                 "values "+
                                 "(?);" ; // insert into team

    static final String SQL_16 = "insert into person "+
                                 "values "+
                                 "(?,?,?,?,?,?);" ; // insert into person

    static final String SQL_17 = "insert into ict_vaardigheid "+
                                 "values "+
                                 "(?,?);" ; // insert into ict_vaardigheid
                                 
    static final String SQL_18 = "insert into contact_info "+
                                 "values "+
                                 "(?,?,?,?,?);" ; // insert into contact_info

    // static Strings for console menu
    static final String welcome = "Welcome to the Hackathon Registration console%n";
    static final String exit =  "Thank you for using the console%nSee you next time%n";
    static final String mainMenu = "%nWhat would you like to do:%n"+
            "1 - Register%n2 - Lookup info%n3 - Exit%n"; // Main menu passed to printf
    static final String subMenu = "%nWhat would you like to lookup?%n" +
            "a - Signup statistics%nb - List of signups%n" +
            "c - List of signups no team listed%n" +
            "d - List team info%ne - List info on specific signup%n"+
            "f - Back to main menu%n";  // Submenu string passed to printf
    static final String detailedTeamInfo ="Enter the Team ID to display detailed Teaminfo%n";
    static final String searchText = "Please select a search term to begin the search%n Search for signup info by:%n"+
                                    "1 - Student ID%n2 -Firstname%n3 - Lastname%n4 - Age%n5 - Date of birth%n6 - Email"+
                                    "%n7 - Adres%n8 - Mobilenumber%n9 - Quit search%n";
    // all signup Strings 
    static final String signUpText = "How would you like to sign up?%n1. Create a new team%n2. Join an existing team%n3. Abort mission!";
    static final String preSignUpPrompt = "Please enter all requested signup information:%n";
    static final String signUpPrompt_1 =  "Enter your student id:%n";
    static final String signUpPrompt_2 =  "Enter your firstname:%n";
    static final String signUpPrompt_3 =  "Enter your lastname:%n";
    static final String signUpPrompt_4 =  "Enter your date of birth:%n";
    static final String signUpPrompt_5 =  "Enter your age:%n";
    static final String signUpPrompt_6 =  "Enter your team id:%n";
    static final String signUpPrompt_7 =  "Enter your favorite ICT skill:%n";
    static final String signUpPrompt_8 =  "Enter your email:%n";
    static final String signUpPrompt_9 =  "Enter your telefoon number:%n";
    static final String signUpPrompt_10 = "Enter your mobile number:%n";
    static final String signUpPrompt_11 = "Enter your adres:%n";
    static final String signUpPrompt_12 = "Enter the name for your team:%n";

    // all variables 
    int mainMenuChoice;
    char subMenuChoice;
    int signUpChoice;
    int teamChoice;
    int searchTerm;
    String searchValue;
    static boolean quit = false; // quit program boolean
    static boolean returnToMainMenu = false;
    static boolean noSignUp = false;
    static boolean noSearch = false;
    static boolean noRerun = false; 
    String person[]; // array for person objects
    static Scanner user_input = new Scanner(System.in);

    public static int getMainMenuChoice(Scanner user_input) {
        return user_input.nextInt();
    }

    
    public static int getSignUpChoice(Scanner user_input) {
        return user_input.nextInt();
    }
    

    public static char getSubMenuChoice(Scanner user_input) {
        return user_input.next().charAt(0);
    }

    public static int getTeamChoice(Scanner user_input) {
        return user_input.nextInt();
    }

    public static int getSearchTerm(Scanner user_input){
        return user_input.nextInt();
    }

    public static String getSearchValue(Scanner user_input){
        return user_input.next();
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
    // all menu methods
    public static void mainMenu() {
        System.out.printf(mainMenu);
        int mainMenuChoice = getMainMenuChoice(user_input);

        while (!quit) {

            switch (mainMenuChoice) {
                case 1:
                    System.out.println("you chose 1");
                    System.out.printf(mainMenu);
                    mainMenuChoice = getMainMenuChoice(user_input);

                    break;

                case 2:
                    //System.out.println("you chose 2");
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

        System.out.printf(subMenu);
        char subMenuChoice = getSubMenuChoice(user_input);

        while (!returnToMainMenu) {

            switch (subMenuChoice) {
                case 'a':
                    showSignUpStatistics();
                    System.out.printf(subMenu);
                    subMenuChoice = getSubMenuChoice(user_input);
                    break;

                case 'b':
                    showAllSignUps();
                    System.out.printf(subMenu);
                    subMenuChoice = getSubMenuChoice(user_input);
                    break;

                case 'c':
                    showNoTeamSignUps();
                    System.out.printf(subMenu);
                    subMenuChoice = getSubMenuChoice(user_input);
                    break;

                case 'd':
                    showDetailedTeamInfo();
                    System.out.printf(subMenu);
                    subMenuChoice = getSubMenuChoice(user_input);
                    break;

                case 'e':
                    System.out.println("you chose e");
                    System.out.printf(subMenu);
                    subMenuChoice = getSubMenuChoice(user_input);
                    break;

                case 'f':
                    returnToMainMenu = true;
                    mainMenu();
                    break;

            }
        }
    }

    public static void signUpMenu(){
        System.out.printf(signUpText);
        int signUpChoice = getSignUpChoice(user_input);
        int teamChoice = getTeamChoice(user_input);        
        while (!noSignUp) {
            switch (signUpChoice) {
                case 1:
                    
                    break;

                case 2:
                    showTeams();                    
                    teamChoice = getTeamChoice(user_input);

                    break;

                case 3:
                    noSignUp = true;
                    mainMenu();
                    break;
            
                default:
                    System.out.println("Select a signup option");
                    break;
            }
        }
    }

    public static void searchMenu(){
        System.out.printf(searchText);
        int searchTerm = getSearchTerm(user_input);
        String searchValue = getSearchValue(user_input);

        while (!noSearch){
            switch (searchTerm){
                case 1:
                    lookUpInfoByStudentId();
                    break;
                case 2:
                    lookUpInfoByFirstName();
                    break;
                case 3:
                    lookUpInfoByLastName();
                    break;
                case 4:
                    lookUpInfoByAge();
                    break;
                case 5:
                    lookUpInfoByDateOfBirth();
                    break;
                case 6:
                    lookUpInfoByEmail();
                    break;
                case 7:
                    lookUpInfoByAdres();
                    break;
                case 8:
                    lookUpInfoByMobileNumber();
                    break;
                case 9:
                    noSearch = true;
                    subMenu();
                    break;
            }
        }

    }
    // all other methods
    public static void signUp(){
        //signUpMenu();


    }

    //subMenu methods
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
            rs_3.close();

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
        System.out.printf(detailedTeamInfo);
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

        } catch (SQLException e) {
            // Catch SQL Exception
            e.printStackTrace();
        }
    }
    // searchMenu methods     
    public static void lookUpInfoByStudentId(){
        System.out.println("Enter Student ID:");
        String searchValue = getSearchValue(user_input);
        Integer studentID;
        /*try {
            studentID = Integer.valueOf(searchValue);
        } catch (Exception e) {
            // Catch format error
            e.printStackTrace();
        }*/

        try(
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement pstmt_2 = conn.prepareStatement(SQL_10);
        ){
            studentID = Integer.valueOf(searchValue);
            pstmt_2.setInt(1,studentID);
            ResultSet rs_7 = pstmt_2.executeQuery();
            System.out.printf("-----------------------------------------");
            System.out.printf("Showing search results for StudentID: %d",studentID);
            System.out.printf("-----------------------------------------");
            System.out.printf("|%-20s |%-3s |%-10s |%-10s |% |% | ");
            while(rs_7.next()){

            }
        } catch(SQLException e){
            // Catch SQL Exception
            e.printStackTrace();
        }
        finally{}
    }
    public static void lookUpInfoByFirstName(){}
    public static void lookUpInfoByLastName(){}
    public static void lookUpInfoByAge(){}
    public static void lookUpInfoByDateOfBirth(){}
    public static void lookUpInfoByEmail(){}
    public static void lookUpInfoByAdres(){}
    public static void lookUpInfoByMobileNumber(){}
    

}
