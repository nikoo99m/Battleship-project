import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class ShipPlacement {
    private Board board;
    private int boardSize;

    public ShipPlacement(Board board) {
        this.board = board;
        this.boardSize = board.getLength();
    }

    public void placeAllShips(String filename) {
        board.resetBoard();  // Reset the board before placing ships
        DefaultShip defaultShip = new DefaultShip();
        List<Ship> defaultShips = defaultShip.initializeDefaultShip();

        Random random = new Random();
        int strategy = random.nextInt(4); // Choose a strategy (0: random, 1: wall-hugging, 2: center, 3: mixed)

        switch (strategy) {
            case 0:
                randomPlacement(defaultShips, random);
                break;
            case 1:
                wallHuggingPlacement(defaultShips, random);
                break;
            case 2:
                centerPlacement(defaultShips, random);
                break;
            case 3:
                mixedPlacement(defaultShips, random);
                break;
        }

        try {
            logShipPlacements(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logShipPlacements(String filename) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filename, true)) { // 'true' for append mode
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    fileWriter.write(board.getCell(i, j).getStatus() + ",");
                }
            }
            fileWriter.write("\n");
        }
    }

    private void randomPlacement(List<Ship> ships, Random random) {
        for (Ship ship : ships) {
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
    }

    private void wallHuggingPlacement(List<Ship> ships, Random random) {
        for (Ship ship : ships) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextBoolean() ? random.nextInt(2) : boardSize - 1 - random.nextInt(2);
                int column = random.nextBoolean() ? random.nextInt(2) : boardSize - 1 - random.nextInt(2);
                Direction direction = random.nextBoolean() ? Direction.HORIZONTAL : Direction.VERTICAL;

                Location location = new Location(column, row);
                ship.setLocation(location);
                ship.setDirection(direction);

                if (board.addShip(ship)) {
                    placed = true;
                }
            }
        }
    }

    private void centerPlacement(List<Ship> ships, Random random) {
        for (Ship ship : ships) {
            boolean placed = false;
            while (!placed) {
                int row = boardSize / 4 + random.nextInt(boardSize / 2);
                int column = boardSize / 4 + random.nextInt(boardSize / 2);
                Direction direction = random.nextBoolean() ? Direction.HORIZONTAL : Direction.VERTICAL;

                Location location = new Location(column, row);
                ship.setLocation(location);
                ship.setDirection(direction);

                if (board.addShip(ship)) {
                    placed = true;
                }
            }
        }
    }

    private void mixedPlacement(List<Ship> ships, Random random) {
        for (int i = 0; i < ships.size(); i++) {
            Ship ship = ships.get(i);
            boolean placed = false;
            while (!placed) {
                int row, column;
                if (i % 2 == 0) { // Alternate between wall-hugging and center placements
                    row = random.nextBoolean() ? random.nextInt(2) : boardSize - 1 - random.nextInt(2);
                    column = random.nextBoolean() ? random.nextInt(2) : boardSize - 1 - random.nextInt(2);
                } else {
                    row = boardSize / 4 + random.nextInt(boardSize / 2);
                    column = boardSize / 4 + random.nextInt(boardSize / 2);
                }
                Direction direction = random.nextBoolean() ? Direction.HORIZONTAL : Direction.VERTICAL;

                Location location = new Location(column, row);
                ship.setLocation(location);
                ship.setDirection(direction);

                if (board.addShip(ship)) {
                    placed = true;
                }
            }
        }
    }
}
