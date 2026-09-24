package rental;
import customer.Customer;
import movie.Movie;
import movie.MovieCopy;
import java.time.LocalDate;
import java.util.Scanner;

public class Rental {
private int id;
private Customer customer;
private MovieCopy movieCopy;
private String rentalDate;
private String returnDate;
private String status;

public Rental(int id, Customer customer, MovieCopy movieCopy, String rentalDate, String returnDate, String status) {
    getCustomerName(customer);
    this.movieCopy = movieCopy;
    setRentalDate(rentalDate);
    getReturnDate(returnDate);
    this.status = "Active";
    RentalOptions();
    getStatus();
    getId(id);
}

//sets the rental ID, I have no idea how to make it auto increment, so for now it just adds 1 to the ID that is passed in
private void setId(int id) {
    this.id = id+1;
}

//gets the rental ID
public int getId() {
    return id;
}

//sets the rental date to the current date
public void setRentalDate(String rentalDate) {
    this.rentalDate = java.time.LocalDate.now().toString();
}

//gets the rental date
public String getRentalDate() {
    return rentalDate;
}

//sets the return date to 14 days after the rental date (or attempts to)
private String setReturnDate(String rentalDate) {
    int foo;
try {
   foo = Integer.parseInt(rentalDate.substring(8,10)+14);
}
catch (NumberFormatException e) {
   foo = 0;
} 
    String rentalDay = rentalDate.substring(8,10);
    this.returnDate = rentalDate.replace(rentalDay, String.valueOf(foo));
    return returnDate;
}

//Upon calling method, checks if return date is before current date. If so, status is set to overdue. If not, user is prompted to set status to active, returned, lost, or claimed.
public void RentalOptions() {
    String todayDate = LocalDate.now().toString();
    if (returnDate.compareTo(todayDate) <0) {
        this.status = "Overdue";
    } else {
    System.out.println("""
        input status: 
    1. Active (press a)
    2. Returned (press r)
    3. Lost (press l)
    4. Claimed (press c)""");
    char input = next().toLowerCase().charAt(0);
    switch (input) {
        case 'a' -> this.status = "Active";
        case 'r' -> returnCopy();
        case 'l' -> markLost();
        case 'c' -> markReturned();
        default -> System.out.println("Invalid input. Please try again.");
    } }

}

//I dont know how to get the customer name from the customer class, so this is a placeholder for now
public void getCustomerName() {
    this.customer = Customer.getName();
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
