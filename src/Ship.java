import java.util.Objects;

public class Ship {

    private final String name;
    private final int size;
    private Direction direction;
    private Location location;
    protected String shipColour;

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

    public void setLocation(Location location) {
        this.location = location;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getColour(){
        return shipColour;
    }
}