package movie;

import java.util.logging.Logger;

public class Movie {
    private static final Logger log = Logger.getLogger(Movie.class.getName());


    private final int movieId;
    private static int NEXT_ID = 1; // can use for auto-incrementing movieId because static
    private String title;
    private String director;
    private int year;
    private int length;
    private int rating;

    public Movie(String title, String director, int year, int length, int rating) {
        // can't be null or empty
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        
        // can't be null or empty
        if (director == null || director.isEmpty()) {
            throw new IllegalArgumentException("Director cannot be null or empty");
        }

        // must be 4 digits from 1900 and 2026
        if (year < 1900 || year > 2026) {
            throw new IllegalArgumentException("Year must be between 1900 and 2026");
        }
        
        // must be a positive integer
        if (length <= 0 || length > 500) {
            throw new IllegalArgumentException("Length must be a positive integer");
        }

        // must be a valid rating 0-10
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }
        
        this.movieId = NEXT_ID++; // auto-increment movieId with static variable NEXT_ID
        this.title = title;
        this.director = director;
        this.year = year;
        this.length = length;
        this.rating = rating;
    }
    
    public int getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;}


    public void setTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.title = title;
    }


    public String getDirector() {
        return director;}

    public void setDirector(String director) {
        if (director == null || director.isEmpty()) {
            throw new IllegalArgumentException("Director cannot be null or empty");
        }
        this.director = director;
    }




    public int getYear() {
        return year;}

    public void setYear(int year) {
        if (year < 1900 || year > 2026) {
            throw new IllegalArgumentException("Year must be between 1900 and 2026");
        }
        this.year = year;
    }

    public int getLength() {
        return length;}

    public void setLength(int length) {
        if (length <= 0 || length > 500) {
            throw new IllegalArgumentException("Length must be between 1 and 500 minutes");
        }
        this.length = length;
    }


    public int getRating() {         
        return rating;}


    public void setRating(int rating) {
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }
        this.rating = rating;
    }

    public static Movie fromCSVLine(String line) {
        // Assuming fields are in the order: title, director, year, length, genre, rating
        String[] fields = line.split(",");
        if (fields.length != 6) {
            log.warning("CSV line must have exactly 6 fields");
            return null;
        }
        String title = fields[0].trim();
        String director = fields[1].trim();
        int year;
        int length;
        String genre = fields[4].trim();
        int rating;
        try {
            year = Integer.parseInt(fields[2].trim());
            length = Integer.parseInt(fields[3].trim());
            rating = Integer.parseInt(fields[5].trim());
        } catch (NumberFormatException e) {
            log.warning("Year, length, and rating must be valid integers");
            return null;
        }
        return new Movie(title, director, year, length, genre, rating);
    }

    public String toCSVLine() {
        return String.join(",", title, director, String.valueOf(year), String.valueOf(length), genre, String.valueOf(rating));
    }
}
