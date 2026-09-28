package inventory;
import java.util.ArrayList;
import java.util.List;
import customer.Customer;
import movie.Movie;
import movie.MovieCopy;

public class Inventory {
    private static List<Movie> movies = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static List<MovieCopy> copies = new ArrayList<>();

    private Inventory() {
        
    }

    // Not actually sure what this means or if its the right way to do it
    public static Inventory getInstance() {
        return new Inventory();
    }

    public static void addCustomer(Customer c) {
        customers.add(c);
    }

    public static void addMovie(Movie m) {
        movies.add(m);
    }

    public static void addMovie(MovieCopy m) {
        copies.add(m);
    }

    public static Customer[] listCustomers() {
        return (Customer[]) customers.toArray();
    }

    // Waiting until movies is updated to main
    public static Movie[] listAvailableCopies() {
        return new Movie[0];
    }

    public static MovieCopy[] listActiveCopies() {
        List<MovieCopy> activeCopies = new ArrayList<>();
        for (MovieCopy m: copies) {
            if (m.getStatus() == Status.ACTIVE) {
                activeCopies.add(m);
            }
        }

        return (MovieCopy[]) activeCopies.toArray();
    }

    public static Customer findCustomer(int id) {
        for (Customer c: customers) {
            if (c.getId() == id) return c;
        } 

        return null;
    }

    // Waiting for ids to be implemented in Movie
    public static Movie findMovie(int id) {
        for (Movie m: movies) {
            //if (m.getId() == id) return m;
        } 

        return null;
    }
}
