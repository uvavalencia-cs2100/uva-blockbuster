package common;

import java.util.List;

// One line of a CSV file, read by column name so a CsvMapping does not depend on the column order
// or repeat any parsing. Every problem is an IllegalArgumentException, which the repository logs
// and skips.
public final class CsvRow {

    private final List<String> header;
    private final String[] fields;

    // The line must have exactly as many fields as the header has columns
    CsvRow(List<String> header, String line) {
        String[] fields = line.split(",", -1);
        if (fields.length != header.size()) {
            throw new IllegalArgumentException(
                    "CSV line must have exactly " + header.size() + " fields");
        }
        this.header = header;
        this.fields = fields;
    }

    public String getString(String column) {
        int index = header.indexOf(column);
        if (index < 0) {
            throw new IllegalArgumentException("Unknown column: " + column);
        }
        return fields[index].trim();
    }

    public int getInt(String column) {
        try {
            return Integer.parseInt(getString(column));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(column + " must be a valid integer", e);
        }
    }

}
