package movie;

import common.AbstractRepository;

// Repository: stores movies in movies.csv. The file handling comes from AbstractRepository and
// the columns from MovieCSV, so there is nothing else to write here.
public class MovieRepository extends AbstractRepository<Movie> {
    private static final String FILE_NAME = "movies.csv";

    // `dataPath` is the folder that holds movies.csv
    public MovieRepository(String dataPath) {
        super(dataPath, FILE_NAME, new MovieCSV());
    }
}