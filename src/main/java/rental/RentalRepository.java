package rental;

import common.AbstractRepository;

// Repository: stories rentals in rentals.csv. The file handling comes from AbstractRepository
// the columns from RentalCSV, so there is nothing else to write here.
public class RentalRepository extends AbstractRepository<Rental> {
    private static final String FILE_NAME = "rentals.csv";

    // 'dataPath' is the folder that holds rentals.csv
    public RentalRepository(String dataPath) {
        super(dataPath, FILE_NAME, new RentalCSV());
    }
    
}
