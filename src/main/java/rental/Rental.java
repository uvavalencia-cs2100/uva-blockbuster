package rental;

import customer.Customer;

import movie.MovieCopy;
import movie.MovieStatus;

import java.util.Date;

public final class Rental {
    private static final long RENTAL_TIME = 14L * 24 * 60 * 60 * 1000;

    private final int id;
    private final Customer customer;
    private final MovieCopy movieCopy;
    private Date rentalDate;
    private Date returnDate;
    private MovieStatus status;

    public Rental(
            int id,
            Customer customer,
            MovieCopy movieCopy,
            Date rentalDate,
            Date returnDate,
            MovieStatus status) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be a positive integer");
        }
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (movieCopy == null) {
            throw new IllegalArgumentException("Movie copy cannot be null");
        }
        this.id = id;
        this.customer = customer;
        this.movieCopy = movieCopy;
        setRentalDate(rentalDate);
        this.status = MovieStatus.RENTED;
    }

    public int getId() {
        return id;
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

    public Customer getCustomer() {
        return this.customer;
    }

    public void returnCopy() {
        this.status = MovieStatus.AVAILABLE;
        movieCopy.setStatus(this.status);
    }

    public void markLost() {
        this.status = MovieStatus.LOST;
        movieCopy.setStatus(this.status);
    }

    public MovieStatus getStatus() {
        return status;
    }
}
