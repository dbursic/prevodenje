package hr.istratech.prevodenje.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by dbursic on 3.10.2017..
 */
public class OracleService {
    private ConnectionProvider connectionProvider;

    private static final String INSERT_MODUL = "INSERT INTO smet_moduli_obuka(id, sifra, oznaka, naziv) VALUES(?, ?, 'FMB', ?)";


    public OracleService(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void saveModul(String sifra, String naziv) {
        Connection connection = null;
        PreparedStatement insertStatement = null;

        int count;
        try {
            connection = connectionProvider.getConnection();

            insertStatement = connection.prepareStatement(INSERT_MODUL);
            insertStatement.clearParameters();
            insertStatement.setLong(1, 4);
            insertStatement.setString(2, "BBBB");
            insertStatement.setString(3, "BBbbb");
          //  insertStatement.setString(3, "FMB");
            insertStatement.executeUpdate();

            System.out.println("Spremljen modul " + sifra);

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                if (null != insertStatement)
                    insertStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
