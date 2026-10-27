package rental;

import common.AbstractService;

// Service: the rentals' collection. All the behaviour (unique ids, add, find, list, remove, and
// saving after each change) is inherited from AbstractService; this class fixes the type to
// Rental and is the place for rules that only rentals have.
public class RentalService extends AbstractService<Rental> {
    // The parent loads the stored rentals, so a RentalService that exists is always loaded
    public RentalService(RentalRepository repository) {
        super(repository);
    }
}
