package config;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.logging.Logger;

// The app settings, read from a properties file. Every setting is optional: a missing or empty
// file, or a missing or blank property, just leaves the default value.
public class AppConfig {
    private static final Logger log = Logger.getLogger(AppConfig.class.getName());

    public static final String DEFAULT_CONFIG_FILE = "config.properties";

    public static final String DATA_PATH_KEY = "data_path";
    public static final String DEFAULT_DATA_PATH = "data";

    private final String dataPath;

    private AppConfig(String dataPath) {
        this.dataPath = dataPath;
    }

    // A config with every setting at its default value
    public static AppConfig defaults() {
        return new AppConfig(DEFAULT_DATA_PATH);
    }

    // The config file path can be given as the first argument; otherwise config.properties is used
    public static AppConfig load(String[] args) {
        return load(Path.of(args.length > 0 ? args[0] : DEFAULT_CONFIG_FILE));
    }

    public static AppConfig load(Path file) {
        Properties properties = new Properties();
        if (Files.exists(file)) {
            try (Reader reader = Files.newBufferedReader(file)) {
                properties.load(reader);
                log.info("Read config from " + file);
            } catch (IOException | IllegalArgumentException e) {
                log.warning(
                        "Could not read config " + file + ", using defaults: " + e.getMessage());
            }
        } else {
            log.info("No config file at " + file + ", using defaults");
        }

        String dataPath = properties.getProperty(DATA_PATH_KEY, DEFAULT_DATA_PATH).trim();
        if (dataPath.isEmpty()) {
            dataPath = DEFAULT_DATA_PATH;
        }
        log.info(DATA_PATH_KEY + " = " + dataPath);
        return new AppConfig(dataPath);
    }

    // Folder holding the CSV files
    public String getDataPath() {
        return dataPath;
    }
}
