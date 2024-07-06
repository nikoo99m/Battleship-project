import java.util.List;
import java.util.Scanner;

public class HumanPlayer extends Player {

    HumanPlayer(Board board) {
        super(board);
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

    public void shoot(Board enemyBoard) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the row of the point for shooting");
        int row = scanner.nextInt();
        System.out.println("Enter the column of the point for shooting");
        int column = scanner.nextInt();
        Location firedLocation = new Location(column, row);
        setLastShot(firedLocation);

        if (enemyBoard.addHit(firedLocation)) {
            System.out.println("It's a Hit!");
            enemyBoard.printBoardForEnemy();
        } else {
            System.out.println("It's a Miss! Next player's turn.");
            enemyBoard.printBoardForEnemy();
        }
    }

    public Board getBoard() {
        return board;
    }
}



