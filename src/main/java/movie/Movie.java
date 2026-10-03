package movie;

// Never allowed to exist in an invalid state: the constructor and the setters validate every value.
public class Movie {
    public static final String CSV_HEADER = "id,title,director,year,length,genre,rating";

    private static final int MIN_YEAR = 1900;
    private static final int MAX_YEAR = 2027;
    private static final int MAX_LENGTH = 500;
    private static final int MAX_RATING = 10;

    private final int id;
    private String title;
    private String director;
    private int year;
    private int length;
    private String genre;
    private int rating;

    public Movie(int id, String title, String director, int year, int length, String genre, int rating) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be a positive integer");
        }
        this.id = id;
        setTitle(title);
        setDirector(director);
        setYear(year);
        setLength(length);
        setGenre(genre);
        setRating(rating);
    }

    public int getId() {
        return id;
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
            throw new IllegalArgumentException("Year must be between " + MIN_YEAR + " and " + MAX_YEAR);
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
            throw new IllegalArgumentException("Length must be less than or equal to " + MAX_LENGTH + " minutes");
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

    // Text fields cannot be null or blank, and cannot contain a comma because it would break the CSV file
    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " cannot be null or blank");
        }
        if (value.contains(",")) {
            throw new IllegalArgumentException(field + " cannot contain a comma");
        }
        return value.trim();
    }

    public static Movie fromCSVLine(String line) {
        // Fields are in the order: id, title, director, year, length, genre, rating
        String[] fields = line.split(",");
        if (fields.length != 7) {
            throw new IllegalArgumentException("CSV line must have exactly 7 fields");
        }
        int id;
        int year;
        int length;
        int rating;
        try {
            id = Integer.parseInt(fields[0].trim());
            year = Integer.parseInt(fields[3].trim());
            length = Integer.parseInt(fields[4].trim());
            rating = Integer.parseInt(fields[6].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Id, year, length, and rating must be valid integers", e);
        }
        return new Movie(id, fields[1], fields[2], year, length, fields[5], rating);
    }

    public String toCSVLine() {
        return String.join(",", String.valueOf(id), title, director, String.valueOf(year), String.valueOf(length), genre, String.valueOf(rating));
    }
}
