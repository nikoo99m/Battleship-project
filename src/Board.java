import java.util.Arrays;

public class Board {
    private final int length;
    private final char[][] board;
    public static final char WATER = '.';
    public static final char HIT = 'h';
    public static final char MISS = 'm';
    public static final char SHIP = 's';

    public Board(int length) {
        this.length = length;
        board = initialBoard();
    }

    public Board(char[][] board) {
        this.length = board.length;
        this.board = board;
    }

    public int getLength() {
        return length;
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

    public char getCellStatus(Location location) {
        int row = location.getRow();
        int column = location.getColumn();
        if (isValidPosition(row, column)) {
            return board[row][column];
        }
        throw new IllegalArgumentException("Invalid location");
    }

    public boolean updateCellStatus(char status, Location location) {
        int row = location.getRow();
        int column = location.getColumn();
        if (isValidPosition(row, column)) {
            board[row][column] = status;
            return true;
        }
        return false;
    }

    public boolean hasShip(Location location) {
        return isStatusAtPosition(location, SHIP);
    }

    public boolean hasWater(Location location) {
        return isStatusAtPosition(location, WATER);
    }

    public boolean hasMiss(Location location) {
        return isStatusAtPosition(location, MISS);
    }

    public boolean hasHit(Location location) {
        return isStatusAtPosition(location, HIT);
    }

    private boolean hasSpace(Ship ship) {
        int shipLength = ship.getSize();
        int startRow = ship.getLocation().getRow();
        int startColumn = ship.getLocation().getColumn();
        int endRow = startRow + (ship.getDirection() == Direction.VERTICAL ? shipLength - 1 : 0);
        int endColumn = startColumn + (ship.getDirection() == Direction.HORIZONTAL ? shipLength - 1 : 0);

        return endRow < length && endColumn < length;
    }

    private boolean isValidPosition(int row, int column) {
        return row >= 0 && row < board.length && column >= 0 && column < board[0].length;
    }

    private boolean isStatusAtPosition(Location location, char status) {
        return getCellStatus(location) == status;
    }


}
