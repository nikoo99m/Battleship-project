import java.util.List;
import java.util.Scanner;

public class Player {
    private final Board board = new Board(10);


    public void placeAllShips() {
        DefaultShip defaultShip = new DefaultShip();
        List<Ship> defaultShips = defaultShip.initializeDefaultShip();

        Scanner scanner = new Scanner(System.in);
        for (Ship ship : defaultShips) {
            boolean placed = false;
            while (!placed) {

                System.out.println("Placing " + ship.getName() + " of size " + ship.getSize());

                System.out.println("Enter starting row");
                int row = scanner.nextInt();
                System.out.println("Enter starting column");
                int column = scanner.nextInt();
                System.out.print("Enter direction (0 for HORIZONTAL, 1 for VERTICAL): ");
                int directionValue = scanner.nextInt();
                Direction direction = (directionValue == 0) ? Direction.HORIZONTAL : Direction.VERTICAL;

                ship.setLocation(new Location(column, row));
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
}