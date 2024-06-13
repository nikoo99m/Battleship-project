public class Ship {

    private final String name;
    private final int size;
    private Direction direction;
    private Location location;

    public Ship(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public Direction getDirection() {
        return direction;
    }
    public Location getLocation() {
        return location;
    }
}