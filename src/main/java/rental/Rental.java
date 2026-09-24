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

private void setId(int id) {
    this.id = id+1;
}

public int getId() {
    return id;
}

public void setRentalDate(String rentalDate) {
    this.rentalDate = java.time.LocalDate.now().toString();
}

public String getRentalDate() {
    return rentalDate;
}

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
