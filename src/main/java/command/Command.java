package command;

public class Command {
    private final char key;
    private final String description;
    private final Runnable action;
    
    public Command(char key, String description, Runnable action) {
        this.key = key;
        this.description = description;
        this.action = action;
    }

    public char key() {
        return key;
    }

    public String description() {
        return description;
    }

    public void run() {
        action.run();
    }
}

