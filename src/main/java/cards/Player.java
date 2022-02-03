/*
 * cards.Player
 * Is a Thread class extension. Describes behavior of every player in the program.
 * Murashkin Mikhail 203
 */
package cards;
import java.util.Random;

public class Player extends Thread{
    static Random random = new Random();
    static long startOfGameTime;
    static int threadsFinished = 0;
    static int totalThreads;
    static final Object LOCK_OBJ = new Object();

    protected int amountOfPoints = 0;
    public int getAmountOfPoints() {
        return amountOfPoints;
    }

    protected String playerName;
    protected int stealSomePoints(int amountToSteal) {
        int newAmount = (this.amountOfPoints >= amountToSteal)
                ? this.amountOfPoints - amountToSteal
                : 0;
        return this.amountOfPoints - (this.amountOfPoints = newAmount);
    }

    public Player(String name) {
        this.playerName = name;
    }

    /**
     * Take card from deck
     * @return amount of points
     */
    static int takeCard() throws InterruptedException {
        synchronized (LOCK_OBJ) {
            if (System.currentTimeMillis() - startOfGameTime < Main.GAME_DURATION) {
                return random.nextInt(10) + 1;
            }

            throw new InterruptedException("Game ended");
        }
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                doAction();
            } catch (InterruptedException e) {
                if (++threadsFinished == totalThreads) {
                    Main.findWinner();
                }
                return;
            }
        }
    }

    /**
     * takes card and thread goes to sleep
     */
    public void doAction() throws InterruptedException {
        amountOfPoints += takeCard();
        int sleep = random.nextInt(101) + 100;
        Thread.sleep(sleep);
    }

    @Override
    public String toString() {
        return ((this instanceof Cardsharp) ? "[Cardsharp " : "[Fair player ")
                + playerName + "]";
    }
}
