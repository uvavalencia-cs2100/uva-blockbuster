package customer;

import common.AbstractRepository;
import common.CsvMapping;

// Repository: stores customers in customers.csv. The file handling comes from AbstractRepository and
// the columns from Customer, so there is nothing else to write here.
public class CustomerRepository extends AbstractRepository<Customer> {
    private static final String FILE_NAME = "customers.csv";
    // A Customer is the CsvMapping, but it needs an instance to call. This one is never stored; it
    // only describes the columns.
    private static final CsvMapping<Customer> MAPPING =
            new Customer(1, "Mapping", "mapping@example.com");

    // `dataPath` is the folder that holds customers.csv
    public CustomerRepository(String dataPath) {
        super(dataPath, FILE_NAME, MAPPING);
    }
}
