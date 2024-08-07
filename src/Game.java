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
            } while (hit && !checkGameOver());
        }

        public void playGame() {
            boolean gameOver = false;
            Player currentPlayer = player1;
            Player enemyPlayer = player2;

            while (!gameOver) {
                turn(currentPlayer, enemyPlayer);


                gameOver = checkGameOver();

                if (!gameOver) {
                    if (currentPlayer == player1) {
                        currentPlayer = player2;
                        enemyPlayer = player1;
                    } else {
                        currentPlayer = player1;
                        enemyPlayer = player2;
                    }
                }
            }
            System.out.println("Game over!");
            System.out.println((currentPlayer == player1 ? "Player 1" : "Player 2") + " wins!");
        }

        private boolean checkGameOver() {

        return player1.getBoard().areAllShipsSunk() || player2.getBoard().areAllShipsSunk();
    }

    public void setDifficulty() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = "";

        System.out.println("╔═══════════════╗  ╔════════════════════╗  ╔════════════════╗");
        System.out.println("║     1. Easy   ║  ║  2. Intermediate   ║  ║  3. Difficult  ║");
        System.out.println("╚═══════════════╝  ╚════════════════════╝  ╚════════════════╝");
        System.out.println();

        do {
            System.out.print("Select your difficulty (1-3): ");
            try {
                input = in.readLine();
            } catch (java.io.IOException e) {
                System.out.println("An error has occurred: " + e.getMessage());
            }
        } while (!Pattern.matches("[123]", input));

        this.difficulty = Integer.valueOf(input);
        System.out.println("You have selected difficulty: " + this.difficulty);
    }

}

