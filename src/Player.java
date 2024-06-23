import java.util.List;
import java.util.Scanner;

public class Player {
    private Board board = new Board(10);

    Player(Board board) {
        this.board = board;
    }


    public void placeAllShips() {
        DefaultShip defaultShip = new DefaultShip();
        List<Ship> defaultShips = defaultShip.initializeDefaultShip();

        Scanner scanner = new Scanner(System.in);

        for (Ship ship : defaultShips) {
            boolean placed = false;
            while (!placed) {

                System.out.println("Placing " + ship.getName() + " of size " + ship.getSize());

                String getRow = "Enter starting row";
                String getColumn = "Enter starting column";
                Location location = InputCheck.checkLocationInput(scanner, board, getRow, getColumn);
                String getDirection = "Enter direction (0 for HORIZONTAL, 1 for VERTICAL): ";
                Direction direction = InputCheck.checkDirectipnInput(scanner, getDirection);

                ship.setLocation(location);
                ship.setDirection(direction);

                if (board.addShip(ship)) {
                    placed = true;
                    System.out.println(ship.getName() + " placed successfully.");
                    board.printBoard();
                } else {
                    System.out.println("Cannot place ship at specified location. Please try again.");

                }
            }
        }
    }

    public Location shoot(Board enemyBoard) {
        System.out.println("Enter the row of the point for shoothing");
        Scanner scanner = new Scanner(System.in);
        int row = scanner.nextInt();
        System.out.println("Enter the column of the point for shoothing");
        Scanner sc = new Scanner(System.in);
        int column = sc.nextInt();
        Location firedLocation = new Location(column, row);

        if (enemyBoard.addHit(firedLocation)) {
            enemyBoard.printBoard();
            if (enemyBoard.hasHit(firedLocation)) {
                shoot(enemyBoard);
            }
        }
        return firedLocation;
    }
}

