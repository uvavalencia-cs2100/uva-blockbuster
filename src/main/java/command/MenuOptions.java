package command;

import app.AppContext;

import java.util.List;

// Builds every menu of the app and exposes a single entry point: runMenu().
public class MenuOptions {
    private final List<Command> customerCommands;
    private final List<Command> movieCommands;
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
                        new Command('a', "Add new Movie", MenuRunner::placeholder),
                        new Command('l', "List Movies", MenuRunner::placeholder),
                        new Command('d', "Delete Movies", MenuRunner::placeholder),
                        MenuRunner.BACK,
                        MenuRunner.HELP);

        rentalCommands =
                List.of(
                        new Command('r', "Rent a Movie", MenuRunner::placeholder),
                        new Command('t', "Return a Movie", MenuRunner::placeholder),
                        new Command('l', "List Rentals", MenuRunner::placeholder),
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
                                () -> MenuRunner.runMenu("Movie Options", movieCommands)),
                        new Command(
                                'r',
                                "Rental Options",
                                () -> MenuRunner.runMenu("Rental Options", rentalCommands)),
                        MenuRunner.QUIT,
                        MenuRunner.HELP);
    }

    private String customerStatus() {
        return "Customers: " + AppContext.getInstance().getCustomerService().list().size();
    }

    // Shows the main menu and runs until the user quits
    public void runMenu() {
        MenuRunner.runMenu(mainCommands);
    }
}
