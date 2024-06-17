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


                System.out.print("Enter starting point like A1 : ");
                String startingPoint = scanner.nextLine();

                System.out.print("Enter direction (0 for HORIZONTAL, 1 for VERTICAL): ");
                int directionValue = scanner.nextInt();
                Direction direction = (directionValue == 0) ? Direction.HORIZONTAL : Direction.VERTICAL;
            }
        }
    }
}