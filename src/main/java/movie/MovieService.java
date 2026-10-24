package movie;

import common.AbstractService;

// Service: the movies' collection. All the behaviour (unique ids, add, find, list, remove, and
// saving after each change) is inherited from AbstractService; this class fixes the type to
// Movie and is the place for rules that only movies have.
public class MovieService extends AbstractService<Movie> {
    // The parent loads the stored movies, so a MovieService that exists is always loaded
    public MovieService(MovieRepository repository) {
        super(repository);
    }
}