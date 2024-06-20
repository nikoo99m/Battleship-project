public class Main {
    public static void main(String[] args){

        Board board = new Board(10);
        Board enemyboard = new Board(10);

        board.printBoard();
        Player player = new Player(board);
        Player enemy = new Player(enemyboard);
        player.placeAllShips();
        enemy.placeAllShips();
        player.shoot(enemyboard);

    }}