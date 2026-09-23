import java.util.List;

import command.Command;
import command.MenuRunner;

public class Main {
        
    // Lists customer options and takes user input to navigate to the appropriate method
    public static void CustomerOptions() {
        Command add = new Command('a', "Add new Customer", MenuRunner::placeholder);
        Command list = new Command('l', "List Customers", MenuRunner::placeholder);
        Command delete = new Command('d', "Delete Customers", MenuRunner::placeholder);
        
        List<Command> commands = List.of(add, list, delete, MenuRunner.BACK, MenuRunner.HELP);
        
        MenuRunner.runMenu("Customer Options:", commands);
    }

    // Lists movie options and takes user input to navigate to the appropriate method
    public static void MovieOptions() {
        Command add = new Command('a', "Add new Movie", MenuRunner::placeholder);
        Command list = new Command('l', "List Movies", MenuRunner::placeholder);
        Command delete = new Command('d', "Delete Movies", MenuRunner::placeholder);
        
        List<Command> commands = List.of(add, list, delete, MenuRunner.BACK, MenuRunner.HELP);
        
        MenuRunner.runMenu("Movie Options:", commands);
    }

    // Lists movie options and takes user input to navigate to the appropriate method
    public static void RentalOptions() {
        Command rent = new Command('r', "Rent a Movie", MenuRunner::placeholder);
        Command returnMovie = new Command('t', "Return a Movie", MenuRunner::placeholder);
        Command list = new Command('l', "List Rentals", MenuRunner::placeholder);
        
        List<Command> commands = List.of(rent, returnMovie, list, MenuRunner.BACK, MenuRunner.HELP);
        
        MenuRunner.runMenu("Rental Options:", commands);
    }

    // Lists main menu options and takes user input to navigate to the appropriate method
    public static void main(String[] args) {
        Command customerOptions = new Command('c', "Customer Options", Main::CustomerOptions);
        Command movieOptions = new Command('m', "Movie Options", Main::MovieOptions);
        Command rentalOptions = new Command('r', "Rental Options", Main::RentalOptions);

        List<Command> commands = List.of(customerOptions, movieOptions, rentalOptions, MenuRunner.QUIT, MenuRunner.HELP);
        
        MenuRunner.runMenu("Welcome to Blockbuster!", commands);
    }
}