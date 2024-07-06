import java.util.Objects;

public class Board {
    private final int length;
    private final Cell[][] board;
    private int numShips = 0;

    public Board(int length) {
        this.length = length;
        this.board = initialBoard();
    }

    public Board(Cell[][] board) {
        this.length = board.length;
        this.board = board;
    }


    public int getLength() {
        return length;
    }

    private Cell[][] initialBoard() {
        Cell[][] newBoard = new Cell[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                newBoard[i][j] = new Cell();
            }
        }
        return newBoard;
    }

    public void printBoard() {
        for (Cell[] row : board) {
            for (Cell cell : row) {
                char status = cell.getStatus();
                String colorCode = switch (status) {
                    case Cell.SHIP -> cell.colourCode;
                    case Cell.WATER -> "\u001B[34m";
                    case Cell.MISS -> "\u001B[37m";
                    case Cell.HIT -> "\u001B[31m";
                    default -> "\u001B[0m";
                };

                System.out.print(colorCode + status + " ");
            }
            System.out.println("\u001B[0m"); // Reset color at the end of each row
        }
    }

    public String getCellStatus(Location location) {
        int row = location.getRow();
        int column = location.getColumn();
        if (isValidPosition(row, column)) {
            return board[row][column].getStatus();
        }
        throw new IllegalArgumentException("Invalid location");
    }

    public boolean updateCellStatus(String status, Location location) {
        int row = location.getRow();
        int column = location.getColumn();
        if (isValidPosition(row, column)) {
            board[row][column].setStatus(status);
            return true;
        }
        return false;
    }

    public boolean hasShip(Location location) {
        return isStatusAtPosition(location, Cell.SHIP);
    }

    public boolean hasWater(Location location) {
        return isStatusAtPosition(location, Cell.WATER);
    }

    public boolean hasMiss(Location location) {
        return isStatusAtPosition(location, Cell.MISS);
    }

    public boolean hasHit(Location location) {
        return isStatusAtPosition(location, Cell.HIT);
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

    private boolean isStatusAtPosition(Location location, String status) {
        return Objects.equals(getCellStatus(location), status);
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

        if (!hasSpace(ship)) {
            System.out.println("Error, there is not enough space for that ship in that direction");
            return false;
        }

        if (!isShipNear(ship)) {
            System.out.println("Error, there is a ship near");
            return false;
        }

        return true;
    }

    private boolean isShipNear(Ship ship) {
        int startRow = ship.getLocation().getRow();
        int startColumn = ship.getLocation().getColumn();
        int shipLength = ship.getSize();
        int endRow = startRow + (ship.getDirection() == Direction.VERTICAL ? shipLength - 1 : 0);
        int endColumn = startColumn + (ship.getDirection() == Direction.HORIZONTAL ? shipLength - 1 : 0);

        for (int row = startRow - 1; row <= endRow + 1; row++) {
            for (int column = startColumn - 1; column <= endColumn + 1; column++) {
                if (isValidPosition(row, column)) {
                    if (board[row][column].getStatus().equals(Cell.SHIP)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean canPlaceShip(Ship ship, int row, int column) {
        for (int i = 0; i < ship.getSize(); i++) {
            if (ship.getDirection() == Direction.HORIZONTAL) {
                if (column + i >= board[0].length || board[row][column + i].getStatus() != Cell.WATER) {
                    return false;
                }
            } else if (ship.getDirection() == Direction.VERTICAL) {
                if (row + i >= board.length || board[row + i][column].getStatus() != Cell.WATER) {
                    return false;
                }
            }
        }
        return true;
    }

    public void placeShipOnBoard(Ship ship, int row, int column) {
        if (canPlaceShip(ship, row, column)) {
            for (int i = 0; i < ship.getSize(); i++) {
                if (ship.getDirection() == Direction.HORIZONTAL) {
                    board[row][column + i].setStatus(Cell.SHIP);
                    board[row][column + i].colourCode = ship.getColour();
                } else if (ship.getDirection() == Direction.VERTICAL) {
                    board[row + i][column].setStatus(Cell.SHIP);
                    board[row + i][column].colourCode = ship.getColour();
                }
            }
            numShips++;
        } else {

            System.out.println("Cannot place ship at the given location. Try a different location.");
        }
    }

    public boolean addHit(Location location) {
        int x = location.getRow();
        int y = location.getColumn();
        String current = board[x][y].getStatus();

        if (current.equals(Cell.HIT) || current.equals(Cell.MISS)) {
            return false;
        }

        if (current.equals(Cell.WATER)) {
            updateCellStatus(Cell.MISS, location);
            return false;
        }

        return false;
    }

    public boolean isShipSunk(Ship ship) {
        Location location = ship.getLocation();
        Direction direction = ship.getDirection();
        int row = location.getRow();
        int col = location.getColumn();

        for (int c = col; c >= 0; c--) {
            String status = board[row][c].getStatus();
            if (status.equals(Cell.WATER)) {
                break;
            }
            if (status.equals(Cell.SHIP)) {
                return false;
            }
        }

        for (int c = col; c < board[0].length; c++) {
            String status = board[row][c].getStatus();
            if (status.equals(Cell.WATER)) {
                break;
            }
            if (status.equals(Cell.SHIP)) {
                return false;
            }
        }

        for (int r = row; r >= 0; r--) {
            String status = board[r][col].getStatus();
            if (status.equals(Cell.WATER)) {
                break;
            }
            if (status.equals(Cell.SHIP)) {
                return false;
            }
        }

        for (int r = row; r < board.length; r++) {
            String status = board[r][col].getStatus();
            if (status.equals(Cell.WATER)) {
                break;
            }
            if (status.equals(Cell.SHIP)) {
                return false;
            }
        }

        return true;
    }

    public void printBoardForEnemy() {
        for (Cell[] row : board) {
            for (Cell cell : row) {
                String status = cell.getStatus();
                String displayStatus;
                String colorCode;

                if (Objects.equals(status, Cell.SHIP)) {
                    displayStatus = Cell.WATER;
                    colorCode = "\u001B[34m";
                } else {
                    displayStatus = status;
                    colorCode = switch (status) {
                        case Cell.WATER -> "\u001B[34m";
                        case Cell.MISS -> "\u001B[30m";
                        case Cell.HIT -> "\u001B[31m";
                        case Cell.SUNK -> "\u001B[37m";
                        default -> "\u001B[0m";
                    };
                }

                System.out.print(colorCode + String.format("%-5s", displayStatus) + " ");
            }
            System.out.println("\u001B[0m");
        }
    }

}

