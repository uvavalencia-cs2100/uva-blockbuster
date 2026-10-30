package rental;
import common.Entity;
import customer.Customer;
import movie.Movie;
import movie.MovieCopy;

import java.util.*;

public final class Rental extends Entity{
    private static final long RENTAL_TIME = 14L * 24 * 60 * 60 * 1000;

    private int id;
    private Customer customer;
    private MovieCopy movieCopy;
    private Date rentalDate;
    private Date returnDate;
    private RentalStatus rentalStatus;
    

    public Rental(int id, MovieCopy movieCopy, Customer customer, RentalStatus rentalStatus){
        super(id);
        this.movieCopy = movieCopy;
        this.customer = customer;
        this.rentalStatus = rentalStatus;
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be a positive integer");
        }
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (movieCopy == null) {
            throw new IllegalArgumentException("Movie copy cannot be null");
        }
        
    }

    // sets the rental date and the return date to 14 days after the rental date
    public void setRentalDate(Date rentalDate) {
        if (rentalDate == null) {
            throw new IllegalArgumentException("Rental date cannot be null");
        }
        this.rentalDate = rentalDate;
        this.returnDate = new Date(rentalDate.getTime() + RENTAL_TIME);
    }

    // gets the rental date
    public Date getRentalDate() {
        return this.rentalDate;
    }

    // Gets the name
    public String getName(){
        return customer.getName();
    }
    
    // Gets the title
    public Movie getTitle(){
        return movieCopy.getMovie();
    }

    // Gets the email
    public String getEmail(){
        return customer.getEmail();
    }

     @Override
    public String getSingleLineView() {
        return String.format("Id: #%d, name: %s email: <%s>, Movie #%d: title='%s', Id: #%d", getId(), movieCopy.getMovie(), customer.getEmail(), customer.getName(), id);
    }

    @Override
    public String getDetailedView() {
        return String.format("Id: #%d:\nName: %s\nEmail: <%s>\nId: #%d\\nTitle: %s", getId(), movieCopy.getMovie(), customer.getEmail(), customer.getName(), id);
    }

    @Override
    public String getLogView() {
        return String.format("Rental Entity - Id: #%d, Name: %s, Email: <%s>, title=%s,", getId(), movieCopy.getMovie(), customer.getEmail(), customer.getName(), id);
    }
}
