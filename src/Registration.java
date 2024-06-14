import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

// Het aanmaken van een registration class
public class Registration {

    private Connection connection;

    public Registration() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }
    // Voor het registreren van de data (Referentie stack overflow)
    public void registerUser(String voornaam, String achternaam, String geboortedatum, String favorieteIT) {
        String sqlPerson = "INSERT INTO person (voornaam, achternaam, geboorte_datum) VALUES (?, ?, ?)";
        String sqlICTSkills = "INSERT INTO ict_vaardigheid (omschrijving, student_id) VALUES (?, ?)";
    
        try (PreparedStatement statementPerson = connection.prepareStatement(sqlPerson, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement statementICTSkills = connection.prepareStatement(sqlICTSkills)) {
    
            connection.setAutoCommit(true); // (Transactie controle) update de info in de database
    
            // Invoeren van data in de person tabel
            statementPerson.setString(1, voornaam);
            statementPerson.setString(2, achternaam);
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                statementPerson.setDate(3, new java.sql.Date(sdf.parse(geboortedatum).getTime()));
            } catch (ParseException e) {
                System.out.println("Ongeldige input: Verkeerde datum formaat. Gebruik aub yyyy-MM-dd.");
                return;
            }
    
            int affectedRows = statementPerson.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Registratie mislukt probeer het nog eens.");
            }
    
            // De gegenereerde sleutel ophalen (student_id)
            try (ResultSet generatedKeys = statementPerson.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int student_id = generatedKeys.getInt(1);
    
                    // Invoeren data in de ict_vaardigheid tabel
                    statementICTSkills.setString(1, favorieteIT);
                    statementICTSkills.setInt(2, student_id);
                    int rowsInserted = statementICTSkills.executeUpdate();
    
                    if (rowsInserted > 0) {
                        System.out.println("U bent succesvol geregistreerd uw student ID is: " + student_id);
                    } else {
                        System.out.println("Fout bij het registreren.");
                    }
                } else {
                    throw new SQLException("Probeer het nog eens.");
                }
            }
    
        } catch (SQLException e) {
            System.out.println("SQL Fout: " + e.getMessage());
        }
    }
    

    public void listUsers() {
        String sql = "SELECT * FROM person";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("User ID: " + resultSet.getInt("student_id") + ", Username: "
                        + resultSet.getString("Voornaam"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Het oproepen van de view teamleden
    public void viewInfo1(String teamLeden) {
        String sql = "SELECT * FROM " + teamLeden;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Het weergeven van de kolommen
                System.out.println("Voornaam: " + resultSet.getString("voornaam") + ", Achternaam:"
                        + resultSet.getString("achternaam") + ", Team: "
                        + resultSet.getString("team"));
            }
        } catch (SQLException e) {
            System.out.println("Het oproepen van de data mislukt:: " + e.getMessage());
        }
    }
    // Het oproepen van de view teamleden adres
    public void viewInfo2(String teamLeden_Adres) {
        String sql = "SELECT * FROM " + teamLeden_Adres;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Het weergeven van de kolommen
                System.out.println("Voornaam: " + resultSet.getString("voornaam") + ", Achternaam:"
                        + resultSet.getString("achternaam") + ", Team: "
                        + resultSet.getString("team") + ", Verblijfplaats: "
                        + resultSet.getString("adres"));
            }
        } catch (SQLException e) {
            System.out.println("Het oproepen van de data mislukt:: " + e.getMessage());
        }
    }
    // Het oproepen van de view teamleden vaardigheden
    public void viewInfo3(String teamLeden_vaardigheden) {
        String sql = "SELECT * FROM " + teamLeden_vaardigheden;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Het weergeven van de kolommen
                System.out.println("Voornaam: " + resultSet.getString("voornaam") + ", Achternaam:"
                        + resultSet.getString("achternaam") + ", Team: "
                        + resultSet.getString("team") + ", Favoriete ICT vaardigheid: "
                        + resultSet.getString("omschrijving"));
            }
        } catch (SQLException e) {
            System.out.println("Het oproepen van de data mislukt: " + e.getMessage());
        }
    }
    
}
