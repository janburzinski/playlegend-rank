package de.janburzinski.rank.command.commands;

import de.janburzinski.rank.exceptions.GetPlayerException;
import de.janburzinski.rank.player.LegendPlayer;
import de.janburzinski.rank.player.LegendPlayerHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            LegendPlayerHandler playerHandler = new LegendPlayerHandler(player);
            try {
                LegendPlayer p = playerHandler.getPlayer(player.getUniqueId());
                player.sendMessage("&a---------- player ------------");
                player.sendMessage("rank: " + p.getLegendRank().rank);
                player.sendMessage("r_color: " + String.valueOf(p.getLegendRank().rankColor));
                player.sendMessage("r_prefix: " + p.getLegendRank().rankPrefix);
                player.sendMessage("prio: " + String.valueOf(p.getLegendRank().rankPrio));
                player.sendMessage("---------- player ende haha ------------");
            } catch (GetPlayerException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
}
