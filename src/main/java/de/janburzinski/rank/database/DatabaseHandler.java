package de.janburzinski.rank.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.janburzinski.rank.database.helper.DatabaseHelperHandler;
import de.janburzinski.rank.database.helper.TableSetupQueries;
import de.janburzinski.rank.logger.LogService;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Ich verwende PostgreSQL als Datenbank für dieses Projekt, aufgrund der besseren Concurrency und Performance.
 * <p>
 * MySQL wäre allerdings auch eine sehr gute alternative, da MySQL eine schnellere Lesegeschwindigkeit
 * bei einfachen Operationen hat.
 */
@Getter
public class DatabaseHandler extends Database {

    // Connection Variable, um die Verbindung zur Datenbank zu handeln
    public HikariDataSource dataSource;
    @Getter
    public HikariConfig config;

    public DatabaseHandler(String host, String database, String username, String password, int port) {
        super(host, database, username, password, port);
    }

    @Override
    public void connect() {
        // setup connection
        config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://" + host + ":" + port + "/" + database);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setMaxLifetime(1800000); // maximale lebenszeit einer verbindung
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        this.dataSource = new HikariDataSource(config);
        if (dataSource.isRunning())
            LogService.info("Die Verbindung zur Datenbank wurde erfolgreich hergestellt!");

        // tabellen erstellen und default inputs erstellen, falls nicht vorhanden
        DatabaseHelperHandler databaseHelper = new DatabaseHelperHandler(this);
        try {
            databaseHelper.setupTables();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disconnect() {
        if (!dataSource.isClosed()) {
            dataSource.close();
            LogService.info("Die Verbindung zur Datenbank wurde erfolgreich geschlossen!");
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
