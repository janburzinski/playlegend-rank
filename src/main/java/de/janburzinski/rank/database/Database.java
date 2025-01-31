package de.janburzinski.rank.database;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
public abstract class Database {
    @Getter
    private final DatabaseConfig dbConfig;

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

    public abstract DatabaseConfig getConfig();

}
