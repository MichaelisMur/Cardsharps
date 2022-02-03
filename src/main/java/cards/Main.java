/*
 * cards.Main
 * Program entrance class. Creates and starts Players threads. Outputs the result of the game.
 * Murashkin Mikhail 203
 */
package cards;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static int fairPlayersAmount = -1;      // amount of fair players
    static int cardSharpsAmount = -1;       // amount of cardsharps
    static final int GAME_DURATION = 10000;  // duration of the game in ms

    static ArrayList<Player> playersArrayList = new ArrayList<>();      // list of players
    static ArrayList<Player> fairPlayersArrayList = new ArrayList<>();  // list of fair players only
    static ArrayList<Player> winnersArrayList = new ArrayList<>();      // list of game winners

    public static void main(String[] args) {
        enterAmountOfPlayers();

        Player.totalThreads = fairPlayersAmount + cardSharpsAmount;

        for (int i = 0; i < fairPlayersAmount; i++) {
            playersArrayList.add(new Player(String.format("%s", i)));
        }
        for (int i = 0; i < cardSharpsAmount; i++) {
            playersArrayList.add(new Cardsharp(String.format("%s", i)));
        }

        fairPlayersArrayList = new ArrayList<>(playersArrayList);
        fairPlayersArrayList.removeIf(
                a -> a instanceof Cardsharp
        );

        System.out.println("Starting the game with a duration of " + GAME_DURATION + "ms...");
        Player.startOfGameTime = System.currentTimeMillis();
        for (int i = 0; i < fairPlayersAmount + cardSharpsAmount; i++) {
            playersArrayList.get(i).start();
        }
    }

    static void start() {

    }

    /**
     * Enter amount of players from concole
     */
    static void enterAmountOfPlayers() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter amount of fair players in the game (from 1 to 100):");
        while ((fairPlayersAmount < 1) || (fairPlayersAmount > 100)) {
            try {
                fairPlayersAmount = Integer.parseInt(scan.nextLine());
                if ((fairPlayersAmount < 1) || (fairPlayersAmount > 100))
                    throw new NumberFormatException();
            } catch (Exception e) {
                System.out.println("Wrong input. Positive integer value not more then a hundred is " +
                        "expected. Try once more:");
            }
        }

        System.out.println("Enter amount of cardsharps in the game (from 0 to 100):");
        while ((cardSharpsAmount < 0) || (cardSharpsAmount > 100)) {
            try {
                cardSharpsAmount = Integer.parseInt(scan.nextLine());
                if((cardSharpsAmount < 0) || (cardSharpsAmount > 100))
                    throw new NumberFormatException();
            } catch (Exception e) {
                System.out.println(
                        "Wrong input. Non-negative integer value not more then a hundred is " +
                                "expected. Try once more:");
            }
        }
    }

    /**
     * Finds all the players with max amount of points
     */
    static void findWinner() {
        int winnerPoints = -1;
        for (Player player : playersArrayList) {
            if (player.getAmountOfPoints() < winnerPoints) {
                continue;
            } else if (player.getAmountOfPoints() > winnerPoints) {
                winnersArrayList.clear();
            }
            winnerPoints = player.getAmountOfPoints();
            winnersArrayList.add(player);
        }

        printWinners();
    }

    /**
     * Prints all the winners
     */
    static void printWinners() {
        System.out.println("\nList of the winners:");
        for (Player player : winnersArrayList) {
            System.out.println(player + " (" + player.getAmountOfPoints() + " points)");
        }
    }
}
