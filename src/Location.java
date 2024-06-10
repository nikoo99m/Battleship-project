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
}
