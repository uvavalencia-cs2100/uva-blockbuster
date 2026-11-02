package movie;

import java.util.Objects;

import common.Entity;

// Never allowed to exist in an invalid state: the constructor and the setters validate every value.
public final class MovieCopy extends Entity {
    private Movie movie;
    private MovieStatus status;
    private int copyNumber;

    public MovieCopy(int id, Movie movie, MovieStatus status, int copyNumber) {
        super(id);
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


    // Two movie copies are the same movie copy if they share an id
    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (this == object) return true;
        if (object instanceof MovieCopy) {
            MovieCopy otherMovieCopy = (MovieCopy) object;
            return this.getId() == otherMovieCopy.getId();
        }
        return false;
    }


    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return String.format("Movie Copy #%d <%d>: %s - %s", getId(), copyNumber, movie.getTitle(), status);
    }

    @Override
    public String getSingleLineView() {
        return String.format("Id: #%d, copy number: %d, title: %s status: <%s>", getId(), copyNumber, movie.getTitle(), status);
    }

    @Override
    public String getDetailedView() {
        return String.format("Id: #%d:\nCopy number: %d\nTitle: %s\nStatus: <%s>", getId(), copyNumber, movie.getTitle(), status);
    }

    @Override
    public String getLogView() {
        return String.format("Movie Copy Entity - Id: #%d, Copy Number: %d, Title: %s, Status: <%s>", getId(), copyNumber, movie.getTitle(), status);
    }
}
