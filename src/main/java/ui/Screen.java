package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Supplier;

// Full-screen terminal UI: a bordered window split into an upper frame
// (menu, data and state) and a lower frame (log), with the prompt on its last line.
// It runs on the terminal's alternate screen, so the scrollback is left untouched
// and restored on exit. Input stays line based (type a value, press Enter).
public class Screen {
    // ANSI escape sequences: "ESC [" (the Control Sequence Introducer) followed by a command
    private static final String ESC = "\u001b[";
    // Switches to the terminal's alternate screen buffer: a blank screen with no scrollback, and the
    // normal screen is saved. This is what vim, less and k9s do, so the app owns the whole window.
    private static final String ENTER_ALT_SCREEN = ESC + "?1049h";
    // Switches back to the normal screen buffer, restoring what the user had before the app started
    private static final String LEAVE_ALT_SCREEN = ESC + "?1049l";
    // Erases the whole screen (the cursor does not move)
    private static final String CLEAR_SCREEN = ESC + "2J";
    // Moves the cursor to the top-left corner (row 1, column 1)
    private static final String CURSOR_HOME = ESC + "H";
    private static final String BORDER = ESC + "36m"; // cyan
    private static final String RESET = ESC + "0m";
    // Frame colors: light gray text (256-color palette 252) on blue menu and log frames and a dark gray content frame, so the
    // frames stand out from each other whatever theme the terminal uses
    private static final String MENU_COLOR = ESC + "38;5;252;48;5;24m";
    private static final String CONTENT_COLOR = ESC + "38;5;252;48;5;235m";
    private static final String LOG_COLOR = ESC + "38;5;252;48;5;24m";
    private static final int DEFAULT_ROWS = 24;
    private static final int DEFAULT_COLS = 80;
    private static final int LOG_ROWS = 6; // most lines the log frame shows
    private static final int MIN_ROWS = 10;
    private static final int MIN_COLS = 30;

    private static final Scanner scan = new Scanner(System.in);
    private static boolean started = false;

    private static String title = "Menu";
    private static String breadcrumb = "";
    private static Supplier<String> status = () -> "";
    private static List<String> menu = List.of();
    private static List<String> content = List.of();

    private Screen() {
    }

    // Takes over the terminal. Safe to call more than once.
    public static void start() {
        if (started) {
            return;
        }
        started = true;
        System.out.print(ENTER_ALT_SCREEN + CLEAR_SCREEN + CURSOR_HOME);
        System.out.flush();
        // Registers a thread the JVM runs just before it exits (System.exit, Ctrl+C, end of main), so
        // the terminal always leaves the alternate screen, even if the app stops unexpectedly.
        // stop() is idempotent, so it is harmless if it already ran.
        Runtime.getRuntime().addShutdownHook(new Thread(Screen::stop));
    }

    // Gives the terminal back as it was before start()
    public static void stop() {
        if (!started) {
            return;
        }
        started = false;
        System.out.print(RESET + LEAVE_ALT_SCREEN);
        System.out.flush();
    }

    public static void setBreadcrumb(String path) {
        breadcrumb = path;
    }

    // The state shown on the right of the top border, re-read on every render
    public static void setStatus(Supplier<String> supplier) {
        status = supplier;
    }

    public static void setMenu(List<String> lines) {
        menu = lines;
    }

    // Data shown under the menu until it is replaced or cleared
    public static void setContent(List<String> lines) {
        content = lines;
    }

    public static void clearContent() {
        content = List.of();
    }

    // Draws the screen, shows the prompt on the input line and reads what the user types
    public static String readLine(String prompt) {
        render(prompt);
        try {
            return scan.nextLine();
        } catch (NoSuchElementException e) { // input closed (Ctrl+D)
            stop();
            System.exit(0);
            return "";
        }
    }

