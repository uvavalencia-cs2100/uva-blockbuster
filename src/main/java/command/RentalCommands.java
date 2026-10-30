package command;

import app.AppContext;
import rental.Rental;
import rental.RentalService;

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
            List<String> form = new ArrayList<>(List.of("New rental"));
            int id = Integer.parseInt(MenuRunner.ask(form, "Id"));
            String name = MenuRunner.ask(form, "Name");
            String email = MenuRunner.ask(form, "Email");
            String title = MenuRunner.ask(form, "title");
            Rental rental = new Rental(id, name, email, title);
            rentalService().add(rental);
            log.info("Added " + rental.getLogView());
        } catch (NumberFormatException e) {
            log.warning("Id must be a number.");
        } catch (IllegalArgumentException e) {
            log.warning("Could not add customer: " + e.getMessage());
        }
    }

    public static void listRentals() {
        List<Rental> all = rentalService().list();
        if (all.isEmpty()) {
            log.info("No rentals.");
        }
        Screen.showSingleLineViews(all);
    }

    public static void showCustomer() {
        try {
            int id = Integer.parseInt(MenuRunner.prompt("Id of the customer to show: "));
            rentalService()
                    .findById(id)
                    .ifPresentOrElse(
                        Screen::showDetailedView,
                        () -> log.warning("No customer with id " + id));                    
        } catch (NumberFormatException e) {
            log.warning("Id must be a number.");
        }
    }

    public static void deleteRental() {
        try {
            int id = Integer.parseInt(MenuRunner.prompt("Id of the customer to delete: "));
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

