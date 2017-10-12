import hr.istratech.prevodenje.persistence.ConnectionProvider;

import java.sql.*;

/**
 * Created by dbursic on 3.10.2017..
 */
public class JdbcTest {

    private static String statementCreateTableSQL = "CREATE TABLE JDBC_TEST("
            + "ID NUMBER(5) NOT NULL, "
            + "USERNAME VARCHAR(20) NOT NULL, "
            + "USERNAME2 VARCHAR(20), "
            + "PRIMARY KEY (ID) "
            + ")";


    private static String statementCreateProcedureSQL = "CREATE OR REPLACE PROCEDURE insertJDBC_TEST(\n" +
            "\t   p_userid IN JDBC_TEST.ID%TYPE,\n" +
            "\t   p_username IN JDBC_TEST.USERNAME%TYPE)\n" +
            "IS\n" +
            "BEGIN\n" +
            "\n" +
            "  INSERT INTO JDBC_TEST (\"ID\", \"USERNAME\")\n" +
            "  VALUES (p_userid, p_username);\n" +
            "\n" +
            "  COMMIT;\n" +
            "\n" +
            "END;";

    private static String callableStatementCallProcedure = "{CALL insertJDBC_TEST (?, ?)}";

    private static String preparedStatementInsertSQL = "INSERT into JDBC_TEST"
            + "(ID, username, username2) VALUES (?,?, ?)";

/*

    There are three different kinds of statements:

    Statement: Used to implement simple SQL statements with no parameters.
    PreparedStatement: (Extends Statement.) Used for precompiling SQL statements that might contain input parameters. See Using Prepared Statements for more information.
    CallableStatement: (Extends PreparedStatement.) Used to execute stored procedures that may contain both input and output parameters. See Stored Procedures for more information.

    To execute a query, call an execute method from Statement such as the following:

    execute: Returns true if the first object that the query returns is a ResultSet object. Use this method if the query could return one or more ResultSet objects. Retrieve the ResultSet objects returned from the query by repeatedly calling Statement.getResultSet.
    executeQuery: Returns one ResultSet object.
    executeUpdate: Returns an integer representing the number of rows affected by the SQL statement. Use this method if you are using INSERT, DELETE, or UPDATE SQL statements.

*/


    public static void main(String[] args) {
        ConnectionProvider connectionProvider = new ConnectionProvider("iii-oracle-test", "1521", "ORCL", "i3centar", "centar");

        try {
            printMetadata(connectionProvider);

            statementDropOnEnd(connectionProvider);

            /* Used to implement simple SQL statements with no parameters. */
            statementExample(connectionProvider);

            /* Used for precompiling SQL statements that might contain input parameters. */
            preparedStatementExample(connectionProvider);

            /* Used to execute stored procedures that may contain both input and output parameters. */
            callableStatementExample(connectionProvider);

            /* ResultSet example */
            viewTable(connectionProvider);

            /* Oèisti bazu na kraju */
            // statementDropOnEnd(connectionProvider);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connectionProvider.closeSafely();
    }

    private static void viewTable(ConnectionProvider connectionProvider) throws SQLException {
        Statement statement = null;
        String query = "select * FROM JDBC_TEST";
        try {
            statement = connectionProvider.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String username = rs.getString("USERNAME");
                String username2 = rs.getString("USERNAME2");
                System.out.println(id + "  " + username + "  " + username2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    private static void statementExample(ConnectionProvider connectionProvider) throws SQLException {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = connectionProvider.getConnection();
            stmt = connection.createStatement();

            stmt.executeUpdate(statementCreateTableSQL);
            System.out.println("Tablica kreirana!");

            stmt.executeUpdate(statementCreateProcedureSQL);
            System.out.println("Procedura kreirana!");
        } catch (SQLException e) {
            throw e;
        } finally {
            connectionProvider.closeSafely(connection, stmt);
        }
    }


    private static void callableStatementExample(ConnectionProvider connectionProvider) throws SQLException {
        Connection connection = null;
        CallableStatement cstmt = null;
        try {
            connection = connectionProvider.getConnection();
            cstmt = connection.prepareCall(callableStatementCallProcedure);
            cstmt.setInt(1, 88);
            cstmt.setString(2, "foo");
            int rowsInserted = cstmt.executeUpdate();

            System.out.println(rowsInserted + " Record is inserted into JDBC_TEST table!");
        } catch (SQLException e) {
            throw e;
        } finally {
            connectionProvider.closeSafely(connection, cstmt);
        }
    }

    private static void preparedStatementExample(ConnectionProvider connectionProvider) throws SQLException {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = connectionProvider.getConnection();
            pstmt = connection.prepareStatement(preparedStatementInsertSQL);
            pstmt.setInt(1, 87);
            pstmt.setString(2, "Picard");
            pstmt.setString(3, "Richard");
            int rowsInserted = pstmt.executeUpdate();

            System.out.println(rowsInserted + " Record is inserted into JDBC_TEST table!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionProvider.closeSafely(connection, pstmt);
        }
    }


    private static void statementDropOnEnd(ConnectionProvider connectionProvider) throws SQLException {
        Connection connection = null;
        Statement stmt = null;
        try {
            String dropTableSQL = "DROP TABLE JDBC_TEST";
            String dropProcedureSQL = "DROP PROCEDURE insertJDBC_TEST";

            connection = connectionProvider.getConnection();
            stmt = connection.createStatement();

            stmt.executeUpdate(dropProcedureSQL);
            System.out.println("Drop procedure!");
            stmt.executeUpdate(dropTableSQL);
            System.out.println("Drop tablice!");
        } catch (SQLException e) {
            throw e;
        } finally {
            connectionProvider.closeSafely(connection, stmt);
        }

    }

    private static void printMetadata(ConnectionProvider connectionProvider) throws SQLException {
        DatabaseMetaData dbmd = connectionProvider.getConnection().getMetaData();
        System.out.println("Connected with " +
                dbmd.getDriverName() + " " + dbmd.getDriverVersion()
                + "{ " + dbmd.getDriverMajorVersion() + "," +
                dbmd.getDriverMinorVersion() + " }" + " to " +
                dbmd.getDatabaseProductName() + " " +
                dbmd.getDatabaseProductVersion() + "\n");


    }
}
