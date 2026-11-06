package rental;

//enum for assigning status of a rental: Active, Overdue, Lost, or Returned
//uses numbers as signifiers for storing in a csv file
public enum RentalStatus {
    ACTIVE(1),
    OVERDUE(2),
    RETURNED(3),
    LOST(4);
  
    private final int value;

    RentalStatus(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }

     // The status stored with this code, e.g. when reading it back from a file
    public static RentalStatus fromValue(int value) {
        for (RentalStatus status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown rental status value: " + value);
    }
}
