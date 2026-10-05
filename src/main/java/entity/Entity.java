package entity;

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