package movie;

import common.AbstractService;

// Service: the movie copies' collection. All the behaviour (unique ids, add, find, list, remove, and
// saving after each change) is inherited from AbstractService; this class fixes the type to
// MovieCopy and is the place for rules that only movie copies have.
public class MovieCopyService extends AbstractService<MovieCopy> {
    // The parent loads the stored customers, so a MovieCopyService that exists is always loaded
    public MovieCopyService(MovieCopyRepository repository) {
        super(repository);
    }
}
