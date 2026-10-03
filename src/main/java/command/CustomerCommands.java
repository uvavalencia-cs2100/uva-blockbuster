package command;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import app.AppContext;
import customer.Customer;
import customer.CustomerService;
import ui.Screen;

// Actions behind the customer menu. They go through the CustomerService held by AppContext.
public class CustomerCommands {

    private static final Logger log = Logger.getLogger(CustomerCommands.class.getName());

    private CustomerCommands() {
    }

    private static CustomerService customerService() {
        return AppContext.getInstance().getCustomerService();
    }

    public static void addCustomer() {
        try {
            List<String> form = new ArrayList<>(List.of("New customer"));
            int id = Integer.parseInt(MenuRunner.ask(form, "Id"));
            String name = MenuRunner.ask(form, "Name");
            String email = MenuRunner.ask(form, "Email");
            customerService().add(new Customer(id, name, email));
            log.info("Customer added: " + id);
        } catch (NumberFormatException e) {
            log.warning("Id must be a number.");
        } catch (IllegalArgumentException e) {
            log.warning("Could not add customer: " + e.getMessage());
        }
    }

    public static void listCustomers() {
        List<Customer> all = customerService().list();
        if (all.isEmpty()) {
            log.info("No customers.");
        }
        Screen.setContent(all.stream().map(Customer::toString).toList());
    }

    public static void deleteCustomer() {
        try {
            int id = Integer.parseInt(MenuRunner.prompt("Id of the customer to delete: "));
            if (customerService().remove(id)) {
                log.info("Customer deleted: " + id);
            } else {
                log.warning("No customer with id " + id);
            }
        } catch (NumberFormatException e) {
            log.warning("Id must be a number.");
        }
    }
}
