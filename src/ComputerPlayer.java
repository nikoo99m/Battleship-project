import java.util.*;

public class ComputerPlayer extends Player {
    private int boardSize;
    private boolean targetingMode;
    private List<Location> targetCells;
    private Direction targetDirection;
    private Location firstHitLocation;
    private Set<Location> sunkShipLocations;

    public ComputerPlayer(Board board) {
        super(board);
        this.boardSize = board.getLength();
        this.targetingMode = false;
        this.targetCells = new ArrayList<>();
        this.targetDirection = null;
        this.firstHitLocation = null;
        this.sunkShipLocations = new HashSet<>();
    }

    @Override
    public void placeAllShips() {
        DefaultShip defaultShip = new DefaultShip();
        List<Ship> defaultShips = defaultShip.initializeDefaultShip();

        Random random = new Random();

        for (Ship ship : defaultShips) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(boardSize);
                int column = random.nextInt(boardSize);
                Direction direction = random.nextBoolean() ? Direction.HORIZONTAL : Direction.VERTICAL;

                Location location = new Location(column, row);
                ship.setLocation(location);
                ship.setDirection(direction);

                if (board.addShip(ship)) {
                    placed = true;
                }
            }
        }
        board.printBoard();
    }

    @Override
    public void shoot(Board enemyBoard) {
        Location shotLocation;

        while (true) {
            if (targetingMode && !targetCells.isEmpty()) {
                shotLocation = targetCells.remove(0);
            } else {
                shotLocation = selectRandomShot(enemyBoard);
            }

            setLastShot(shotLocation);

            if (enemyBoard.addHit(shotLocation)) {
                System.out.println("Computer hits at (" + shotLocation.getRow() + ", " + shotLocation.getColumn() + ")");
                enemyBoard.printBoardForEnemy();
                if (enemyBoard.isShipSunk(shotLocation)) {
                    System.out.println("Computer sunk a ship!");
                    markSunkShip(shotLocation, enemyBoard);
                    resetTargetingMode();
                    break;
                } else {
                    targetingMode = true;
                    if (firstHitLocation == null) {
                        firstHitLocation = shotLocation;
                        addAdjacentCellsToTarget(shotLocation, enemyBoard);
                    } else if (targetDirection == null) {
                        determineTargetDirection(shotLocation);
                        refineDirectionalTargets(enemyBoard);
                    } else {
                        refineDirectionalTargets(enemyBoard);
                    }
                }
            } else {
                System.out.println("Computer misses at (" + shotLocation.getRow() + ", " + shotLocation.getColumn() + ")");
                enemyBoard.printBoardForEnemy();
                if (targetDirection != null && !targetCells.isEmpty()) {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private Location selectRandomShot(Board enemyBoard) {
        Random random = new Random();
        Location shotLocation;

        do {
            int row = random.nextInt(boardSize);
            int column = random.nextInt(boardSize);
            shotLocation = new Location(column, row);
        } while (isAlreadyShot(shotLocation, enemyBoard)  || isAdjacentToSunkShip(shotLocation, enemyBoard));
        // || isAdjacentToSunkShip(shotLocation, enemyBoard

        return shotLocation;
    }

    private boolean isAdjacentToSunkShip(Location location, Board enemyBoard) {
        int row = location.getRow();
        int col = location.getColumn();

        if (isSunkShip(row - 1, col, enemyBoard)) return true;
        if (isSunkShip(row + 1, col, enemyBoard)) return true;
        if (isSunkShip(row, col - 1, enemyBoard)) return true;
        if (isSunkShip(row, col + 1, enemyBoard)) return true;

        return false;
    }

    private boolean isSunkShip(int row, int col, Board enemyBoard) {
        if (row >= 0 && row < boardSize && col >= 0 && col < boardSize) {
            return enemyBoard.getCellStatus(new Location(col, row)).equals(Cell.SUNK);
        }
        return false;
    }

    private void markSunkShip(Location shotLocation, Board enemyBoard) {
        int row = shotLocation.getRow();
        int col = shotLocation.getColumn();

        // Mark horizontal and vertical directions
        for (int i = col; i >= 0; i--) {
            Location loc = new Location(i, row);
            if (enemyBoard.getCellStatus(loc).equals(Cell.WATER)) break;
            sunkShipLocations.add(loc);
        }
        for (int i = col + 1; i < boardSize; i++) {
            Location loc = new Location(i, row);
            if (enemyBoard.getCellStatus(loc).equals(Cell.WATER)) break;
            sunkShipLocations.add(loc);
        }
        for (int i = row; i >= 0; i--) {
            Location loc = new Location(col, i);
            if (enemyBoard.getCellStatus(loc).equals(Cell.WATER)) break;
            sunkShipLocations.add(loc);
        }
        for (int i = row + 1; i < boardSize; i++) {
            Location loc = new Location(col, i);
            if (enemyBoard.getCellStatus(loc).equals(Cell.WATER)) break;
            sunkShipLocations.add(loc);
        }
    }

    private void addAdjacentCellsToTarget(Location hitLocation, Board enemyBoard) {
        int row = hitLocation.getRow();
        int col = hitLocation.getColumn();

        if (row > 0 && !isAlreadyShot(new Location(col, row - 1), enemyBoard)) {
            targetCells.add(new Location(col, row - 1));
        }
        if (row < boardSize - 1 && !isAlreadyShot(new Location(col, row + 1), enemyBoard)) {
            targetCells.add(new Location(col, row + 1));
        }
        if (col > 0 && !isAlreadyShot(new Location(col - 1, row), enemyBoard)) {
            targetCells.add(new Location(col - 1, row));
        }
        if (col < boardSize - 1 && !isAlreadyShot(new Location(col + 1, row), enemyBoard)) {
            targetCells.add(new Location(col + 1, row));
        }
    }

    private void determineTargetDirection(Location newHitLocation) {
        if (newHitLocation.getRow() == firstHitLocation.getRow()) {
            targetDirection = Direction.HORIZONTAL;
        } else if (newHitLocation.getColumn() == firstHitLocation.getColumn()) {
            targetDirection = Direction.VERTICAL;
        }
    }

    private void refineDirectionalTargets(Board enemyBoard) {
        targetCells.clear();
        if (targetDirection == Direction.HORIZONTAL) {
            refineHorizontalTargets(enemyBoard);
        } else { // Direction.VERTICAL
            refineVerticalTargets(enemyBoard);
        }
    }

    private void refineHorizontalTargets(Board enemyBoard) {
        int col = firstHitLocation.getColumn();
        int row = firstHitLocation.getRow();

        // Check right direction
        for (int i = col + 1; i < boardSize; i++) {
            Location loc = new Location(i, row);
            if (enemyBoard.getCellStatus(loc).equals(Cell.MISS)) break;
            if (enemyBoard.getCellStatus(loc).equals(Cell.HIT)) continue;
            targetCells.add(loc);
            break;
        }

        // Check left direction
        for (int i = col - 1; i >= 0; i--) {
            Location loc = new Location(i, row);
            if (enemyBoard.getCellStatus(loc).equals(Cell.MISS)) break;
            if (enemyBoard.getCellStatus(loc).equals(Cell.HIT)) continue;
            targetCells.add(loc);
            break;
        }
    }

    private void refineVerticalTargets(Board enemyBoard) {
        int col = firstHitLocation.getColumn();
        int row = firstHitLocation.getRow();

        // Check downward direction
        for (int i = row + 1; i < boardSize; i++) {
            Location loc = new Location(col, i);
            if (enemyBoard.getCellStatus(loc).equals(Cell.MISS)) break;
            if (enemyBoard.getCellStatus(loc).equals(Cell.HIT)) continue;
            targetCells.add(loc);
            break;
        }

        // Check upward direction
        for (int i = row - 1; i >= 0; i--) {
            Location loc = new Location(col, i);
            if (enemyBoard.getCellStatus(loc).equals(Cell.MISS)) break;
            if (enemyBoard.getCellStatus(loc).equals(Cell.HIT)) continue;
            targetCells.add(loc);
            break;
        }
    }

    private boolean isAlreadyShot(Location location, Board enemyBoard) {
        String status = enemyBoard.getCellStatus(location);
        return status.equals(Cell.MISS) || status.equals(Cell.HIT) || status.equals(Cell.SUNK);
    }

    private void resetTargetingMode() {
        targetingMode = false;
        targetCells.clear();
        targetDirection = null;
        firstHitLocation = null;
    }
}

