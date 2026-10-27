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
    private Movie movie;
    private MovieCopy movieCopy;
    private String name;
    private String title;
    private String email;
    private Date rentalDate;
    private Date returnDate;
    

    public Rental(int id, String name, String email, String title){
        super(id);
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
        return rentalDate;
    }

    // Gets the name, but not from customer class (which I want)
    public String getName(){
        return customer.getName();
    }
    
    // Gets the title, but not from the movie class (which I want)
    public String getTitle(){
        return movie.getTitle();
    }

    // Gets the email, but not from the customer class (which I want)
    public String getEmail(){
        return customer.getEmail();
    }

     @Override
    public String getSingleLineView() {
        return String.format("Id: #%d, name: %s email: <%s>, Movie #%d: title='%s', Id: #%d", getId(), name, email, title, id);
    }

    @Override
    public String getDetailedView() {
        return String.format("Id: #%d:\nName: %s\nEmail: <%s>\nId: #%d\\nTitle: %s", getId(), name, email, title, id);
    }

    @Override
    public String getLogView() {
        return String.format("Rental Entity - Id: #%d, Name: %s, Email: <%s>, title=%s,", getId(), name, email, title, id);
    }
}
