package command;
import java.util.List;
import java.util.Scanner;

public class MenuRunner {
    private static final Scanner scan = new Scanner(System.in);

    // This command can be used to exit the program
    public static final Command QUIT = new Command('q', "Quit", () -> {
        System.out.println("Thank you for using Blockbuster!");
        System.exit(0);
    });
    
    // This command provides help information
    public static final Command HELP = new Command('h', "Help", () -> {
        System.out.println("¯\\_(ツ)_/¯");
    });

    // This command can be used to exit the current menu loop
    public static final Command BACK = new Command('b', "Back to Last Menu", () -> {});

    // Runs a menu with the given title and list of commands
    public static void runMenu(String title, List<Command> commands) {
        boolean running = true;
        while (running) {
            System.out.println(title);
            
            for (Command command : commands) {
                System.out.printf("Press %c: %s%n", command.key(), command.description());
            }

            System.out.print("Enter your choice: ");
            char input = scan.next().toLowerCase().charAt(0);
            boolean found = false;
            for (Command command : commands) {
                if (command.key() == input) {
                    if (command == BACK) {
                        running = false; // Exit the current menu loop
                    } else {
                        command.run();
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public static void placeholder() {
        
    }
}