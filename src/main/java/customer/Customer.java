package customer;

import java.util.Objects;

import entity.Entity;

// Entity: identified by its id (kept and validated by Entity), and never allowed to exist in an
// invalid state. It knows its own CSV columns and its views, but nothing about storage.
public final class Customer extends Entity {
    // First line of a customers CSV file. The columns, in order, are the ones fromCSVLine reads and
    // toCSVLine writes, so change all three together.
    public static final String CSV_HEADER = "id,name,email";

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

    public static Customer fromCSVLine(String line) {
        // Fields are in the order: id, name, email
        String[] fields = line.split(",");
        if (fields.length != 3) {
            throw new IllegalArgumentException("CSV line must have exactly 3 fields");
        }
        int id;
        try {
            id = Integer.parseInt(fields[0].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Id must be a valid integer", e);
        }
        return new Customer(id, fields[1].trim(), fields[2].trim());
    }

    public String toCSVLine() {
        // Fields are in the order: id, name, email
        return String.format("%d,%s,%s", this.getId(), this.getName(), this.getEmail());
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
