import java.util.Scanner;


public class InputCheck {
    public static Location checkLocationInput(Scanner scanner, Board board, String rowMessage, String columnMessage) {
        int row, column;
        while (true) {
            System.out.print(rowMessage);
            row = scanner.nextInt();
            System.out.print(columnMessage);
            column = scanner.nextInt();

            if (Location.locationIsValid(row, column, board)) {
                break;
            } else {
                System.out.println("Invalid location. Please enter again.");
            }
        }

        return new Location(column, row);
    }

    public static Direction checkDirectipnInput(Scanner scanner, String directionMessage) {
        int direction;
        while (true) {
            System.out.print(directionMessage);
            direction = scanner.nextInt();
            if (direction == 0)
                return Direction.HORIZONTAL;
            else if (direction == 1) {
                return Direction.VERTICAL;
            } else {
                System.out.println("Invalid direction. Please enter again.");
            }
        }
    }
}
