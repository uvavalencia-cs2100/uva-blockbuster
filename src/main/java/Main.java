import app.AppContext;

import command.MenuOptions;

import config.AppConfig;

import ui.LogBuffer;
import ui.Screen;

import java.util.logging.Logger;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        LogBuffer.install();
        log.info("Welcome to Blockbuster!");
        AppContext.getInstance().setConfig(AppConfig.load(args));
        AppContext.getInstance().loadData();
        Screen.start();
        new MenuOptions().runMenu();
    }
}
