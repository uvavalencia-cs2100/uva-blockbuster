package movie;

public class MovieCopy {

    private final Movie movie; // a reference to the Movie it is a copy of
    private static int nextId = 1; // can use for auto-incrementing copyId because static
    private final int copyId; // a unique copy id
    private enum Status{AVAILABLE, RENTED, DAMAGED, LOST}; // an availability status 
    private Status status;


    // Constructor that takes a Movie and starts the copy as AVAILABLE.
    public MovieCopy(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie cannot be null");
        }
        
        this.movie = movie;
        this.copyId = nextId++; // auto-increment copyId with static variable nextId
        this.status = Status.AVAILABLE; // default status is AVAILABLE
    }


    // Getter for all fields
    public Movie getMovie() {
        return movie;
    }

    public int getCopyId() {       
        return copyId;
    }

    public Status getStatus() {
        return status;
    }
    // setter for availability status
    public void setStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
    }

}
