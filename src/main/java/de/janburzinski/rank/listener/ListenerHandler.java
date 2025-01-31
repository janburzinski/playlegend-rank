package de.janburzinski.rank.listener;

import de.janburzinski.rank.Main;
import de.janburzinski.rank.logger.LogService;
import org.bukkit.Bukkit;

public class ListenerHandler {
    private final Main plugin;

    public ListenerHandler(Main plugin) {
        this.plugin = plugin;
    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(plugin), plugin);

        Bukkit.getPluginManager().registerEvents(new RankChangeListener(plugin), plugin);

        LogService.debug("Alle Listener wurden registriert");
    }
}
