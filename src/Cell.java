public class Cell {
    private char status;

    public static final char WATER = '.';
    public static final char HIT = 'h';
    public static final char MISS = 'm';
    public static final char SHIP = 's';
    public String colourCode;

    public Cell() {
        this.status = WATER;
    }


    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }
}

