package de.janburzinski.rank.events;

import de.janburzinski.rank.Main;

public class EventInitializer {
    private final Main plugin;

    public EventInitializer(Main plugin) {
        this.plugin = plugin;
    }

    public void initializeEvents() {
        //  plugin.getServer().getPluginManager().registerEvents(new RankChangeEvent(), plugin);
    }
}
