package command;

import ui.Screen;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MenuRunner {
    private static final Logger log = Logger.getLogger(MenuRunner.class.getName());

    /**
     * Titles of the submenus we are inside of, outermost first; shown as the breadcrumb. The main
     * menu has no title, so it is not in here.
     *
     * <p>{@link Deque} is an interface (a "double-ended queue"): it lets us add and remove elements
     * at both ends. Here it works as a stack: {@code addLast} when a menu is entered and {@code
     * removeLast} when it is left, so it always holds the chain of open menus, e.g. {@code
     * [Customer Options]}.
     *
     * <p>{@link ArrayDeque} is the implementation. It is not a linked list: it keeps the elements
     * in a single circular array with a {@code head} and a {@code tail} index that wrap around to
     * position 0 when they pass the end. Adding or removing at either end is O(1) and needs no
     * pointers or node objects; when the array fills up it is replaced by a bigger copy. For this
     * use it is preferred over the legacy {@code Stack} and over {@code LinkedList}, which stores
     * one node (with prev/next pointers) per element.
     */
    private static final Deque<String> path = new ArrayDeque<>();

    // This command can be used to exit the program
    public static final Command QUIT =
            new Command(
                    'q',
                    "Quit",
                    () -> {
                        Screen.stop();
                        System.exit(0);
                    });

    // This command provides help information
    public static final Command HELP =
            new Command(
                    'h',
                    "Help",
                    () ->
                            Screen.setContent(
                                    List.of(
                                            "Type the key of a command and press Enter.",
                                            "¯\\_(ツ)_/¯")));

    // This command can be used to exit the current menu loop
    public static final Command BACK = new Command('b', "Back to Last Menu", () -> {});

    // Runs the main menu, the one every other menu hangs from. It has no title.
    public static void runMenu(List<Command> commands) {
        run(null, commands, () -> "");
    }

    // Runs a submenu with the given title and list of commands
    public static void runMenu(String title, List<Command> commands) {
        run(title, commands, () -> "");
    }

    // Same, and `status` is the state shown on the top border while this menu is on screen
    public static void runMenu(String title, List<Command> commands, Supplier<String> status) {
        run(title, commands, status);
    }

    // `title` is null for the main menu
    private static void run(String title, List<Command> commands, Supplier<String> status) {
        if (title != null) {
            path.addLast(title);
        }
        Screen.clearContent();
        boolean running = true;
        while (running) {
            Screen.setBreadcrumb(breadcrumb());
            Screen.setStatus(status);
            Screen.setMenu(menuLines(commands));

            String line = Screen.readLine("Enter your choice: ").trim();
            if (line.isEmpty()) {
                continue;
            }
            char input = Character.toLowerCase(line.charAt(0));
            Command chosen =
                    commands.stream().filter(c -> c.key() == input).findFirst().orElse(null);
            if (chosen == null) {
                log.warning("Invalid input '" + line + "'. Please try again.");
            } else if (chosen == BACK) {
                running = false; // Exit the current menu loop
            } else {
                Screen.clearContent();
                chosen.run();
            }
        }
        if (title != null) {
            path.removeLast();
        }
        Screen.clearContent();
    }

    // Submenu titles joined as a path, e.g. "Customer Options". At the main menu it is empty, and
    // the
    // top border just says "Menu".
    private static String breadcrumb() {
        return path.stream().collect(Collectors.joining(" > "));
    }

    // The commands laid out in two columns (filled top to bottom, left column first), indented
    // under the frame title
    private static List<String> menuLines(List<Command> commands) {
        List<String> entries =
                commands.stream()
                        .map(c -> String.format("  <%c> %s", c.key(), c.description()))
                        .toList();
        int rows = (entries.size() + 1) / 2;
        int width = entries.stream().mapToInt(String::length).max().orElse(0) + 4;

        List<String> lines = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            String left = entries.get(row);
            String right = row + rows < entries.size() ? entries.get(row + rows) : "";
            lines.add(String.format("%-" + width + "s%s", left, right));
        }
        return lines;
    }

    // Asks the user for a full line of text (may contain spaces)
    public static String prompt(String message) {
        String line;
        do {
            line = Screen.readLine(message);
        } while (line.isBlank());
        return line.trim();
    }

    // Shows the answers given so far (`form`) in the content frame, asks for the next one and adds
    // it
    // to `form`, so a multi-field form fills up on screen as the user goes
    public static String ask(List<String> form, String label) {
        Screen.setContent(form);
        String answer = prompt(label + ": ");
        form.add(label + ": " + answer);
        return answer;
    }

    public static void placeholder() {
        log.info("Not implemented yet.");
    }
}
