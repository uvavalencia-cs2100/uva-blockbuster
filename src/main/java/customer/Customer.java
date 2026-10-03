package customer;

import java.util.Objects;

// Entity: identified by its id, and never allowed to exist in an invalid state.
public class Customer {
    private final int id;
    private String name;
    private String email;

    public Customer(String name, String email, int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be a positive integer");
        }
        this.id = id;
        setName(name);
        setEmail(email);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
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
        return new Customer(fields[1].trim(), fields[2].trim(), id);
    }

    public static String toCSVLine(Customer customer) {
        // Fields are in the order: id, name, email
        return String.format("%d,%s,%s", customer.getId(), customer.getName(), customer.getEmail());
    }

    // Two customers are the same customer if they share an id
    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (this == object) return true;
        if (object instanceof Customer){
            Customer otherCustomer = (Customer) object;
            return this.id == otherCustomer.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Customer #%d: %s <%s>", id, name, email);
    }
}
