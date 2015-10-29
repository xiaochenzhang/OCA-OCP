package service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import model.Game;
import dao.GameDummyDao;

public class GameService {

    final GameDummyDao gameDao = new GameDummyDao();

    /**
     * use of anonymous inner class in sort method
     */
    public List<Game> getGames() {
        return sortGames(gameDao.getCompleteGameInfo());
    }

    public void setDummyGames() {
        gameDao.setCompeteGameInfo();
    }

    /**
     * Anonymous inner class, new a Comparator interface
     */
    private List<Game> sortGames(final List<Game> games) {
        Collections.sort(games, new Comparator<Game>() {

            @Override
            public int compare(final Game game1, final Game game2) {
                if (game1.getName().equals(game2.getName())) {
                    return game1.getPrice().compareTo(game2.getPrice());
                } else {
                    return game1.getName().compareToIgnoreCase(game2.getName());
                }
            }
        });
        return games;
    }

}
