package store;

import java.util.ArrayList;
import java.util.List;

import movie.MovieCopy;

public class Store {

    private final String storeName;
    private static int nextStoreId = 1;
    private final int storeId;
    private final String storeAddress;
    private final List<MovieCopy> movieInventory = new ArrayList<>();


    // Constructor that takes a store name and address, and auto-increments the storeId.
    public Store(String storeName, String storeAddress) {
        if (storeName == null || storeName.trim().isEmpty()) {
            throw new IllegalArgumentException("Store name cannot be null or empty");
        }
        if (storeAddress == null || storeAddress.trim().isEmpty()) {
            throw new IllegalArgumentException("Store address cannot be null or empty");
        }

        this.storeName = storeName;
        this.storeId = nextStoreId++;
        this.storeAddress = storeAddress;
    }

    // Getter for all fields
    public String getStoreName() {
        return storeName;
    }

    public int getStoreId() {
        return storeId;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public List<MovieCopy> getMovieInventory() {
        return movieInventory;
    }


    // Add a MovieCopy to the store's inventory
    public void addCopy(MovieCopy copy){
        if (copy == null) {
            throw new IllegalArgumentException("MovieCopy cannot be null");
        }
        movieInventory.add(copy);

    }


}
