package app;

import customer.CustomerService;

// Singleton: the one place where the shared services live, so the menus and the
// DataLoader all talk to the same CustomerService instance.
public class AppContext {
    private static final AppContext INSTANCE = new AppContext();

    private final CustomerService customerService = new CustomerService();

    private AppContext() {
    }

    public static AppContext getInstance() {
        return INSTANCE;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }
}
