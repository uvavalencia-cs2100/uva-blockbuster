package command;

import app.AppContext;
import customer.Customer;
import customer.CustomerService;
import movie.MovieCopy;
import movie.MovieCopyService;
import rental.Rental;
import rental.RentalService;
import rental.RentalStatus;
import ui.Screen;
import java.util.*;
import java.util.logging.Logger;

// Actions behind the rental menu. They go through the RentalService held by Appcontext.
public class RentalCommands {
    
    private static final Logger log = Logger.getLogger(RentalCommands.class.getName());

    private RentalCommands() {}

    private static RentalService rentalService() {
        return AppContext.getInstance().getRentalService();
    }

    public static void addRental() {
        try {
            MovieCopyService movieCopyService = AppContext.getInstance().getMovieCopyService();
            CustomerService customerService = AppContext.getInstance().getCustomerService();

            List<String> form = new ArrayList<>(List.of("New rental"));

            int id = Integer.parseInt(MenuRunner.ask(form, "Id"));

            int customerId = Integer.parseInt(MenuRunner.ask(form, "Customer Id"));
            Customer customer = customerService.findById(customerId).orElseThrow(()-> new IllegalArgumentException("No customer with id " + customerId));

            int movieCopyId = Integer.parseInt(MenuRunner.ask(form, "Movie Copy Id"));
            MovieCopy movieCopy = movieCopyService.findById(movieCopyId).orElseThrow(()-> new IllegalArgumentException("No movie copy with id " + movieCopyId));

            String rentalDate = MenuRunner.ask(form, "Rental Date");
            String dueDate = MenuRunner.ask(form, "Due Date");
            int status = Integer.parseInt(MenuRunner.ask(form, "Status"));
            RentalStatus rentalStatus = RentalStatus.fromValue(status);

            Rental rental = new Rental(id, customer, movieCopy, rentalDate, dueDate, rentalStatus);
            rentalService().add(rental);
            log.info("Added " + rental.getLogView());
        } catch (NumberFormatException e) {
            log.warning("Id must be a number.");
        } catch (IllegalArgumentException e) {
            log.warning("Could not add rental: " + e.getMessage());
        }
    }

    public static void listRentals() {
        List<Rental> all = rentalService().list();
        if (all.isEmpty()) {
            log.info("No rentals.");
        }
        Screen.showSingleLineViews(all);
    }

    public static void showRental() {
        try {
            int id = Integer.parseInt(MenuRunner.prompt("Id of the rental to show: "));
            rentalService()
                    .findById(id)
                    .ifPresentOrElse(
                        Screen::showDetailedView,
                        () -> log.warning("No rental with id " + id));                    
        } catch (NumberFormatException e) {
            log.warning("Id must be a number.");
        }
    }

    public static void deleteRental() {
        try {
            int id = Integer.parseInt(MenuRunner.prompt("Id of the rental to delete: "));
            Optional<Rental> rental = rentalService().findById(id);
            if (rental.isPresent() && rentalService().remove(id)) {
                log.info("Deleted " + rental.get().getLogView());
            } else {
                log.warning("No rental with id " + id);
            }
        } catch (NumberFormatException e) {
            log.warning("Id must be a number.");
        }
    }
}

