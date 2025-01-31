package de.janburzinski.rank;

import de.janburzinski.rank.command.CommandHandler;
import de.janburzinski.rank.database.DatabaseConfig;
import de.janburzinski.rank.database.DatabaseHandler;
import de.janburzinski.rank.events.EventInitializer;
import de.janburzinski.rank.listener.ListenerHandler;
import de.janburzinski.rank.logger.LogService;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // default config speichern, falls nicht existent
        saveDefaultConfig();

        // ONLY ENABLE IN DEV ENV
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            getLogger().severe("Uncaught exception detected: " + throwable.getMessage());
            throwable.printStackTrace();
            Bukkit.shutdown(); // Stop the server on error
        });

        // config file laden "config.yml"
        FileConfiguration config = getConfig();

        // datenbank verbindung aufbauen
        String dbConfigPrefix = "database.";
        String host = config.getString(dbConfigPrefix + ".host", "localhost");
        String database = config.getString(dbConfigPrefix + ".database", "plrank");
        String username = config.getString(dbConfigPrefix + ".username", "root");
        String password = config.getString(dbConfigPrefix + ".password", "bestespasswort");
        int port = config.getInt(dbConfigPrefix + ".port", 5432);
        DatabaseConfig dbConfig = new DatabaseConfig(host, database, username, password, port);
        DatabaseHandler.initialize(dbConfig);

        // register custom events
        EventInitializer eventInitializer = new EventInitializer(this);
        eventInitializer.initializeEvents();

        // register listeners
        ListenerHandler listenerHandler = new ListenerHandler(this);
        listenerHandler.registerListeners();

        // register commands
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.initializeCommands();


        LogService.info("Rank Plugin enabled!");
    }

    @Override
    public void onDisable() {
        if (DatabaseHandler.getInstance() != null) {
            DatabaseHandler.getInstance().disconnect();
        }
        LogService.info("Rank Plugin disabled!");
    }
}
