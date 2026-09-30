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
        // Assuming fields are in the order: name, email, id
        String[] fields = line.split(",");
        if (fields.length != 3) {
            System.out.println("CSV line must have exactly 3 fields");
            return null;
        }
        String name = fields[0].trim();
        String email = fields[1].trim();
        int id;
        try {
            id = Integer.parseInt(fields[2].trim());
        } catch (NumberFormatException e) {
            System.out.println("Id must be a valid integer");
            return null;
        }
        return new Customer(name, email, id);
    }

    public String toCSVLine() {
        // Assuming fields are in the order: name, email, id
        return String.format("%s,%s,%d", getName(), getEmail(), getId());
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
