package movie;

import java.util.Objects;

import common.Entity;

// Entity: identified by its id (kept and validated by Entity), and never allowed to exist in an
// invalid state. It knows its views, but nothing about storage: how it is stored in a CSV file is
// described by MovieCSV.
public final class Movie extends Entity {
    private static final int MIN_YEAR = 1900;
    private static final int MAX_YEAR = 2027;
    private static final int MAX_LENGTH = 500;
    private static final int MAX_RATING = 10;

    private String title;
    private String director;
    private int year;
    private int length;
    private String genre;
    private int rating;

    public Movie(
            int id, String title, String director, int year, int length, String genre, int rating) {
        super(id);
        setTitle(title);
        setDirector(director);
        setYear(year);
        setLength(length);
        setGenre(genre);
        setRating(rating);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = requireText(title, "Title");
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = requireText(director, "Director");
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year < MIN_YEAR || year > MAX_YEAR) {
            throw new IllegalArgumentException(
                    "Year must be between " + MIN_YEAR + " and " + MAX_YEAR);
        }
        this.year = year;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be a positive integer");
        }
        if (length > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "Length must be less than or equal to " + MAX_LENGTH + " minutes");
        }
        this.length = length;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = requireText(genre, "Genre");
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 0 || rating > MAX_RATING) {
            throw new IllegalArgumentException("Rating must be between 0 and " + MAX_RATING);
        }
        this.rating = rating;
    }

    // Text fields cannot be null or blank, and cannot contain a comma because it would break the
    // CSV file
    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " cannot be null or blank");
        }
        if (value.contains(",")) {
            throw new IllegalArgumentException(field + " cannot contain a comma");
        }
        return value.trim();
    }

    // Two movies are the same movie if they share an id
    @Override 
    public boolean equals(Object object) {
        if (object == null) return false;
        if (this == object) return true;
        if (object instanceof Movie) {
            Movie otherMovie = (Movie) object;
            return this.getId() == otherMovie.getId();
        } 
        return false;
    }

    @Override 
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override 
    public String toString() {
        return String.format(
                "Movie #%d: title='%s', director='%s', year=%d, length=%d, genre='%s', rating=%d",
                getId(), title, director, year, length, genre, rating);
    }

    @Override
    public String getSingleLineView() {
        return String.format("Id: #%d, %s (%d) - %s, %d min, Genre: %s, Rating: %d/%d", getId(), title, year, director, length, genre, rating, MAX_RATING);
    }

    @Override
    public String getDetailedView() {
        return String.format("Id: #%d\nTitle: %s\nDirector: %s\nYear: %d\nLength: %d min\nGenre: %s\nRating: %d/10", getId(), title, director, year, length, genre, rating);
    }

    @Override
    public String getLogView() {
        return String.format("Movie Entity - Id: #%d, title=%s, director=%s, year=%d, length=%d, genre=%s, rating=%d", getId(), title, director, year, length, genre, rating);
    }
}
