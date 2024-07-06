import java.util.Objects;
    public class Game {
        private final Player player1;
        private final Player player2;

        public Game() {
            player1 = new HumanPlayer(new Board(10));
            player2 = new HumanPlayer(new Board(10));
        }

        public void startOfTheGame() {
            player1.placeAllShips();
            player2.placeAllShips();
        }

        private void turn(Player player, Player enemy) {
            boolean hit;
            do {
                System.out.println((player == player1 ? "Player 1" : "Player 2") + "'s turn:");
//              System.out.println(player.getClass().getSimpleName() + "'s turn:");
                player.shoot(enemy.getBoard());

                Location lastShot = player.getLastShot();
                hit = enemy.getBoard().hasHit(lastShot) ;
            } while (hit);
        }

    }

