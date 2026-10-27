package rental;

import java.util.*;
import common.CsvMapping;
import common.CsvRow;



// How rentals are stored in rentals.csv: the only place that lists the columns.
// The names, the values written, and the values read are all declared here.
// See CsvMapping for what each method must do.
public class RentalCSV implements  CsvMapping<Rental> {

    @Override 
    public List<String> getCSVColumnNames() {
        return List.of("id", "name", "email", "title");
    }

    @Override 
    public List<String> toFields(Rental rental) {
        return List.of(
            String.valueOf(rental.getId()),
            rental.getName(),
            rental.getEmail(),
            rental.getTitle()

        );
    }
    
    // The Rental constructor validates the values, so an invalid line throws
    // IllegalArgumentException and the repository skips it
    @Override 
    public Rental fromCSVRow(CsvRow row) {
        return new Rental(row.getInt("id"), 
            row.getString("name"),
            row.getString("Email"),
            row.getString("Title")
        );
    }
}
