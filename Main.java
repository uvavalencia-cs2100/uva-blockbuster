import java.util.Scanner;

public class Main {
    
    private static Scanner scan = new Scanner(System.in);
    
    // Lists customer options and takes user input to navigate to the appropriate method
    public static void CustomerOptions() {
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
            case 'n' -> Customer.newCustomer();
            case 'l' -> Customer.listCustomers();
            case 'd' -> Customer.deleteCustomer();
            case 'b' -> main(new String[]{});
            case 'h' -> HelpOptions();
            default -> System.out.println("Invalid input. Please try again.");
        }
    }
    
    // Unimplemented method for help options
    public static void HelpOptions() {
        
    }

    public static void main(String[] args) {
        // Lists main menu options and takes user input to navigate to the appropriate method
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
            case 'm' -> Movie.MovieOptions();
            case 'r' -> Rental.RentalOptions();
            case 'q' -> System.out.println("Thank you for using Blockbuster!");
            case 'h' -> HelpOptions();
            default -> System.out.println("Invalid input. Please try again.");
        }
        
        scan.close();
    }
}