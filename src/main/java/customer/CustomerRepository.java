package customer;

import common.AbstractRepository;

// Repository: stores customers in customers.csv. The file handling comes from AbstractRepository and
// the columns from CustomerCSV, so there is nothing else to write here.
public class CustomerRepository extends AbstractRepository<Customer> {
    private static final String FILE_NAME = "customers.csv";

    // `dataPath` is the folder that holds customers.csv
    public CustomerRepository(String dataPath) {
        super(dataPath, FILE_NAME, new CustomerCSV());
    }
}
