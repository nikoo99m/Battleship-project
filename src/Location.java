public class Location {
    private final int row;
    private final int column;

    public Location(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public static boolean locationIsValid(int row, int column, Board board) {
        if (row < 0 || column < 0 || row >= board.getLength() || column >= board.getLength()) {
            System.out.println("Invalid row or column");
            return false;
        } else {
            return true;
        }
    }
}
