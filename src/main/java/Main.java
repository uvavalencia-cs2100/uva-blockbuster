import java.util.Scanner;

import dataloader.DataLoader;

public class Main {
    
    private static final Scanner scan = new Scanner(System.in);
    
    // Lists customer options and takes user input to navigate to the appropriate method
    public static void CustomerOptions() {
        boolean running = true;
        while (running) {
            System.out.print("""
                Customer Options:
                1. Add new Customer (press n)
                2. List Customers (press l)
                3. Delete Customers (press d)
                4. Back to Main Menu (press b)
                5. Help (press h)
                """);
            
            char input = scan.next().toLowerCase().charAt(0);
            switch (input) {
                case 'n' -> PlaceholderOption(); //Customer.newCustomer();
                case 'l' -> PlaceholderOption(); //Customer.listCustomers();
                case 'd' -> PlaceholderOption(); //Customer.deleteCustomer();
                case 'b' -> running = false;
                case 'h' -> HelpOptions();
                default -> System.out.println("Invalid input. Please try again.");
            }
        }   
    }
    
    // Unimplemented method for help options
    public static void HelpOptions() {
        
    }

    public static void PlaceholderOption() {
        
    }

    public static void Quit() {
        System.out.println("Thank you for using Blockbuster!");
        System.exit(0);
    }

    public static void main(String[] args) {
        // Lists main menu options and takes user input to navigate to the appropriate method
        DataLoader.readFile("data/customers.csv", "Customer");
        DataLoader.readFile("data/movies.csv", "Movie");
        
        while (true) {
            System.out.print("""
                Welcome to Blockbuster!
                Available Options:
                1. Customer Options (press c)
                2. Movie Options (press m)
                3. Rental Options (press r)
                4. Quit (press q)
                5. Help (press h)
                """);
            
            char input = scan.next().toLowerCase().charAt(0);
            
            // Navigate to the appropriate method based on user input
            switch (input) {
                case 'c' -> CustomerOptions();
                case 'm' -> PlaceholderOption(); //Movie.MovieOptions();
                case 'r' -> PlaceholderOption(); //Rental.RentalOptions();
                case 'q' -> Quit();
                case 'h' -> HelpOptions();
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }
}