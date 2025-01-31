package de.janburzinski.rank.listener;

import de.janburzinski.rank.Main;
import de.janburzinski.rank.events.RankChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RankChangeListener implements Listener {
    private final Main plugin;

    public RankChangeListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onRankChange(RankChangeEvent event) {
        
    }
}
