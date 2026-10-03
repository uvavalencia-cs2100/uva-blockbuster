package dataloader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import app.AppContext;
import customer.Customer;
import movie.Movie;
import inventory.Inventory;

public class DataLoader {
    
    private static void readFile(String path, String className) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // Skip the header line
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                switch (className) {
                    case "Customer" -> {
                        try {
                            AppContext.getInstance().getCustomerService().add(Customer.fromCSVLine(line));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Skipping invalid customer line: " + e.getMessage());
                        }
                    }
                    case "Movie" -> {
                        Movie m = Movie.fromCSVLine(line);
                        if (m != null) {
                            Inventory.addMovie(m);
                        }
                    }
                    default -> System.out.println("Unknown class name: " + className);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    public static void loadData(String basePath) {
        readFile(basePath + "/customers.csv", "Customer");
        readFile(basePath + "/movies.csv", "Movie");
    }
}
