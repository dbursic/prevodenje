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

    private static final String INSERT_MODUL = "INSERT INTO smet_moduli_obuka(sifra, oznaka, naziv) VALUES(?,'FMB',?)";

    public OracleService(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void saveModul(String sifra, String naziv) {
        Connection connection = null;
        PreparedStatement insertStatement = null;
        ResultSet rs = null;

        int count;
        try {
            connection = connectionProvider.getConnection();

            insertStatement = connection.prepareStatement(INSERT_MODUL);
            insertStatement.clearParameters();
            insertStatement.setString(1, sifra.toUpperCase());
            insertStatement.setString(2, naziv);
            insertStatement.execute();

            System.out.println("Spremljen modul " + sifra);

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                if (null != rs)
                    rs.close();
                if (null != insertStatement)
                    insertStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
