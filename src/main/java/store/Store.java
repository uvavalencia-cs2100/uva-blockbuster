package store;

// Will maybe use these later when we convert our array inventory to a list structure
// import java.util.ArrayList;
// import java.util.List;

import movie.MovieCopy;

public class Store {

    private final String storeName;
    private static int NEXT_ID = 1;
    private final int storeId;
    private final String storeAddress;
    private final MovieCopy[] inventory;


    // Constructor that takes a store name and address, and auto-increments the storeId.
    public Store(String storeName, String storeAddress) {
        if (storeName == null || storeName.trim().isEmpty()) {
            throw new IllegalArgumentException("Store name cannot be null or empty");
        }
        if (storeAddress == null || storeAddress.trim().isEmpty()) {
            throw new IllegalArgumentException("Store address cannot be null or empty");
        }

        this.storeName = storeName;
        this.storeId = NEXT_ID++;
        this.storeAddress = storeAddress;
        this.inventory = new MovieCopy[999]; //dont know what the capacity should be 
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


    // Add a MovieCopy to the store's inventory, does this need to be in Store.java, or just in Inventory.java?

    public void addCopy(MovieCopy copy){
        if (copy == null) {
            throw new IllegalArgumentException("MovieCopy cannot be null");
        }

        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                inventory[i] = copy;
                return;
            }
        }

        throw new IllegalStateException("Inventory is full!!");

    }


}
