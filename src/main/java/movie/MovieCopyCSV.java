package movie;

import java.util.List;

import common.CsvMapping;
import common.CsvRow;

// How movie copies are stored in movies_copies.csv: the only place that lists the columns. The names,
// the values written and the values read are all declared here. See CsvMapping for what each
// method must do.
public class MovieCopyCSV implements CsvMapping<MovieCopy> {
    
    private final MovieService movies;

    public MovieCopyCSV(MovieService movies) {
        this.movies = movies;
    }


    @Override
    public List<String> getCSVColumnNames() {
        return List.of("id", "movieId", "status", "copyNumber");
    }

    @Override
    public List<String> toFields(MovieCopy movieCopy) {
        return List.of(
                String.valueOf(movieCopy.getId()), String.valueOf(movieCopy.getMovie().getId()), String.valueOf(movieCopy.getStatus().getValue()), String.valueOf(movieCopy.getCopyNumber()));
    }

    // The MovieCopy constructor validates the values, so an invalid line throws
    // IllegalArgumentException and the repository skips it
    @Override
    public MovieCopy fromCSVRow(CsvRow row) {
        
        int movieId = row.getInt("movieId");
        Movie movie = movies.findById(movieId)
            .orElseThrow(() -> new IllegalArgumentException("No movie with id " + movieId));
        return new MovieCopy(row.getInt("id"), movie,
            MovieStatus.fromValue(row.getInt("status")), row.getInt("copyNumber"));
        
        
        
            }
}