package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

/**
 * 
 * @author xiaochenzhang
 *
 */
public class ConnectionManager {

    private static final Logger LOG = Logger.getLogger(ConnectionManager.class.getName());

    private static final ConnectionManager INSTANCE = new ConnectionManager();

    final String url = "jdbc:postgresql://localhost:5432/designpattern";

    final String user = "postgres";

    final String password = "qafe";

    private Connection postgresConnection;

    private ConnectionManager() {
        init();
        addShutdownHook();
    }

    public static ConnectionManager getConnectionManager() {
        return INSTANCE;
    }

    private void init() {
        try {
            // JDBC API DriverManager: factory pattern
            postgresConnection = DriverManager.getConnection(url, user, password);
        } catch (final SQLException e) {
            LOG.log(Level.SEVERE, "Problem during getting Postgres database connection.");
        }

    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                try {
                    postgresConnection.close();
                } catch (final SQLException e) {
                    LOG.log(Level.SEVERE, "Closing database connection caused a problem.");
                }
            }
        });
    }

    public Connection getPostgresConnection() throws SQLException {
        if (postgresConnection.isClosed()) {
            // open connection in case of closed
            postgresConnection = DriverManager.getConnection(url, user, password);
        }
        return postgresConnection;
    }

    public JdbcRowSet getJdbcRowSet() {
        // Construct a JdbcRowSet object in a try-with-resources statement
        try (JdbcRowSet jrs = RowSetProvider.newFactory().createJdbcRowSet()) {
            jrs.setUrl(url);
            jrs.setUsername(user);
            jrs.setPassword(password);
            return jrs;
        } catch (final SQLException e) {
            LOG.log(Level.WARNING, "An exception has occur during deletion.");
        }
        return null;
    }
}
