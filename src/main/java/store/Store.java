package store;

import java.util.ArrayList;
import java.util.List;

import movie.MovieCopy;

public class Store {
    private static int nextId = 1;

    private final int id;
    private String name;
    private String address;
    private final List<MovieCopy> inventory;

    public Store(String name, String address) {
        this.id = nextId++;
        this.name = name;
        this.address = address;
        this.inventory = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<MovieCopy> getInventory() {
        return inventory;
    }

    public void addCopy(MovieCopy copy) {
        inventory.add(copy);
    }
}
