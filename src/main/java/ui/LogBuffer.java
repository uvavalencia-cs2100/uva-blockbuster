package ui;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

// A java.util.logging Handler that keeps the latest records in memory so the
// Screen can print them in its lower frame instead of the console.
public class LogBuffer extends Handler {
    private static final int MAX_LINES = 200;
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private static final LogBuffer INSTANCE = new LogBuffer();

    private final Deque<String> lines = new ArrayDeque<>();

    private LogBuffer() {}

    // Replaces the default console handler with the buffer. Call once at startup.
    public static void install() {
        LogManager.getLogManager().reset();
        Logger root = Logger.getLogger("");
        root.setLevel(Level.INFO);
        root.addHandler(INSTANCE);
    }

    public static LogBuffer getInstance() {
        return INSTANCE;
    }

    @Override
    public synchronized void publish(LogRecord record) {
        String source = record.getLoggerName() == null ? "" : record.getLoggerName();
        source = source.substring(source.lastIndexOf('.') + 1);
        String line =
                String.format(
                        "%s %-7s %s: %s",
                        LocalTime.now().format(TIME),
                        record.getLevel().getName(),
                        source,
                        record.getMessage());
        lines.addLast(line);
        if (lines.size() > MAX_LINES) {
            lines.removeFirst();
        }
    }

    // The last `count` lines, oldest first
    public synchronized List<String> tail(int count) {
        List<String> all = new ArrayList<>(lines);
        return all.subList(Math.max(0, all.size() - count), all.size());
    }

    @Override
    public void flush() {}

    @Override
    public void close() {}
}
