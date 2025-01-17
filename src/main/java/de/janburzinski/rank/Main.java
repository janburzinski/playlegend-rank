package de.janburzinski.rank;

import de.janburzinski.rank.logger.LogService;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this,this);
        LogService.info("Rank Plugin enabled!");
    }
}
