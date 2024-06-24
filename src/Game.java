public class Game {
    private final Player player1;
    private final Player player2;

    public Game() {
        player1 = new Player(new Board(10));
        player2 = new Player(new Board(10));
    }
    public void startOfTheGame(){
        player1.placeAllShips();
        player2.placeAllShips();
    }

}
