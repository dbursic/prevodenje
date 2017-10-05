package hr.istratech.prevodenje.persistence;

import oracle.jdbc.driver.OracleDriver;

import java.sql.*;

/**
 * Created by dbursic on 3.10.2017..
 */
public class ConnectionProvider {
    private String server;
    private String port;
    private String sid;
    private String username;
    private String password;
    private Connection connection;

    public ConnectionProvider(String server, String port, String sid, String username, String password) {
        this.server = server;
        this.port = port;
        this.sid = sid;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        if (null != connection)
            return connection;

        // Register the JDBC driver: Requires that you initialize a driver so you can open a communication channel with the database.
        // Note that this is unnecessary if you are using a JDBC 4.0 driver with Java SE 6 or above
        // DriverManager.registerDriver(new OracleDriver());

        // Open a connection: Requires using the DriverManager.getConnection() method to create a Connection object, which represents a physical connection with the database.
        connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@" + server + ":" + port + ":" + sid, username, password);
        // connection.setAutoCommit(false);

        return connection;
    }

    public void closeSafely(Connection connection, Statement statement) throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }

    public void closeSafely()  {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
