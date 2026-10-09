package customer;

import java.util.List;
import java.util.Objects;

import entity.CsvMapping;
import entity.CsvRow;
import entity.Entity;

// Entity: identified by its id (kept and validated by Entity), and never allowed to exist in an
// invalid state. It knows its own CSV columns (CsvMapping) and its views, but nothing about
// storage.
public final class Customer extends Entity implements CsvMapping<Customer> {
    private String name;
    private String email;

    public Customer(int id, String name, String email) {
        super(id);
        setName(name);
        setEmail(email);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (name.contains(",")) { // it would break the CSV file
            throw new IllegalArgumentException("Name cannot contain a comma");
        }
        this.name = name.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email must contain '@'");
        }
        if (email.contains(",")) { // it would break the CSV file
            throw new IllegalArgumentException("Email cannot contain a comma");
        }
        this.email = email.trim();
    }

    // The columns of customers.csv: the only place that lists them. The header, the values written
    // and the values read are all declared here. See CsvMapping for what each method must do.
    // These work on any customer, which is only used as the mapping, never for its own data.
    @Override
    public List<String> getCSVColumnNames() {
        return List.of("id", "name", "email");
    }

    @Override
    public List<String> toFields(Customer customer) {
        return List.of(String.valueOf(customer.getId()), customer.name, customer.email);
    }

    @Override
    public Customer fromCSVRow(CsvRow row) {
        return new Customer(row.getInt("id"), row.getString("name"), row.getString("email"));
    }

    // Two customers are the same customer if they share an id
    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (this == object) return true;
        if (object instanceof Customer) {
            Customer otherCustomer = (Customer) object;
            return this.getId() == otherCustomer.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return String.format("Customer #%d: %s <%s>", getId(), name, email);
    }

    @Override
    public String getSingleLineView() {
        return String.format("Id: #%d, name: %s email: <%s>", getId(), name, email);
    }

    @Override
    public String getDetailedView() {
        return String.format("Id: #%d:\nName: %s\nEmail: <%s>", getId(), name, email);
    }

    @Override
    public String getLogView() {
        return String.format("Customer Entity - Id: #%d, Name: %s, Email: <%s>", getId(), name, email);
    }
}
