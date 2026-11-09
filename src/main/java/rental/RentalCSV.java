package rental;

import java.time.LocalDate;
import java.util.*;
import common.CsvMapping;
import common.CsvRow;
import customer.Customer;

import customer.CustomerService;

import movie.MovieCopy;
import movie.MovieCopyService;




// How rentals are stored in rentals.csv: the only place that lists the columns.
// The names, the values written, and the values read are all declared here.
// See CsvMapping for what each method must do.
public class RentalCSV implements  CsvMapping<Rental> {

    private final CustomerService customerService;
    private final MovieCopyService movieCopyService;

    public RentalCSV(CustomerService customerService, MovieCopyService movieCopyService){
        this.movieCopyService = movieCopyService;
        this.customerService = customerService;
    }

    @Override 
    public List<String> getCSVColumnNames() {
        return List.of("id", "customerId", "movieCopyId", "rentalDate", "dueDate", "status");
    }

    @Override 
    public List<String> toFields(Rental rental) {
        String movieCopyId = String.valueOf(rental.getMovieCopy().getId());
        String customerId = String.valueOf(rental.getCustomer().getId());
        String rentalDate = String.valueOf(rental.getRentalDate());
        String dueDate = String.valueOf(rental.getReturnDate());
        return List.of(
            String.valueOf(rental.getId()), customerId, movieCopyId, rentalDate, dueDate, String.valueOf(rental.getRentalStatus().getValue()));

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
        String rentalDate = row.getString("rentalDate");
        LocalDate rentalDate2 = Rental.stringToLocalDate(rentalDate);
        String returnDate = row.getString("dueDate");
        LocalDate returnDate2 = Rental.stringToLocalDate(returnDate);
        Rental.isOverdue(Rental.stringToLocalDate(returnDate));
        RentalStatus status = RentalStatus.fromValue(row.getInt("status"));
        return new Rental(rentalId, customer, movieCopy, rentalDate2, returnDate2, status);
    }
}
