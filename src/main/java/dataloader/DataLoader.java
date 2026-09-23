package dataloader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import customer.Customer;
import movie.Movie;

public class DataLoader {
    public static void readFile(String path, String className) {
        boolean first = true;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("Read line: " + line); // Debugging output
                if (first)  {
                    first = false; // Skip the header line
                    continue;
                }
                String[] fields = line.split(",");
                switch (className) {
                    case "Customer" -> {
                        // Assuming fields are in the order: name, email, id
                        String name = fields[0];
                        String email = fields[1];
                        int id = Integer.parseInt(fields[2]);
                        // Create a new Customer object
                        Customer customer = new Customer(name, email, id);
                        // You can add the customer to a list or perform other operations here
                    }
                    case "Movie" -> {
                        // Assuming fields are in the order: title, director, year, length, genre, rating
                        String title = fields[0];
                        String director = fields[1];
                        int year = Integer.parseInt(fields[2]);
                        int length = Integer.parseInt(fields[3]);
                        String genre = fields[4];
                        int rating = Integer.parseInt(fields[5]);
                        // Create a new Movie object
                        Movie movie = new Movie(title, director, year, length, genre, rating);
                        // You can add the movie to a list or perform other operations here
                    }
                    default -> System.out.println("Unknown class name: " + className);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }
}
