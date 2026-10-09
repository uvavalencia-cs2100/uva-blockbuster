package common;

// Base of every entity (Customer, Movie, Rental ...). It owns the id and its rule (a positive
// integer), so subclasses only add their own fields. It also requires the three views of
// ViewOptions, which subclasses must implement.
public abstract class Entity implements ViewOptions {

    private int id;

    protected Entity(int id) {
        setId(id);
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be a positive integer");
        }
        this.id = id;
    }

} 