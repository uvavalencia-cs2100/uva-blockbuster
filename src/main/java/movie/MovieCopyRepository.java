package movie;

import common.AbstractRepository;

// Repository: stores movie copies in movies_copies.csv. The file handling comes from AbstractRepository and
// the columns from MovieCopyCSV, so there is nothing else to write here.
public class MovieCopyRepository extends AbstractRepository<MovieCopy> {
    private static final String FILE_NAME = "movie_copies.csv";

    // `dataPath` is the folder that holds movies_copies.csv
    public MovieCopyRepository(String dataPath, MovieService movies) {
        super(dataPath, FILE_NAME, new MovieCopyCSV(movies));
    }
}