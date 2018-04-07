package utility;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    private Connection connect;

    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());

    public void connectToDB() {
        String url = "jdbc:sqlserver://gomoku-database-server.database.windows.net:1433;database=GomokuDatabase;user=ktosowaty;password=Hasyp12345;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        try {
            connect = DriverManager.getConnection(url);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

    }
    public void disconnectDB() {
        try {
            connect.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public Connection getConnection() {
        return connect;
    }
}
