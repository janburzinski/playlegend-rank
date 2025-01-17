package de.janburzinski.rank.logger;

import org.bukkit.Bukkit;

/**
 * Zugriff auf den Standard Bukkit Logger vereinfachen
 */
public class LogService {
    private static String prefix = "[Legend-Group] ";

    public static void info(String message) {
        Bukkit.getLogger().info(prefix + message);
    }

    public static void warning(String message) {
        Bukkit.getLogger().warning(prefix + message);
    }

    public static void debug(String message) {
        Bukkit.getLogger().info(prefix + "DEBUG - " + message);
    }
}
