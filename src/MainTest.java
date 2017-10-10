import oracle.forms.jdapi.Alert;
import oracle.forms.jdapi.FormModule;
import oracle.forms.jdapi.Jdapi;
import oracle.forms.jdapi.JdapiIterator;

import java.io.File;
import java.sql.*;

/**
 * Created by dbursic on 10.10.2017..
 */
public class MainTest {
    public static void main(String[] args) {
        System.out.println("Tests");

        File sourceDirectory = new File("D:\\Projekti\\java\\prevodenje2\\resources");
        String filename = "zmp0010.fmb";

        FormModule formModule = FormModule.open(sourceDirectory.getPath() + "/" + filename);

        String formModuleName = formModule.getName();

        String formModuleNameTitle = formModule.getTitle();

//        changeFormTitle(sourceDirectory, filename, formModule, formModuleNameTitle);

//        saveToDatabase(formModuleName, formModuleNameTitle);

//        readFromDatabase();

        System.out.println("Datoteka: " + filename + "; Forma:" + formModuleName + "; Naslov:" + formModuleNameTitle);

        readFormProperties(formModule);

        Jdapi.shutdown();
    }

    private static void readFormProperties(FormModule formModule) {
        JdapiIterator alerts = formModule.getAlerts();
        while (alerts.hasNext()) {
            Alert alert = (Alert) alerts.next();
            System.out.println("Alert: " + alert.getName() + " " + alert.getTitle());
        }
    }

    private static void readFromDatabase() {
        Statement selectStatement = null;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@" + "iii-oracle-test" + ":" + "1521" + ":" + "ORCL", "i3centar", "centar");

            selectStatement = connection.createStatement();
            ResultSet resultSet = selectStatement.executeQuery("SELECT * FROM smet_moduli_obuka");

            int i = 1;
            while(resultSet.next()) {
                System.out.println(i++ + " redak: " + resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3));
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                if (null != selectStatement)
                    selectStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (null != connection)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private static void saveToDatabase(String formModuleName, String formModuleNameTitle) {
        PreparedStatement insertStatement = null;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@" + "iii-oracle-test" + ":" + "1521" + ":" + "ORCL", "i3centar", "centar");

            insertStatement = connection.prepareStatement("INSERT INTO smet_moduli_obuka(sifra, oznaka, naziv) VALUES(?, 'FMB', ?)");
            insertStatement.clearParameters();
            insertStatement.setString(1, formModuleName);
            insertStatement.setString(2, formModuleNameTitle);
            int count = insertStatement.executeUpdate();

            System.out.println("Spremljeno modula " + count + "! (" + formModuleName + ")");

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                if (null != insertStatement)
                    insertStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (null != connection)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private static void changeFormTitle(File sourceDirectory, String filename, FormModule formModule, String formModuleNameTitle) {
        formModule.setTitle(formModuleNameTitle + " (changed)");
        formModule.save(sourceDirectory.getPath() + "/" + filename);
    }

}
