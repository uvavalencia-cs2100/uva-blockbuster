package customer;

import entity.AbstractService;

// Service: the customers' collection. All the behaviour (unique ids, add, find, list, remove, and
// saving after each change) is inherited from AbstractService; this class fixes the type to
// Customer and is the place for rules that only customers have.
public class CustomerService extends AbstractService<Customer> {
    // The parent loads the stored customers, so a CustomerService that exists is always loaded
    public CustomerService(CustomerRepository repository) {
        super(repository);
    }
}
