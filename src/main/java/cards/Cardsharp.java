/*
 * cards.Cardsharp
 * Is a cards.Player class extension. Describes behavior of a cardsharp in the program.
 * Murashkin Mikhail 203
 */
package cards;

public class Cardsharp extends Player {

    private boolean rested = false;
    public Cardsharp(String name) {
        super(name);
    }

    /**
     * takes card or steals from fair player; then goes to sleep
     */
    @Override
    public void doAction() throws InterruptedException {
        int sleep;
        if (rested && (Player.random.nextInt(10) < 1)) {
            amountOfPoints += stealFromPlayer();
            sleep = Player.random.nextInt(121) + 180;
        } else {
            amountOfPoints += Player.takeCard();
            sleep = Player.random.nextInt(101) + 100;
        }
        Thread.sleep(sleep);
        rested = true;
    }

    /**
     * steals points from player
     * @return amount of points stolen
     */
    static public int stealFromPlayer() throws InterruptedException {
        synchronized(Player.LOCK_OBJ) {
            if (System.currentTimeMillis() - Player.startOfGameTime < Main.GAME_DURATION) {
                Player playerToStealFrom = Main.fairPlayersArrayList.get(
                        Player.random.nextInt(Main.fairPlayersArrayList.size())
                );

                return playerToStealFrom.stealSomePoints(
                        Player.random.nextInt(9)
                );
            }

            throw new InterruptedException("Game ended");
        }
    }
}
