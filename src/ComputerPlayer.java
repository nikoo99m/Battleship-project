import java.util.List;

public class ComputerPlayer extends Player{

    public ComputerPlayer(Board board) {
        super(board);
    }

    @Override
    public void placeAllShips() {
        DefaultShip defaultShip = new DefaultShip();
        List<Ship> defaultShips = defaultShip.initializeDefaultShip();

        for (Ship ship : defaultShips) {
            boolean placed = false;
            while (!placed) {
                int randomRow = (int) (Math.random() * board.getLength());
                int randomColumn = (int) (Math.random() * board.getLength());
                Direction randomDirection = Math.random() < 0.5 ? Direction.HORIZONTAL : Direction.VERTICAL;

                ship.setDirection(randomDirection);
                ship.setLocation(new Location(randomColumn, randomRow));

                if (board.addShip(ship)) {
                    placed = true;
                    System.out.println(ship.getName() + " placed successfully.");
                    board.printBoard();

                }
            }
        }
    }


    @Override
    public void shoot(Board enemyBoard) {


    }
}




