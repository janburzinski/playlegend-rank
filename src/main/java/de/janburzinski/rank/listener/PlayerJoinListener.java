package de.janburzinski.rank.listener;

import de.janburzinski.rank.Main;
import de.janburzinski.rank.logger.LogService;
import de.janburzinski.rank.player.LegendPlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final Main plugin;

    public PlayerJoinListener(Main plugin) {
        this.plugin = plugin;
    }

    /**
     * [X] initialize player
     * [] rank
     * [] setup rank timer
     * [] potentially update display name
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        //@NotNull
        Player player = event.getPlayer();
        event.joinMessage(null);

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            LogService.info("player: " + player + " | " + player.getUniqueId());

            LegendPlayerHandler legendPlayer = new LegendPlayerHandler(player.getUniqueId(), player);
            try {
                legendPlayer.updatePlayer();
            } catch (Exception e) {
                LogService.warning("Fehler beim Spieler updaten: " + e.getMessage());
            }
        });
    }
}
