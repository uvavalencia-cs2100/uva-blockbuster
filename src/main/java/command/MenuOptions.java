package command;

import app.AppContext;
import rental.Rental;

import java.util.List;

// Builds every menu of the app and exposes a single entry point: runMenu().
public class MenuOptions {
    private final List<Command> customerCommands;
    private final List<Command> movieCommands;
    private final List<Command> movieCopyCommands;
    private final List<Command> rentalCommands;
    private final List<Command> mainCommands;

    public MenuOptions() {
        customerCommands =
                List.of(
                        new Command('a', "Add new Customer", CustomerCommands::addCustomer),
                        new Command('l', "List Customers", CustomerCommands::listCustomers),
                        new Command('s', "Show Customer", CustomerCommands::showCustomer),
                        new Command('d', "Delete Customers", CustomerCommands::deleteCustomer),
                        MenuRunner.BACK,
                        MenuRunner.HELP);

        movieCommands =
                List.of(
                        new Command('a', "Add new Movie", MovieCommands::addMovie),
                        new Command('l', "List Movies", MovieCommands::listMovies),
                        new Command('s', "Show Movie", MovieCommands::showMovie),
                        new Command('d', "Delete Movies", MovieCommands::deleteMovie),
                        MenuRunner.BACK,
                        MenuRunner.HELP);

        movieCopyCommands =
                List.of(
                        new Command('a', "Add new Movie Copy", MovieCopyCommands::addMovieCopy),
                        new Command('l', "List Movie Copies", MovieCopyCommands::listMovieCopies),
                        new Command('s', "Show Movie Copy", MovieCopyCommands::showMovieCopy),
                        new Command('d', "Delete Movies Copy", MovieCopyCommands::deleteMovieCopy),
                        MenuRunner.BACK,
                        MenuRunner.HELP);                        

        rentalCommands =
                List.of(
                        new Command('r', "Rent a Movie", RentalCommands::addRental),
                        new Command('t', "Show Rental", RentalCommands::showRental),
                        new Command('l', "List Rentals", RentalCommands::listRentals),
                        new Command('d', "Delete Rental", RentalCommands::deleteRental),
                        MenuRunner.BACK,
                        MenuRunner.HELP);

        mainCommands =
                List.of(
                        new Command(
                                'c',
                                "Customer Options",
                                () ->
                                        MenuRunner.runMenu(
                                                "Customer Options",
                                                customerCommands,
                                                this::customerStatus)),
                        new Command(
                                'm',
                                "Movie Options",
                                () -> 
                                        MenuRunner.runMenu(
                                                "Movie Options", 
                                                movieCommands,
                                                this::movieStatus)),
                        new Command(
                                'x',
                                "Movie Copy Options",
                                () ->
                                        MenuRunner.runMenu(
                                                "Movie Copy Options",
                                                movieCopyCommands,
                                                this::movieCopyStatus)),
                        new Command(
                                'r',
                                "Rental Options",
                                () -> MenuRunner.runMenu("Rental Options", rentalCommands, this::rentalStatus)),
                        MenuRunner.QUIT,
                        MenuRunner.HELP);
    }

    private String customerStatus() {
        return "Customers: " + AppContext.getInstance().getCustomerService().list().size();
    }

    private String movieStatus() {
        return "Movies: " + AppContext.getInstance().getMovieService().list().size();
    }

    private String movieCopyStatus() {
        return "Movie Copies: " + AppContext.getInstance().getMovieCopyService().list().size();
    }

    private String rentalStatus() {
        return "Rentals: " + AppContext.getInstance().getRentalService().list().size();
    }

    // Shows the main menu and runs until the user quits
    public void runMenu() {
        MenuRunner.runMenu(mainCommands);
    }
}
