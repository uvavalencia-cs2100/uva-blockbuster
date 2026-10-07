package entity;

import java.util.List;

public abstract class AbstractRepository<T> {

    abstract T read();

    abstract void write(List<T> elements);
    
}
