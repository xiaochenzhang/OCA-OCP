package service;

/**
 * 
 * @author xiaochenzhang
 *
 */
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import model.Game;
import dao.GameDao;

public class GameService {

    GameDao gameDao = new GameDao();

    public void getGamesViaPreparedStmt() {
        gameDao.getGames();
    }

    public void getGamePriceViaStoredProcedure(final String name) {
        System.err.println("\nCalling stored procedure...");
        gameDao.getUpperCaseViaStoredProcedure(name);
    }

    public void printRowSetRetrievedGames() {
        gameDao.rowSetGetGame();
    }

    public void deleteLastRow() {
        System.err.println("\nDeleting games...");
        gameDao.rowSetDeleteLastRow();
    }

    public void addNewGame() {
        final Game game = new Game();
        // random id just for demo
        final int id = ThreadLocalRandom.current().nextInt(10, 30);
        game.setId(id);
        game.setName("New Game " + id);
        game.setPrice("" + id);
        game.setReview("There is no review.");
        gameDao.rowSetAddGame(game);
    }

    public void transationAddGame() {

        final List<Game> games = new ArrayList<Game>();
        final Game game1 = new Game();
        final Game game2 = new Game();

        game1.setId(31);
        game1.setName("New Game 31");
        game1.setPrice("10");

        game2.setId(32);
        game2.setName("New Game 32");
        game2.setPrice("20");

        games.add(game1);
        games.add(game2);
        // add game2 again, will cause error due to unique name, so roll back to save point
        games.add(game2);

        try {
            System.err.println("\nAdding new games...");
            gameDao.transationAddGame(games);
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

}
