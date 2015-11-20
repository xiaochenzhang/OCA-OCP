package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.rowset.JdbcRowSet;

import listener.GameRowSetListener;
import model.Game;
import connection.ConnectionManager;
import connection.util.DatabaseUtil;
import dao.util.DaoUtil;

/**
 * 
 * @author xiaochenzhang
 *
 */
public class GameDao {

    private static final Logger LOG = Logger.getLogger(GameDao.class.getName());

    private final ConnectionManager connectionManager = ConnectionManager.getConnectionManager();

    private static final String GET_GAMES_QUERY =
        "select id, name, price, review from games order by upper(name)";

    private static final String SQL_UPPER_CALL = "{? = call upper(?)}";

    private static final String ROW_SET_SELECT_QUERY = "select * from games order by upper(name)";

    private static final String ROW_BACK_ADD_QUERY = "insert into games (id, name, price) values (?,?,?)";

    public List<Game> getGames() {

        // JDBC API Statement: prepare statement, auto closable
        try (Connection conn = connectionManager.getPostgresConnection();
                PreparedStatement stmt = conn.prepareStatement(GET_GAMES_QUERY);
                ResultSet rs = stmt.executeQuery();) {

            DaoUtil.printData(rs, "PreparedStatement");

            final List<Game> games = new ArrayList<Game>();
            while (rs.next()) {
                final Game game = new Game();
                game.setName(rs.getString("name"));
                game.setPrice(rs.getString("price"));
                game.setReview(rs.getString("review"));
                games.add(game);
            }
            return games;
        } catch (final SQLException e) {
            final String msg = "Could not find database connection details.";
            LOG.log(Level.WARNING, msg);
        }
        return null;
    }

    /**
     * Use CallableStatement
     * 
     * @param name name of the game, in parameter for query
     */
    public void getUpperCaseViaStoredProcedure(final String name) {

        try (Connection conn = connectionManager.getPostgresConnection();
        // use CallableStatement
                CallableStatement callableStmt = conn.prepareCall(SQL_UPPER_CALL);) {

            // register IN: dummy string, register OUT: upper case
            callableStmt.registerOutParameter(1, java.sql.Types.VARCHAR);
            callableStmt.setString(2, name);

            // Use execute method to run stored procedure.
            System.out.println(">>>>>> Executing stored procedure (i.e. upper() function)...");
            callableStmt.execute();

            // get the game price based on the name
            final String upperCase = callableStmt.getString(1);
            System.out.println(">>>>>> Input string: " + name + ", Output string: " + upperCase);

        } catch (final SQLException e) {
            final String msg = "Could not find database connection details.";
            LOG.log(Level.WARNING, msg);
        }

    }

    /**
     * This method is to use JdbcRowSet
     */
    public void rowSetGetGame() {

        final JdbcRowSet jrs = connectionManager.getJdbcRowSet();

        try {
            jrs.setCommand(ROW_SET_SELECT_QUERY);
            jrs.execute();
            DaoUtil.printData(jrs, "JdbcRowSet");
        } catch (final SQLException e) {
            LOG.log(Level.WARNING, "An exception has occur during deletion.");
        }
    }

    public void rowSetDeleteLastRow() {

        final JdbcRowSet jrs = connectionManager.getJdbcRowSet();
        jrs.addRowSetListener(new GameRowSetListener());

        try {
            jrs.setCommand(ROW_SET_SELECT_QUERY);
            jrs.execute();
            jrs.last(); // pointing at the last row
            System.out.println(">>>>>>>> A game is removed using JdbcRowSet: " + jrs.getObject("name"));
            jrs.deleteRow();

        } catch (final SQLException e) {
            LOG.log(Level.WARNING, "An exception has occur during deletion.");
        }
    }

    public void rowSetAddGame(final Game game) {
        if (game == null) {
            return;
        }

        final JdbcRowSet jrs = connectionManager.getJdbcRowSet();
        jrs.addRowSetListener(new GameRowSetListener());

        try {
            jrs.setCommand(ROW_SET_SELECT_QUERY);
            jrs.execute();

            jrs.moveToInsertRow();
            jrs.updateInt("id", game.getId());
            jrs.updateString("name", game.getName());
            jrs.updateString("price", game.getPrice());
            jrs.updateString("review", game.getReview());
            jrs.insertRow();
            jrs.moveToCurrentRow();

            System.out.println(">>>>>>>> A game is added using JdbcRowSet.");
            System.out.println("New Game: " + game.getName());
            DaoUtil.printData(jrs, "JdbcRowSet");
        } catch (final SQLException e) {
            LOG.log(Level.WARNING, "An exception has occur during addition.");
        }
    }

    public void transationAddGame(final List<Game> games) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Savepoint sp1 = null;

        if (games == null || games.size() < 1) {
            return;
        }

        try {
            conn = connectionManager.getPostgresConnection();
            conn.setAutoCommit(false); // Start a transaction

            stmt = conn.prepareStatement(ROW_BACK_ADD_QUERY);

            // first, execute list size-1 query
            for (int i = 0; i < games.size() - 1; i++) {
                stmt.setInt(1, games.get(i).getId());
                stmt.setString(2, games.get(i).getName());
                stmt.setString(3, games.get(i).getPrice());
                stmt.executeUpdate();

                rs = stmt.getResultSet();
                System.out.println(">>>>>> Adding a new game: " + games.get(i).getName());
            }

            // Create a Save point
            sp1 = conn.setSavepoint();
            System.out.println(">>>>>> A save point is created.");

            // then execute the last command
            System.out.println(">>>>>> Adding more games...");
            stmt.setInt(1, games.get(games.size() - 1).getId());
            stmt.setString(2, games.get(games.size() - 1).getName());
            stmt.setString(3, games.get(games.size() - 1).getPrice());
            stmt.executeUpdate();
            System.out.println(">>>>>> A new game is added: " + games.get(games.size() - 1).getName());

            System.out.println(">>>>>> Committing to database...");
            conn.commit();
            System.out.println(">>>>>> Data successfully added to database.");
        } catch (final SQLException e) {
            if (sp1 != null) {
                System.out.println(">>>>>> Rolling back to save point.");
                conn.rollback(sp1);
                conn.commit(); // commit from save point
                System.out.println(">>>>>> Data inserting failed, rolled back to save point.");
            }

            final String msg = "An exeption has occured during addition.";
            LOG.log(Level.WARNING, msg);
        } finally {
            // close all resources
            DatabaseUtil.closeAll(conn, stmt, rs, LOG);
        }
    }

}
