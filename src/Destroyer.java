public class Destroyer extends Ship {
    private static final String NAME = "Destroyer";
    private static final int LENGTH = 2;

    public Destroyer(String name, int size) {
        super(name, size);
        shipColour = "\u001B[35m";
    }

}
