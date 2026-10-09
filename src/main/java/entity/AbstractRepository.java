package entity;

import java.util.List;
import java.util.logging.Logger;

// Repository base: the contract every repository fulfils, whatever the storage is. The service
// only knows these two methods, so it never sees paths, headers or lines. A subclass (e.g.
// CustomerRepository) decides how its entity is stored.
public abstract class AbstractRepository<T> {

    // Named after the concrete subclass, so log records show e.g. customer.CustomerRepository
    protected final Logger log = Logger.getLogger(getClass().getName());

    // Returns every stored element. It must not throw for bad data: skip it and log it instead.
    public abstract List<T> read();

    // Stores all the given elements, replacing what was stored before.
    public abstract void write(List<T> elements);

}
