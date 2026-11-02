package rental;

import java.util.*;
import common.CsvMapping;
import common.CsvRow;
import customer.Customer;
import customer.CustomerCSV;
import customer.CustomerService;
import movie.Movie;
import movie.MovieCopy;
import movie.MovieCopyService;
import movie.MovieStatus;



// How rentals are stored in rentals.csv: the only place that lists the columns.
// The names, the values written, and the values read are all declared here.
// See CsvMapping for what each method must do.
public class RentalCSV implements  CsvMapping<Rental> {

    private final CustomerService customerService;
    private final MovieCopyService movieCopyService;

    public RentalCSV(MovieCopyService movieCopyService, CustomerService customerService){
        this.movieCopyService = movieCopyService;
        this.customerService = customerService;
    }

    @Override 
    public List<String> getCSVColumnNames() {
        return List.of("id", "name", "email", "title", "status");
    }

    @Override 
    public List<String> toFields(Rental rental) {
        String movieCopyId = String.valueOf(rental.getMovieCopy().getId());
        String customerId = String.valueOf(rental.getCustomer().getId());
        return List.of(
            rental.getId(), customerId, movieCopyId, rental.getRentalStatus().getValue())
            .stream()
            .map(String::valueOf).toList();

    }
    
    // The Rental constructor validates the values, so an invalid line throws
    // IllegalArgumentException and the repository skips it
    @Override 
    public Rental fromCSVRow(CsvRow row) {

        int rentalId = row.getInt("id");
        Customer customer = customerService.findById(row.getInt("customerId"))
            .orElseThrow(() -> new IllegalArgumentException("No customer with id " + row.getInt("customerId")));
        MovieCopy movieCopy = movieCopyService.findById(row.getInt("movieCopyId"))
            .orElseThrow(() -> new IllegalArgumentException("No movie copy with id " + row.getInt("movieCopyId")));
        RentalStatus status = RentalStatus.fromValue(row.getInt("status"));
        return new Rental(rentalId, customer, movieCopy , status);
    }
}
