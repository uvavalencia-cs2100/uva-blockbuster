package rental;

import java.util.*;
import common.CsvMapping;
import common.CsvRow;
import customer.Customer;
import customer.CustomerCSV;
import customer.CustomerService;
import movie.Movie;
import movie.MovieCopy;
import movie.MovieStatus;



// How rentals are stored in rentals.csv: the only place that lists the columns.
// The names, the values written, and the values read are all declared here.
// See CsvMapping for what each method must do.
public class RentalCSV implements  CsvMapping<Rental> {

    private final CustomerService = customerService;
    private final MovieCopyService = movieCopyService;

    public MovieCopyCSV(MovieCopyService movieCopyService){
            this.movieCopyService = movieCopyService;
    }

    public CustomerCSV(CustomerService customerService){
        this.customerService = customerService;
    }

    @Override 
    public List<String> getCSVColumnNames() {
        return List.of("id", "name", "email", "title", "status");
    }

    @Override 
    public List<String> toFields(Rental rental, MovieCopy movieCopy, Customer customer) {
        return List.of(
            rental.getId(), movieCopy.getMovie().getId(), movieCopy.getStatus().getValue(), movieCopy.getCopyNumber()).stream()
                .map(String::valueOf).toList();

    }
    
    // The Rental constructor validates the values, so an invalid line throws
    // IllegalArgumentException and the repository skips it
    @Override 
    public Rental fromCSVRow(CsvRow row) {

        int rentalId = row.getInt("id");
        Rental rental = rental.findById(rentalId)
            .orElseThrow(() -> new IllegalArgumentException("No movie with id " + movieId));
        return new Rental(row.getInt("id"), 
                            movieCopyService.valueOf(row.getString("title")),
                            customerService.valueOf(row.getString("name"),
                            row.getString("title")),
                            row.getString("status")
                        );
        // return new Rental(
        //         row.getInt("id"),
        //         row.getString("title"),
        //         row.getString("name")
        //         //row.getString("email") 
            
        // );
    }
}
