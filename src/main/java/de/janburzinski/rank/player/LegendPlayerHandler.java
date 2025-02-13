package de.janburzinski.rank.player;

import de.janburzinski.rank.database.DatabaseHandler;
import de.janburzinski.rank.exceptions.GetPlayerException;
import de.janburzinski.rank.exceptions.UserUpdateException;
import de.janburzinski.rank.group.LegendGroup;
import de.janburzinski.rank.group.LegendGroupHandler;
import de.janburzinski.rank.logger.LogService;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.UUID;

/**
 * Befasst sich mit der Logik für das Spieler Objekt
 */
public class LegendPlayerHandler {
    private LegendPlayerQueries queries;
    private final UUID uuid;
    private final Player player;

    public LegendPlayerHandler(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
    }

    public LegendPlayerHandler(UUID uuid, Player player) {
        this.player = player;
        this.uuid = uuid;
    }

    public void initializePlayerOnJoin() {

    }

    /**
     * Spieler Felder Updaten (pot. displayName) oder erstellen, falls er nicht existiert
     *
     * @throws UserUpdateException Datenbank Verbindung steht vermutlich nicht
     */
    public void updatePlayer() throws UserUpdateException {
        LogService.debug(player.getName() + " | " + player.getUniqueId());
        try (Connection connection = DatabaseHandler.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO mc_users (uuid, display_name) VALUES (?, ?) " +
                    "ON CONFLICT (uuid) DO UPDATE SET display_name = EXCLUDED.display_name;");

            UUID uuid = player.getUniqueId();
            String displayName = player.getName();

            statement.setObject(1, uuid);
            statement.setString(2, displayName); // display name beim einfügen
            statement.executeUpdate();

            LogService.debug("| USER | [" + displayName + "] - " + uuid + " - wurde erstellt/geupdated");
        } catch (SQLException e) {
            throw new UserUpdateException("Fehler beim Player Updaten: "
                    + e.getMessage());
        }
    }

    public LegendPlayer getPlayer(UUID uuid) throws GetPlayerException {
        try (Connection connection = DatabaseHandler.getInstance().getConnection()) {
            String query = "SELECT u.*, r.rank_dpname, r.prio, r.prefix, r.color " +
                    "FROM mc_users u " +
                    "LEFT JOIN rank r ON u.rank_id = r.rank_id " +
                    "WHERE u.uuid = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, uuid);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String displayName = rs.getString("display_name");

                    String rankDisplayName = rs.getString("rank_dpname");
                    int rankPrio = rs.getInt("prio");
                    String prefix = rs.getString("prefix");
                    int color = rs.getInt("color");

                    LegendGroup rank = new LegendGroup(rankDisplayName, rankPrio, prefix, color);

                    return new LegendPlayer(uuid, displayName, rank);
                }
            }
        } catch (SQLException e) {
            throw new GetPlayerException("Fehler beim bekommen vom Spieler aus der DB: "
                    + e.getMessage());
        }

        return null;
    }

    /**
     * Gucken ob der Spieler einen Rank hat, der noch nicht abgelaufen ist
     * Funktion entfernt auch automatisch den Rank, falls er schon abgelaufen ist
     *
     * @return hat der spieler einen rank, die noch nicht abgelaufen ist
     * @throws SQLException fehler bei irgendwas mit der sql query oder verbindung
     */
    public boolean checkIfHasTempRank() throws SQLException {
        try (Connection connection = DatabaseHandler.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(queries.USER_HAS_TIMED_GROUP);
            statement.setString(1, uuid.toString());

            ResultSet set = statement.executeQuery();
            if (!set.next()) return false;

            String rank = set.getString("rank_id");
            long until = set.getLong("rank_until");
            long currentTimestamp = Instant.now().getEpochSecond();

            if (until <= currentTimestamp) {
                LegendGroupHandler groupHandler = new LegendGroupHandler(uuid);
                groupHandler.resetRankFromUser();

                // rank ist abgelaufen, also hat der spieler jetzt keinen rank mehr, den man überprüfen müsste
                return false;
            }

            // # todo: remove debug
            LogService.debug(uuid + "hat " + rank + " bis " + until);
        }

        // da die sql query nur etwas zurückgibt, wenn rank_until <= jetzt
        // können wir davon ausgehen, dass der spieler kein rank hat der zeitlich begrenzt ist
        return true;
    }
}
