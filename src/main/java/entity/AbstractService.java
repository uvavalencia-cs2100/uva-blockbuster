package entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import customer.Customer;
import customer.CustomerRepository;

public abstract class AbstractService<T extends Entity> {

    private final Map<Integer, T> elements = new LinkedHashMap<>();
    private final AbstractRepository<T> repository;

    protected AbstractService(AbstractRepository<T> repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.repository = repository;
        load();
    }

    // Fills the service with what the repository has stored. It does not save anything back.
    private void load() {
        for (T element : repository.read()) {
            try {
                put(element);
            } catch (IllegalArgumentException e) {
                log.warning("Skipping customer: " + e.getMessage());
            }
        }
    }

    public void add(T element) {
        put(element);
        save();
    }

    public Optional<T> findById(int id) {
        return Optional.ofNullable(elements.get(id));
    }

    // Returns a read-only snapshot so callers cannot modify our internal state
    public List<T> list() {
        return List.copyOf(elements.values());
    }

    // Returns true if a customer was removed
    public boolean remove(int id) {
        boolean removed = elements.remove(id) != null;
        if (removed) {
            save();
        }
        return removed;
    }

    private void put(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (elements.containsKey(element.getId())) {
            throw new IllegalArgumentException(
                    "A customer with id " + element.getId() + " already exists");
        }
        elements.put(element.getId(), element);
    }

    private void save() {
        repository.save(list());
    }
}
