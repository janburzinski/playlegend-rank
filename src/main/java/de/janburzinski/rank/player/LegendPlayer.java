package de.janburzinski.rank.player;

import de.janburzinski.rank.database.DatabaseHandler;
import de.janburzinski.rank.group.LegendGroup;
import de.janburzinski.rank.logger.LogService;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Getter
@Setter
public class LegendPlayer {
    public UUID uuid;
    public String displayName;
    public LegendGroup legendRank;

    public LegendPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public LegendPlayer(UUID uuid, String displayName) {
        this.uuid = uuid;
        this.displayName = displayName;
    }

    public LegendPlayer(UUID uuid, String displayName, LegendGroup legendRank) {
        this.uuid = uuid;
        this.displayName = displayName;
        this.legendRank = legendRank;
    }

    // doing a database requests every time is the dumbest thing to do EVER
    // todo: implement caching
    public int convertRankToColor() {
        try (Connection connection = DatabaseHandler.getInstance().getConnection()) {
            String query = "SELECT r.color " +
                    "FROM mc_users u " +
                    "JOIN rank r ON u.rank_id = r.rank_id " +
                    "WHERE u.uuid = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, uuid);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) return rs.getInt("color");
            }
        } catch (SQLException e) {
            LogService.warning("error converting rank to color: " + e.getMessage());
        }
        return 0x000000;
    }
}
