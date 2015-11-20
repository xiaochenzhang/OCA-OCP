package chapter15Jdbc.databaseapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import service.GameService;

/**
 * 
 * @author xiaochenzhang
 *
 */
public class App {

    public static void main(final String[] args) throws InterruptedException {
        final GameService gameService = new GameService();

        while (true) {
            Thread.sleep(100);
            printCommands();
            Thread.sleep(100);
            final String command = getUserInput("Please select a command.");

            switch (command) {
                case "1":
                    gameService.getGamesViaPreparedStmt();
                    break;
                case "2":
                    gameService.printRowSetRetrievedGames();
                    break;
                case "3":
                    gameService.addNewGame();
                    break;
                case "4":
                    gameService.deleteLastRow();
                    break;
                case "5":
                    gameService.transationAddGame();
                    break;
                case "6":
                    gameService.getGamePriceViaStoredProcedure("Hearthstone");
                    break;
                default:
                    System.out.println("Invalid command.");
                    break;
            }
        }
    }

    private static void printCommands() {
        System.out.println("**************************************************************");
        System.out.println("* Selete one of the following commands:");
        System.out.println("* 1. use PrepareStatement to get list of games in the database");
        System.out.println("* 2. use RowSet to get list of games");
        System.out.println("* 3. use RowSet to add a record");
        System.out.println("* 4. use RowSet to delete a record");
        System.out.println("* 5. use transation to insert some records");
        System.out.println("* 6. use CallableStatement to call stored procedure");
        System.out.println("**************************************************************");

    }

    private static String getUserInput(final String message) {
        System.out.println(message);
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            return br.readLine();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return "Invalid input.";
    }
}
