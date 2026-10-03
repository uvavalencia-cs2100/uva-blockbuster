package customer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

// Service: owns the collection of customers and the rules about it (e.g. unique ids). Every change
// is handed to the repository, so callers never have to remember to save.
public class CustomerService {
    private static final Logger log = Logger.getLogger(CustomerService.class.getName());

    private final Map<Integer, Customer> customers = new LinkedHashMap<>();
    private final CustomerRepository repository;

    // Starts with whatever the repository has stored, so the service is never in a state where a
    // save could overwrite the stored customers with an incomplete list.
    public CustomerService(CustomerRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.repository = repository;
        load();
    }

    // Fills the service with what the repository has stored. It does not save anything back.
    private void load() {
        for (Customer customer : repository.load()) {
            try {
                put(customer);
            } catch (IllegalArgumentException e) {
                log.warning("Skipping customer: " + e.getMessage());
            }
        }
    }

    public void add(Customer customer) {
        put(customer);
        save();
    }

    public Optional<Customer> findById(int id) {
        return Optional.ofNullable(customers.get(id));
    }

    // Returns a read-only snapshot so callers cannot modify our internal state
    public List<Customer> list() {
        return List.copyOf(customers.values());
    }

    // Returns true if a customer was removed
    public boolean remove(int id) {
        boolean removed = customers.remove(id) != null;
        if (removed) {
            save();
        }
        return removed;
    }

    private void put(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (customers.containsKey(customer.getId())) {
            throw new IllegalArgumentException("A customer with id " + customer.getId() + " already exists");
        }
        customers.put(customer.getId(), customer);
    }

    private void save() {
        repository.save(list());
    }
}
