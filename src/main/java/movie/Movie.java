package movie;
public class Movie {

    private String title;
    private String director;
    private int year;
    private int length;
    private String genre;
    private int rating;

    public Movie(String title, String director, int year, int length, String genre, int rating) {
        this.title = title;
        this.director = director;
        this.year = year;
        this.length = length;
        this.genre = genre;
        this.rating = rating;
    }
    

    public String getTitle() {
        // can't be null or empty
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        return title;}

    public void setTitle(String title) {
        this.title = title;}



    public String getDirector() {
        // can't be null or empty
        if (director == null || director.isEmpty()) {
            throw new IllegalArgumentException("Director cannot be null or empty");
        }
        return director;}

    public void setDirector(String director) {
        this.director = director;}




    public int getYear() {
        // must be 4 digits from 1900 and 2026
        if (year < 1900 || year > 2027) {
            throw new IllegalArgumentException("Year must be between 1900 and 2026");
        }
        return year;}

    public void setYear(int year) {
        this.year = year;}




    public int getLength() {
        // must be a positive integer
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be a positive integer");
        }

        if (length > 500) {
            throw new IllegalArgumentException("Length must be less than or equal to 500 minutes");
        }
        return length;}

    public void setLength(int length) {
        this.length = length;}




    public String getGenre() {
        return genre;}

    public void setGenre(String genre) {
        this.genre = genre;}



    public int getRating() { 
        // must be a valid rating 0-10
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }

        return rating;}


    public void setRating(int rating) {
        this.rating = rating;}

    public static Movie fromCSVLine(String line) {
        // Assuming fields are in the order: title, director, year, length, genre, rating
        String[] fields = line.split(",");
        if (fields.length != 6) {
            System.out.println("CSV line must have exactly 6 fields");
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
            System.out.println("Year, length, and rating must be valid integers");
            return null;
        }
        return new Movie(title, director, year, length, genre, rating);
    }

    public String toCSVLine() {
        return String.join(",", title, director, String.valueOf(year), String.valueOf(length), genre, String.valueOf(rating));
    }
}
