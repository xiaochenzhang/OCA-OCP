package connection.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author xiaochenzhang
 *
 */
public final class DatabaseUtil {

    /**
     * This method is to close all database resources
     * 
     * @param connection the database connection
     * @param stmt query statements
     * @param rs result sets
     * @param log
     */
    public static void closeAll(final Connection connection, final Statement stmt, final ResultSet rs,
            final Logger log) {
        try {
            try {
                if (rs != null) {
                    rs.close();
                }
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } finally {
                    if (connection != null) {
                        connection.close();
                    }
                }
            }
        } catch (final SQLException e) {
            log.log(Level.FINE, "Problem closing resource", e);
        }
    }

}
