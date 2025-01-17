package de.janburzinski.rank.database;

import com.zaxxer.hikari.HikariConfig;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

@AllArgsConstructor
public abstract class Database {
    public String host, database, username, password;
    public final int port;

    /**
     * Verbindung zur PostgreSQL Datenbank herstellen
     */
    public abstract void connect();

    /**
     * Datenbank Verbindung schließen
     */
    public abstract void disconnect();

    /**
     * Connection Instanz aus dem Pool bekommen
     */
    public abstract Connection getConnection() throws SQLException;

    public abstract HikariConfig getConfig();

}
