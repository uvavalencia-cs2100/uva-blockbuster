package common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

// Repository base: stores one kind of entity as a CSV file. It owns everything about the file
// (path, header, skipping bad lines, writing through a temporary file); the CsvMapping owns what
// the columns are. The service only knows read() and write(), so it never sees paths, headers or
// lines. A subclass (e.g. CustomerRepository) only gives the file name and the mapping.
public abstract class AbstractRepository<T extends Entity> {

    // Named after the concrete subclass, so log records show e.g. customer.CustomerRepository
    protected final Logger log = Logger.getLogger(getClass().getName());

    private final Path file;
    private final CsvMapping<T> mapping;

    // `dataPath` is the folder that holds the file
    protected AbstractRepository(String dataPath, String fileName, CsvMapping<T> mapping) {
        this.file = Path.of(dataPath, fileName);
        this.mapping = mapping;
    }

    // Reads every valid element. Invalid lines are logged and skipped; a missing or unreadable
    // file is logged and gives an empty list.
    public List<T> read() {
        List<T> elements = new ArrayList<>();
        List<String> lines;
        try {
            lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.severe("Error reading " + file + ": " + e.getMessage());
            return elements;
        }
        // The first line is the header
        for (String line : lines.stream().skip(1).filter(l -> !l.isBlank()).toList()) {
            try {
                T element = mapping.fromCSVRow(new CsvRow(mapping.getCSVColumnNames(), line));
                elements.add(element);
                log.info("Loaded " + element.getLogView());
            } catch (IllegalArgumentException e) {
                log.warning("Skipping invalid line in " + file.getFileName() + ": " + e.getMessage());
            }
        }
        return elements;
    }

    // Writes all the elements, so changes survive a restart. It writes to a temporary file first
    // and then replaces the real one, so a failure never leaves it half written.
    public void write(List<T> elements) {
        Path temp = file.resolveSibling(file.getFileName() + ".tmp");
        List<String> lines = new ArrayList<>();
        lines.add(String.join(",", mapping.getCSVColumnNames()));
        for (T element : elements) {
            lines.add(String.join(",", mapping.toFields(element)));
        }
        try {
            Files.write(temp, lines, StandardCharsets.UTF_8);
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING);
            log.info("Saved " + elements.size() + " elements to " + file);
        } catch (IOException e) {
            log.severe("Could not save elements to " + file + ": " + e.getMessage());
        }
    }

}
