package de.janburzinski.rank.group;

import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Befasst sich mit all der Logik für die Ranks
 */
public class LegendGroupHandler {
    public UUID uuid;
    public Player player;

    public LegendGroupHandler(UUID uuid) {
        this.uuid = uuid;
    }

    public LegendGroupHandler(Player player) {
        this.player = player;
    }

    public void setRank() {
    }

    public boolean isValidRank(String rank) {
        return false;
    }

    public void resetRankFromUser() {
    }

    public String getRank() {
        return "";
    }
}
