package rental;
import common.Entity;
import customer.Customer;
import movie.MovieCopy;

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class Rental extends Entity{
    // private static final long RENTAL_TIME = 14L * 24 * 60 * 60 * 1000;

    
    private Customer customer;
    private MovieCopy movieCopy;
    private String rentalDate;
    private String returnDate;
    private RentalStatus rentalStatus;
    
    
    

    public Rental(int id, Customer customer, MovieCopy movieCopy, String rentalDate, String returnDate, RentalStatus rentalStatus){
        super(id);
        setMovieCopy(movieCopy);
        setCustomer(customer);
        setRentalStatus(rentalStatus);
        setRentalDate(rentalDate);
        setReturnDate(rentalDate);

    }

    public static String dateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    public static LocalDate stringToLocalDate(String dateStr) {
    if (dateStr == null || dateStr.trim().isEmpty()) {
        return null; // Handle null or empty inputs safely
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    try {
        return LocalDate.parse(dateStr, formatter);
    } catch (DateTimeParseException e) {
        // Handle strings that don't match the expected format
        System.err.println("Invalid date format: " + dateStr);
        return null; 
    }
}
    

    public void setMovieCopy(MovieCopy movieCopy){
        this.movieCopy = movieCopy;
    }

    public void setCustomer(Customer customer){
        if (customer == null){
            throw new IllegalArgumentException("Customer cannot be null");
        }
        this.customer = customer;
    }

    public void setRentalStatus(RentalStatus rentalStatus){
        if(rentalStatus == null){
            throw new IllegalArgumentException("Rental status cannot be null");
        }
        this.rentalStatus = rentalStatus;
    }

     // sets the rental date and the return date to 14 days after the rental date
    public void setRentalDate(String rentalDate) {
        if (rentalDate == null) {
            throw new IllegalArgumentException("Rental date cannot be null");
        }
        this.rentalDate = rentalDate;
        // this.returnDate = new LocalDate(rentalDate.getTime() + RENTAL_TIME);
    }

    public void setReturnDate(String rentalDate){
        if (returnDate == null) {
            throw new IllegalArgumentException("Rental date cannot be null");
        }
        LocalDate date = stringToLocalDate(rentalDate);
        returnDate = dateToString(date.plusWeeks(2));
        
    }

    public RentalStatus getRentalStatus() {
        return rentalStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public MovieCopy getMovieCopy() {
        return movieCopy;
    }

    // gets the rental date
    public String getRentalDate() {
        return rentalDate;
    }

    public String getReturnDate(){
        return returnDate;
    }



    public static boolean isOverdue(LocalDate returnDate) {
        if (returnDate == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        return today.isAfter(returnDate);
    }

     @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (this == object) return true;
        if (object instanceof Rental) {
           Rental otherRental = (Rental) object;
            return this.getId() == otherRental.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return String.format("Rental #%d <%s>: %s - %s", getId(), customer.getName(), customer.getEmail(), movieCopy.getMovie().getTitle());
    }

     @Override
    public String getSingleLineView() {
        return String.format("Id: #%d, name: %s email: <%s>, Movie #%d: title='%s'", getId(), customer.getName(), customer.getEmail(), movieCopy.getMovie().getTitle());
    }

    @Override
    public String getDetailedView() {
        return String.format("Id: #%d:\nName: %s\nEmail: <%s>\nTitle: %s", getId(), customer.getName(), customer.getEmail(), movieCopy.getMovie().getTitle());
    }

    @Override
    public String getLogView() {
        return String.format("Rental Entity - Id: #%d, Name: %s, Email: <%s>, title=%s,", getId(), customer.getName(), customer.getEmail(), movieCopy.getMovie().getTitle());
    }
}
