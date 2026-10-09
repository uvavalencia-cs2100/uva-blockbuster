package customer;

import java.util.List;

import common.CsvMapping;
import common.CsvRow;

// How customers are stored in customers.csv: the only place that lists the columns. The names,
// the values written and the values read are all declared here. See CsvMapping for what each
// method must do.
public class CustomerCSV implements CsvMapping<Customer> {

    @Override
    public List<String> getCSVColumnNames() {
        return List.of("id", "name", "email");
    }

    @Override
    public List<String> toFields(Customer customer) {
        return List.of(
                String.valueOf(customer.getId()), customer.getName(), customer.getEmail());
    }

    // The Customer constructor validates the values, so an invalid line throws
    // IllegalArgumentException and the repository skips it
    @Override
    public Customer fromCSVRow(CsvRow row) {
        return new Customer(row.getInt("id"), row.getString("name"), row.getString("email"));
    }
}
