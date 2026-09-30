package rental;
import customer.Customer;
import movie.Movie;
import movie.MovieCopy;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.Date;

public class Rental {
    private static int nextId = 1;
    private final int copyId;
    private Customer customer;
    private MovieCopy movieCopy;
    private Date rentalDate;
    private Date returnDate;
    private String status;

public Rental(int id, Customer customer, MovieCopy movieCopy, Date rentalDate, Date returnDate, String status) {
    setCustomer(customer);
    this.movieCopy = movieCopy;
    setRentalDate(rentalDate);
    this.status = "Active";
    // RentalOptions();
    getStatus();
    this.copyId = nextId++;
}

public int getCopyId() {       
        // must be a positive integer
        if (copyId <= 0) {
            throw new IllegalArgumentException("Copy ID must be a positive integer");
        }
        return copyId;
    }

//sets the rental date to the current date and return date to 14 days after the rental date
public void setRentalDate(Date rentalDate) {
    this.rentalDate = rentalDate;
    this.returnDate = new Date(rentalDate.getTime() + (14L * 24 * 60 * 60 * 1000)); 
}

//gets the rental date
public Date getRentalDate() {
    return rentalDate;
}


// //sets the return date to 14 days after the rental date (or attempts to)
// private String setReturnDate(String rentalDate) {
//     int foo;
// try {
//    foo = Integer.parseInt(rentalDate.substring(8,10)+14);
// }
// catch (NumberFormatException e) {
//    foo = 0;
// } 
//     String rentalDay = rentalDate.substring(8,10);
//     this.returnDate = rentalDate.replace(rentalDay, String.valueOf(foo));
//     return returnDate;
// }

//Upon calling method, checks if return date is before current date. If so, status is set to overdue. If not, user is prompted to set status to active, returned, lost, or claimed.
// public void RentalOptions() {
//     String todayDate = LocalDate.now().toString();
//     if (returnDate.compareTo(todayDate) <0) {
//         this.status = "Overdue";
//     } else {
//     System.out.println("""
//         input status: 
//     1. Active (press a)
//     2. Returned (press r)
//     3. Lost (press l)
//     4. Claimed (press c)""");
//     char input = next().toLowerCase().charAt(0);
//     switch (input) {
//         case 'a' -> this.status = "Active";
//         case 'r' -> returnCopy();
//         case 'l' -> markLost();
//         case 'c' -> markReturned();
//         default -> System.out.println("Invalid input. Please try again.");
//     } }



public void setCustomer(Customer customer) {
    if (customer != null)
    this.customer = customer;
}

public Customer getCustomer() {
    return this.customer;
}

public void returnCopy() {
    this.status = "Returned";
    //movie.MovieCopy.setStatus("Available"); //placeholder for now, MovieCopy needs setStatus method
}

public void markLost() {
    this.status = "Lost";
    //movie.MovieCopy.setStatus("Lost"); //placeholder for now, MovieCopy needs setStatus method
}

public void markReturned() {
    this.status = "Returned";
    //movie.MovieCopy.setStatus("Rented"); //placeholder for now, MovieCopy needs setStatus method
}

public String getStatus() {
    return status;
}
}
