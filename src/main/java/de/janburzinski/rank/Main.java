package de.janburzinski.rank;

import de.janburzinski.rank.command.CommandHandler;
import de.janburzinski.rank.database.DatabaseConfig;
import de.janburzinski.rank.database.DatabaseHandler;
import de.janburzinski.rank.events.EventInitializer;
import de.janburzinski.rank.listener.ListenerHandler;
import de.janburzinski.rank.logger.LogService;
import de.janburzinski.rank.sign.SignHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // default config speichern, falls nicht existent
        saveDefaultConfig();

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
        CommandHandler commandHandler = new CommandHandler(this);
        commandHandler.initializeCommands();

        // init cache

        // load signs
        SignHandler signHandler = new SignHandler();
        signHandler.loadSigns();

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
