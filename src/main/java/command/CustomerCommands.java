package command;

import app.AppContext;

import customer.Customer;
import customer.CustomerService;

import ui.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

// Actions behind the customer menu. They go through the CustomerService held by AppContext.
public class CustomerCommands {

    private static final Logger log = Logger.getLogger(CustomerCommands.class.getName());

    private CustomerCommands() {}

    private static CustomerService customerService() {
        return AppContext.getInstance().getCustomerService();
    }

    public static void addCustomer() {
        try {
            List<String> form = new ArrayList<>(List.of("New customer"));
            int id = Integer.parseInt(MenuRunner.ask(form, "Id"));
            String name = MenuRunner.ask(form, "Name");
            String email = MenuRunner.ask(form, "Email");
            Customer customer = new Customer(id, name, email);
            customerService().add(customer);
            log.info("Added " + customer.getLogView());
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
        Screen.showSingleLineViews(all);
    }

    public static void showCustomer() {
        try {
            int id = Integer.parseInt(MenuRunner.prompt("Id of the customer to show: "));
            customerService()
                    .findById(id)
                    .ifPresentOrElse(
                            Screen::showDetailedView,
                            () -> log.warning("No customer with id " + id));
        } catch (NumberFormatException e) {
            log.warning("Id must be a number.");
        }
    }

    public static void deleteCustomer() {
        try {
            int id = Integer.parseInt(MenuRunner.prompt("Id of the customer to delete: "));
            Optional<Customer> customer = customerService().findById(id);
            if (customer.isPresent() && customerService().remove(id)) {
                log.info("Deleted " + customer.get().getLogView());
            } else {
                log.warning("No customer with id " + id);
            }
        } catch (NumberFormatException e) {
            log.warning("Id must be a number.");
        }
    }
}
