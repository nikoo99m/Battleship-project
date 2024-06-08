public class Board {
    private int length = 10;
    private char[][] board;
    public static final char WATER = '.';

    public Board() {
        board = initialBoard();
    }

    private char[][] initialBoard() {
        char[][] board = new char[length][length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0 ; j < board[i].length; j++) {
                board[i][j] = WATER;
            }
        }
        return board;
    }

    public void printBoard() {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}
