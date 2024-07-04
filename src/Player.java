public abstract class Player {
    protected Location lastShot;
    
    protected Board board;

    public Player(Board board) {
        this.board = board;
    }

    public abstract void placeAllShips();

    public abstract void shoot(Board enemyBoard);

    public Board getBoard() {
        return board;
    }
    public Location getLastShot() {
        return lastShot;
    }
    protected void setLastShot(Location lastShot) {
        this.lastShot = lastShot;
    }

}

