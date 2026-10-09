package common;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

// Service base: owns the collection of one kind of entity and the rules about it (unique ids, add,
// find, remove). Every change is handed to the repository, so callers never have to remember to
// save. Subclasses (e.g. CustomerService) fix the type and add rules specific to their entity.
public abstract class AbstractService<T extends Entity> {

    private final Map<Integer, T> elements = new LinkedHashMap<>();
    private final AbstractRepository<T> repository;
    // Named after the concrete subclass, so log records show e.g. customer.CustomerService
    protected final Logger log = Logger.getLogger(getClass().getName());

    // Starts with whatever the repository has stored, so the service is never in a state where a
    // save could overwrite the stored elements with an incomplete list.
    protected AbstractService(AbstractRepository<T> repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.repository = repository;
        loadEntities();
    }

    // Fills the service with what the repository has stored. It does not save anything back.
    private void loadEntities() {
        for (T element : repository.read()) {
            try {
                put(element);
            } catch (IllegalArgumentException e) {
                log.warning("Skipping element: " + e.getMessage());
            }
        }
    }

    private void put(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }
        if (elements.containsKey(element.getId())) {
            throw new IllegalArgumentException(
                    "An element with id " + element.getId() + " already exists");
        }
        elements.put(element.getId(), element);
    }

    // Hands the whole collection to the repository
    private void write() {
        repository.write(list());
    }

    public void add(T element) {
        put(element);
        write();
    }

    public Optional<T> findById(int id) {
        return Optional.ofNullable(elements.get(id));
    }

    // Returns a read-only snapshot so callers cannot modify our internal state
    public List<T> list() {
        return List.copyOf(elements.values());
    }

    // Returns true if an element was removed
    public boolean remove(int id) {
        boolean removed = elements.remove(id) != null;
        if (removed) {
            write();
        }
        return removed;
    }

}
