package movie;

public class MovieStatus {

    public enum Status {
        AVAILABLE, DAMAGED, LOST
    }

    private Status status;

    public MovieStatus() {
        this.status = Status.AVAILABLE; 
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}