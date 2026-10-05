package customer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

// Repository: the only class that knows customers are stored as a CSV file. The service asks it
// to load and save and never sees paths, headers or lines.
public class CustomerRepository {
    private static final Logger log = Logger.getLogger(CustomerRepository.class.getName());

    private static final String FILE_NAME = "customers.csv";

    private final Path file;

    // `dataPath` is the folder that holds customers.csv
    public CustomerRepository(String dataPath) {
        this.file = Path.of(dataPath, FILE_NAME);
    }

    // Reads every valid customer. Invalid lines are logged and skipped; a missing or unreadable
    // file is logged and gives an empty list.
    public List<Customer> load() {
        List<Customer> customers = new ArrayList<>();
        List<String> lines;
        try {
            lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.severe("Error reading " + file + ": " + e.getMessage());
            return customers;
        }
        // The first line is the header
        for (String line : lines.stream().skip(1).filter(l -> !l.isBlank()).toList()) {
            try {
                Customer customer = Customer.fromCSVLine(line);
                customers.add(customer);
                log.info("Loaded " + customer);
            } catch (IllegalArgumentException e) {
                log.warning("Skipping invalid customer line: " + e.getMessage());
            }
        }
        return customers;
    }

    // Writes all the customers, so changes survive a restart. It writes to a temporary file first
    // and then replaces the real one, so a failure never leaves it half written.
    public void save(List<Customer> customers) {
        Path temp = file.resolveSibling(FILE_NAME + ".tmp");
        List<String> lines = new ArrayList<>();
        lines.add(Customer.CSV_HEADER);
        for (Customer customer : customers) {
            lines.add(customer.toCSVLine());
        }
        try {
            Files.write(temp, lines, StandardCharsets.UTF_8);
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING);
            log.info("Saved " + customers.size() + " customers to " + file);
        } catch (IOException e) {
            log.severe("Could not save customers to " + file + ": " + e.getMessage());
        }
    }
}
