package movie;

import store.Store;

public class MovieCopy {

    private Movie movie; // a reference to the Movie it is a copy of
    private static int NEXT_ID = 1; // can use for auto-incrementing copyId because static
    private final int copyId; // a unique copy id
    private Store store; // a reference to the Store it belongs to 

    
    // Constructor that takes a Movie and starts the copy as AVAILABLE.
    public MovieCopy(Movie movie, Store store) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie cannot be null");
        }
        
        this.movie = movie;
        this.copyId = NEXT_ID++; // auto-increment copyId with static variable NEXT_ID
        this.store = store; // initialize store as null
    }


    // Getter for all fields
    public Movie getMovie() {
        return movie;
    }

    public int getCopyId() {       
        return copyId;
    }

    public Store getStore() {
        return store;
    }

}