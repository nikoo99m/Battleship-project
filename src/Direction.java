public enum Direction {
    HORIZONTAL('h', 'H'),
    VERTICAL('v', 'V');

    private final char lower;
    private final char upper;

    Direction(char lower, char upper) {
        this.lower = lower;
        this.upper = upper;
    }

    public static Direction decode(char c) {
        for (Direction direction : Direction.values()) {
            if (direction.lower == c || direction.upper == c) {
                return direction;
            }

        }
        return null;
    }}

