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
                String colorCode = "";



                switch (status) {
                    case Cell.SHIP:
                        colorCode = cell.colourCode;
                        break;
                    case Cell.WATER:
                        colorCode = "\u001B[36m"; // Cyan color for water
                        break;
                    case Cell.MISS:
                        colorCode = "\u001B[37m"; // White color for misses
                        break;
                    case Cell.HIT:
                        colorCode = "\u001B[31m"; // Red color for hits
                        break;
                    default:
                        colorCode = "\u001B[0m"; // Reset color
                        break;
                }

                System.out.print(colorCode + status + " ");
            }
            System.out.println("\u001B[0m"); // Reset color at the end of each row
        }
    }

    public char getCellStatus(Location location) {
        int row = location.getRow();
        int column = location.getColumn();
        if (isValidPosition(row, column)) {
            return board[row][column].getStatus();
        }
        throw new IllegalArgumentException("Invalid location");
    }

    public boolean updateCellStatus(char status, Location location) {
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
            // Handle the case where the ship cannot be placed
            System.out.println("Cannot place ship at the given location. Try a different location.");
        }
    }


    public boolean addHit(Location location) {
        int x = location.getRow();
        int y = location.getColumn();
        char current = board[x][y].getStatus();
        if (current == Cell.HIT || current == Cell.MISS) {
            return false;
        }

        if (current == Cell.SHIP) {
            numShips--;

            return updateCellStatus(Cell.HIT, location);
        }

        if (current == Cell.WATER) {

            return updateCellStatus(Cell.MISS, location);
        }

        return false;
    }

    public boolean isShipSunk(Ship ship) {
        Location location = ship.getLocation();
        Direction direction = ship.getDirection();
        int row = location.getRow();
        int column = location.getColumn();
        if (direction == Direction.HORIZONTAL) {
            for (int i = 0; i < ship.getSize(); i++) {
                if (board[row][column + i].getStatus() != Cell.HIT) {
                    return false;
                }
            }
        } else if (direction == Direction.VERTICAL) {
            for (int i = 0; i < ship.getSize(); i++) {
                if (board[row + i][column].getStatus() != Cell.HIT) {
                    return false;
                }
            }
        }
        return true;
    }
}

