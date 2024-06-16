import java.util.Arrays;

public class Board {
    private final int length;
    private final char[][] board;
    public static final char WATER = '.';
    public static final char HIT = 'h';
    public static final char MISS = 'm';
    public static final char SHIP = 's';
    private int numShips = 0;

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
    public boolean addShip(Ship ship) {
        Location location = ship.getLocation();
        int row = location.getRow();
        int column = location.getColumn();

        if (!validateShipLocation(ship, location)) {
            return false;
        }
        placeShipOnBoard(ship, row, column);
        return true;
    }

    private boolean validateShipLocation(Ship ship, Location location) {
        if (hasShip(location)) {
            System.out.println("Error, there is already a ship at that position");
            return false;
        }

        if (!hasSpace(ship)) {
            System.out.println("Error, there is not enough space for that ship in that direction");
            return false;
        }

        return true;
    }

    private void placeShipOnBoard(Ship ship, int row, int column) {
        for (int i = 0; i < ship.getSize(); i++) {
            if (ship.getDirection() == Direction.HORIZONTAL) {
                board[row][column + i] = SHIP;
            } else if (ship.getDirection() == Direction.VERTICAL) {
                board[row + i][column] = SHIP;
            }
        }
        numShips++;
    }
    public boolean addHit(Location location) {
        int x = location.getRow();
        int y = location.getColumn();
        char current = board[x][y];

        if (current == HIT || current == MISS) {
            return false;
        }

        if (current == SHIP) {
            numShips--;

            return updateCellStatus(HIT, location);
        }

        if (current == WATER) {

            return updateCellStatus(MISS, location);
        }

        return false;
    }
}

