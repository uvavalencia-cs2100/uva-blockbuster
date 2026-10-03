package customer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// Service: owns the collection of customers and the rules about it (e.g. unique ids).
// It is a normal object, so create one and pass it to whoever needs it.
public class CustomerService {
    private final Map<Integer, Customer> customers = new LinkedHashMap<>();

    public void add(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (customers.containsKey(customer.getId())) {
            throw new IllegalArgumentException("A customer with id " + customer.getId() + " already exists");
        }
        customers.put(customer.getId(), customer);
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
        return customers.remove(id) != null;
    }
}
