package app;

import java.util.List;

import model.Game;
import service.GameService;

public class InnerClassApp {

    public static void main(final String[] args) {

        final GameService gameService = new GameService();
        gameService.setDummyGames();

        /**
         * use of anonymous inner class in getGames() method
         */
        final List<Game> gameList = gameService.getGames();

        System.err.println("Following games are in stock (sorted by price then name): ");
        for (final Game game : gameList) {
            /**
             * method-local class Printer in printMe() method
             */
            game.printMe();

            System.out.print(game.getName() + ", ");

            /**
             * use inner class from outside of outer class
             */
            final Game.Designer designer = game.getDesigner();
            designer.seeGamePrice();

            /**
             * use of static nested class
             */
            final Game.SeparaterPrinter sp = new Game.SeparaterPrinter();
            System.out.println(sp.toString());
        }

    }
}
