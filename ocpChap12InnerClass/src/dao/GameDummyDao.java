package dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import model.Game;

public class GameDummyDao {

    private final List<Game> gameList = new ArrayList<Game>();

    public void setCompeteGameInfo() {

        final Game game1 = new Game();
        final Game game2 = new Game();
        final Game game3 = new Game();

        /**
         * instantiate inner class object from outside outer class
         */
        final Game.Designer gameDesigner1 = game1.new Designer();
        final Game.Designer gameDesigner2 = game2.new Designer();
        final Game.Designer gameDesigner3 = game3.new Designer();

        gameDesigner1.setName("Shigeru Miyamoto");
        gameDesigner2.setName("Jean-Louis Roubira");
        gameDesigner3.setName("Julian Gollop, Dave Ellis");

        game1.setName("Mario");
        game1.setPrice(new BigDecimal("25.9"));
        game1.setDesigner(gameDesigner1);

        game2.setName("Dixit");
        game2.setPrice(new BigDecimal("25"));
        game2.setDesigner(gameDesigner2);

        game3.setName("X-com");
        game3.setPrice(new BigDecimal("35.99"));
        game3.setDesigner(gameDesigner3);

        gameList.add(game1);
        gameList.add(game2);
        gameList.add(game3);
    }

    public List<Game> getCompleteGameInfo() {
        return gameList;
    }

}
