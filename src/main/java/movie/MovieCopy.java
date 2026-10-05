package movie;

// Never allowed to exist in an invalid state: the constructor and the setters validate every value.
public final class MovieCopy {
    private final int id;
    private Movie movie;
    private MovieStatus status;
    private int copyNumber;

    public MovieCopy(int id, Movie movie, MovieStatus status, int copyNumber) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be a positive integer");
        }
        this.id = id;
        setMovie(movie);
        setStatus(status);
        setCopyNumber(copyNumber);
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie cannot be null");
        }
        this.movie = movie;
    }

    public int getId() {
        return id;
    }

    public MovieStatus getStatus() {
        return status;
    }

    public void setStatus(MovieStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
    }

    public int getCopyNumber() {
        return copyNumber;
    }

    public void setCopyNumber(int copyNumber) {
        if (copyNumber <= 0) {
            throw new IllegalArgumentException("Copy number must be a positive integer");
        }
        this.copyNumber = copyNumber;
    }
}
