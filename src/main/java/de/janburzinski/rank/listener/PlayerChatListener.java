package de.janburzinski.rank.listener;

import de.janburzinski.rank.exceptions.GetPlayerException;
import de.janburzinski.rank.player.LegendPlayer;
import de.janburzinski.rank.player.LegendPlayerHandler;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) throws GetPlayerException {
        Player player = event.getPlayer();
        Component playerName = player.displayName();
        String rawText = LegacyComponentSerializer.legacySection().serialize(event.message());

        LegendPlayerHandler playerHandler = new LegendPlayerHandler(player);
        LegendPlayer p = playerHandler.getPlayer(player.getUniqueId());
        int color = p.getLegendRank().getRankColor();

        Component newMessage = Component.text(rawText);
        Component customChat = Component.text().append(Component.text("[" + p.getLegendRank().getRankPrefix() + "] ").color(TextColor.color(color)))
                .append(playerName)
                .append(Component.text(" >> ").color(TextColor.color(0xAAAAAA)))
                .append(newMessage)
                .build();

        event.renderer((source, sourceDisplayName, message, viewer) -> customChat);
    }
}
