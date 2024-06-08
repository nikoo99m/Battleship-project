import java.util.Arrays;

public class Board {
    private final int length;
    private char[][] board;
    public static final char WATER = '.';

    public Board(int length){
        this.length = length;
        board = initialBoard();
    }
    public Board(char[][] board){
        this.length = board.length;
        this.board = board;
    }

    private char[][] initialBoard() {
        return Arrays.stream(new char[length][length])
                .map(row -> {
                    Arrays.fill(row, WATER);
                    return row;
                })
                .toArray(char[][]::new);
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
