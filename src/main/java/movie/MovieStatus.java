package movie;

// Where a copy of a movie is in its life: on the shelf, out with a customer, or out of circulation.
// Each status carries an int code, which is what gets stored (e.g. in a CSV file) instead of the name.
public enum MovieStatus {
    AVAILABLE(1),
    RENTED(2),
    DAMAGED(3),
    LOST(4);

    private final int value;

    MovieStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    // The status stored with this code, e.g. when reading it back from a file
    public static MovieStatus fromValue(int value) {
        for (MovieStatus status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown movie status value: " + value);
    }
}
