package customer;

import java.util.Objects;

import common.Entity;

// Entity: identified by its id (kept and validated by Entity), and never allowed to exist in an
// invalid state. It knows its views, but nothing about storage: how it is stored in a CSV file is
// described by CustomerCSV.
public final class Customer extends Entity {
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
