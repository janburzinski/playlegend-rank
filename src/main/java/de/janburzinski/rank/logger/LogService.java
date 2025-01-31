package de.janburzinski.rank.logger;

import org.bukkit.Bukkit;

/**
 * Zugriff auf den Standard Bukkit Logger vereinfachen
 */
public class LogService {
    private static final String prefix = "[Legend-Rank] ";

    public static void info(String message) {
        Bukkit.getLogger().info(prefix + message);
    }

    public static void warning(String message) {
        Bukkit.getLogger().warning(prefix + message);

        // ONLY IN DEVELOPMENT
        Bukkit.shutdown();
    }

    public static void debug(String message) {
        // todo: nicht loggen wenn debug nicht in .env angegeben bzw. aktiviert
        Bukkit.getLogger().info(prefix + "DEBUG - " + message);
    }
}
