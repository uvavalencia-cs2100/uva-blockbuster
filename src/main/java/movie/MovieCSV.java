package movie;

import java.util.List;

import common.CsvMapping;
import common.CsvRow;

// How movies are stored in movies.csv: the only place that lists the columns. The names,
// the values written and the values read are all declared here. See CsvMapping for what each
// method must do.
public class MovieCSV implements CsvMapping<Movie> {
    
    @Override
    public List<String> getCSVColumnNames() {
        return List.of("id", "title", "director", "year", "length", "genre", "rating");
    }

    @Override
    public List<String> toFields(Movie movie) {
        return List.of(
            String.valueOf(movie.getId()),
            movie.getTitle(),
            movie.getDirector(),
            String.valueOf(movie.getYear()),
            String.valueOf(movie.getLength()),
            movie.getGenre(),
            String.valueOf(movie.getRating())
        );
    }

    // The Movie constructor validates the values, so an invalid line throws
    // IllegalArgumentException and the repository skips it
    @Override
    public Movie fromCSVRow(CsvRow row) {
        return new Movie(
            row.getInt("id"),
            row.getString("title"),
            row.getString("director"),
            row.getInt("year"),
            row.getInt("length"),
            row.getString("genre"),
            row.getInt("rating")
        );
    }
 
}
