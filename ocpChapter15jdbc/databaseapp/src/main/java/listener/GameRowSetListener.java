package listener;

import java.sql.SQLException;

import javax.sql.RowSet;
import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;

/**
 * 
 * @author xiaochenzhang
 *
 */
public class GameRowSetListener implements RowSetListener {

    @Override
    public void rowChanged(final RowSetEvent event) {
        if (event.getSource() instanceof RowSet) {
            try {
                // Re-execute the query, refreshing the results
                System.out.println("Notification from listener: a row is changed!");
                ((RowSet) event.getSource()).execute();
            } catch (final SQLException se) {
                System.out.println("SQLException during execute");
            }
        }
    }

    @Override
    public void cursorMoved(final RowSetEvent event) {
    }

    @Override
    public void rowSetChanged(final RowSetEvent event) {
    }
}
