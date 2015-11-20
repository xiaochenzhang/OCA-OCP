package dao.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * 
 * @author xiaochenzhang
 *
 */
public class DaoUtil {

    public static String formatString(final String s, int n) {
        if (s.length() <= n)
            n++;
        return String.format("%1$-" + n + "s", s);
    }

    public static void printData(final ResultSet resultSet, final String objName) {
        try {
            final ResultSetMetaData metaData = resultSet.getMetaData();
            final int noOfCols = metaData.getColumnCount();
            System.err.println("\nPrinting data retrieved using " + objName);

            for (int i = 1; i <= noOfCols; i++) {
                System.out.printf("%-20s\t", metaData.getColumnName(i));
            }
            System.out.println();

            while (resultSet.next()) {
                for (int i = 1; i <= noOfCols; i++) {
                    System.out.printf("%-20s\t", resultSet.getObject(i));
                }
                System.out.println();
            }
        } catch (final SQLException ex) {
            ex.printStackTrace();
        }
    }

}
