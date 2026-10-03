package command;

import java.util.List;

import app.AppContext;
import customer.Customer;
import customer.CustomerService;

// Actions behind the customer menu. They go through the CustomerService held by AppContext.
public class CustomerCommands {

    private CustomerCommands() {
    }

    private static CustomerService customerService() {
        return AppContext.getInstance().getCustomerService();
    }

    public static void addCustomer() {
        try {
            String name = MenuRunner.prompt("Name: ");
            String email = MenuRunner.prompt("Email: ");
            int id = Integer.parseInt(MenuRunner.prompt("Id: "));
            customerService().add(new Customer(id, name, email));
            System.out.println("Customer added.");
        } catch (NumberFormatException e) {
            System.out.println("Id must be a number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Could not add customer: " + e.getMessage());
        }
    }

    public static void listCustomers() {
        List<Customer> all = customerService().list();
        if (all.isEmpty()) {
            System.out.println("No customers.");
        }
        for (Customer c : all) {
            System.out.println(c);
        }
    }

    public static void deleteCustomer() {
        try {
            int id = Integer.parseInt(MenuRunner.prompt("Id of the customer to delete: "));
            if (customerService().remove(id)) {
                System.out.println("Customer deleted.");
            } else {
                System.out.println("No customer with id " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Id must be a number.");
        }
    }
}