    private static void render(String prompt) {
        int[] size = terminalSize();
        int rows = size[0];
        int cols = size[1];
        int inner = cols - 2;
        // Rows left for the three frames: minus top border, two dividers and bottom border
        int bodyRows = rows - 4;
        int logRows = Math.min(LOG_ROWS, bodyRows / 3);
        int menuRows = Math.min(menu.size(), bodyRows - logRows - 2);
        int contentRows = bodyRows - logRows - menuRows; // the last one is the prompt line

        StringBuilder out = new StringBuilder(RESET + CURSOR_HOME + CLEAR_SCREEN);
        out.append(border(titleBar(inner), '┌', '┐'));
        appendRows(out, menu, menuRows, inner, MENU_COLOR);
        out.append(border(bar(" Content ", "", inner), '├', '┤'));
        List<String> shown = new ArrayList<>(clip(content, contentRows - 1));
        while (shown.size() < contentRows - 1) {
            shown.add("");
        }
        shown.add(prompt);
        appendRows(out, shown, contentRows, inner, CONTENT_COLOR);
        out.append(border(bar(" Logs ", "", inner), '├', '┤'));
        appendRows(out, LogBuffer.getInstance().tail(logRows), logRows, inner, LOG_COLOR);
        // No newline after the bottom border: on the last row it would scroll the whole screen up
        out.append(BORDER).append('└').append("─".repeat(inner)).append('┘').append(RESET);

        // Back to the end of the prompt, on the last row of the content frame
        int promptRow = 2 + menuRows + contentRows;
        int promptCol = Math.min(prompt.length() + 1, inner) + 2;
        out.append(ESC).append(promptRow).append(';').append(promptCol).append('H');
        out.append(CONTENT_COLOR); // so what the user types gets the content background too
        System.out.print(out);
        System.out.flush();
    }

    // Keeps the lines that fit in `rows`, replacing the last one with a note when some are left out
    private static List<String> clip(List<String> lines, int rows) {
        if (lines.size() <= rows) {
            return lines;
        }
        List<String> shown = new ArrayList<>(lines.subList(0, Math.max(rows - 1, 0)));
        shown.add("... (" + (lines.size() - shown.size()) + " more lines)");
        return shown;
    }

    private static String titleBar(int inner) {
        String left = " " + title + (breadcrumb.isEmpty() ? "" : " | " + breadcrumb) + " ";
        String right = status.get();
        return bar(left, right.isEmpty() ? "" : " " + right + " ", inner);
    }

    // A horizontal line of `inner` chars with text anchored on the left and on the right
    private static String bar(String left, String right, int inner) {
        left = fit(left, Math.max(inner - right.length() - 2, 0)).stripTrailing() + " ";
        int fill = Math.max(inner - 1 - left.length() - right.length(), 0);
        return "─" + left + "─".repeat(fill) + right;
    }

    private static String border(String line, char left, char right) {
        return BORDER + left + line + right + RESET + "\n";
    }

    private static void appendRows(StringBuilder out, List<String> lines, int count, int inner, String color) {
        for (int i = 0; i < count; i++) {
            String text = i < lines.size() ? " " + lines.get(i) : "";
            out.append(BORDER).append('│').append(RESET).append(color).append(fit(text, inner)).append(RESET)
               .append(BORDER).append('│').append(RESET).append('\n');
        }
    }

    // Cuts or pads the text to exactly `width` chars
    private static String fit(String text, int width) {
        if (text.length() > width) {
            return text.substring(0, Math.max(width, 0));
        }
        return text + " ".repeat(width - text.length());
    }

    // Terminal size as {rows, cols}, never smaller than MIN_ROWS x MIN_COLS so the layout always fits
    private static int[] terminalSize() {
        int[] size = readTerminalSize();
        return new int[] { Math.max(size[0], MIN_ROWS), Math.max(size[1], MIN_COLS) };
    }

    // Asks stty on Unix, falls back to a classic 80x24
    private static int[] readTerminalSize() {
        try {
            Process p = new ProcessBuilder("sh", "-c", "stty size < /dev/tty").redirectErrorStream(true).start();
            String[] parts = new String(p.getInputStream().readAllBytes()).trim().split(" ");
            p.waitFor();
            if (parts.length == 2) {
                return new int[] { Integer.parseInt(parts[0]), Integer.parseInt(parts[1]) };
            }
        } catch (IOException | NumberFormatException e) {
            // no stty or no terminal: use the default
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return new int[] { DEFAULT_ROWS, DEFAULT_COLS };
    }
}
