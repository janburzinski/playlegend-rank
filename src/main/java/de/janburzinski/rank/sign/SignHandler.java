package de.janburzinski.rank.sign;

import de.janburzinski.rank.database.DatabaseHandler;
import de.janburzinski.rank.logger.LogService;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SignHandler {
    public ArrayList<StoredSign> storedSigns;

    public void loadSigns() {
        // since this method is only being called on startup
        this.storedSigns = new ArrayList<>();

        try (Connection connection = DatabaseHandler.getInstance().getConnection()) {
            String query = "SELECT * FROM signs";
            PreparedStatement statement = connection.prepareStatement(query);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    String worldName = rs.getString("world");
                    int x = rs.getInt("x");
                    int y = rs.getInt("y");
                    int z = rs.getInt("z");

                    World world = Bukkit.getWorld(worldName);
                    if (world == null) {
                        LogService.warning("Fehler beim bekommen der Welt " + worldName);
                        continue;
                    }

                    StoredSign storedSign = new StoredSign(world, x, y, z);
                    storedSigns.add(storedSign);
                }
            }
        } catch (SQLException e) {
            LogService.warning("fehler beim laden von den schildern: " + e.getMessage());
        }

        LogService.info("All Signs were loaded");
    }

    public void displaySigns() {
        if (storedSigns.isEmpty()) return;
    }

    public void updateSign(Player player, Location location) {
    }

    public void createSign(Location location) {
        try (Connection connection = DatabaseHandler.getInstance().getConnection()) {
            String worldName = location.getWorld().getName();
            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();

            String query = "INSERT INTO signs(world,x,y,z) VALUES(?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, worldName);
            statement.setInt(2, x);
            statement.setInt(3, y);
            statement.setInt(4, z);

            statement.executeQuery();
        } catch (SQLException e) {
            LogService.warning("fehler beim erstellen von einem schild: " + e.getMessage());
        }
    }
}
