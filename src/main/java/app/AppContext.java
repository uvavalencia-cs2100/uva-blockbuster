package app;

import config.AppConfig;

import customer.CustomerRepository;
import customer.CustomerService;
import movie.MovieRepository;
import movie.MovieService;
import movie.MovieCopyRepository;
import movie.MovieCopyService;

import java.util.logging.Logger;

// Singleton: the one place where the shared services and settings live, so the menus and the
// startup code all talk to the same EntityService instance and read the same configuration.
public class AppContext {
    private static final Logger log = Logger.getLogger(AppContext.class.getName());
    private static final AppContext INSTANCE = new AppContext();

    private AppConfig config = AppConfig.defaults();
    // Created on first use, once the config is final, because creating it loads the entities
    private CustomerService customerService;
    private MovieService movieService;
    private MovieCopyService movieCopyService;

    private AppContext() {}

    public static AppContext getInstance() {
        return INSTANCE;
    }

    // Loads the data at startup, from the data path in the config. Only entities are loaded for
    // now:
    // they load themselves when their service is created. The other entities are not ready yet.
    public void loadData() {
        log.info("Loading data from " + config.getDataPath());
        // the first call creates it, and that loads the entities
        getCustomerService(); 
        getMovieService(); 
        getMovieCopyService();
    }

    public CustomerService getCustomerService() {
        if (customerService == null) {
            customerService = new CustomerService(new CustomerRepository(config.getDataPath()));
        }
        return customerService;
    }

    public MovieService getMovieService() {
        if (movieService == null) {
            movieService = new MovieService(new MovieRepository(config.getDataPath()));
        }
        return movieService;
    }

    public MovieCopyService getMovieCopyService() {
        if (movieCopyService == null) {
            movieCopyService = new MovieCopyService(new MovieCopyRepository(config.getDataPath(), getMovieService()));
        }
        return movieCopyService;
    }

    public AppConfig getConfig() {
        return config;
    }

    // Services are built from the config (e.g. where the data folder is), so call this once at
    // startup, before the first getCustomerService(). Changing it later discards the service,
    // and the next getCustomerService() loads it again from the new location.
    public void setConfig(AppConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("Config cannot be null");
        }
        this.config = config;
        this.customerService = null;
        this.movieService = null;
        this.movieCopyService = null;
    }
}
