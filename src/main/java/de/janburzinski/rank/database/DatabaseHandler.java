package de.janburzinski.rank.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.janburzinski.rank.database.helper.DatabaseHelperHandler;
import de.janburzinski.rank.logger.LogService;
import lombok.Getter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Ich verwende PostgreSQL als Datenbank für dieses Projekt, aufgrund der besseren Concurrency und Performance.
 * <p>
 * MySQL wäre allerdings auch eine sehr gute alternative, da MySQL eine schnellere Lesegeschwindigkeit
 * bei einfachen Operationen hat.
 */
@Getter
public class DatabaseHandler extends Database {

    private static DatabaseHandler instance;

    // Connection Variable, um die Verbindung zur Datenbank zu handeln
    public HikariDataSource dataSource;
    @Getter
    public HikariConfig hikariConfig;

    private final DatabaseConfig config;

    private DatabaseHandler(DatabaseConfig config) {
        super(config);
        this.config = config;
    }

    public static DatabaseHandler getInstance() {
        if (instance == null) throw new IllegalStateException("Datenbank Verbindung ist nicht verfügbar!");
        return instance;
    }

    public static synchronized DatabaseHandler initialize(DatabaseConfig config) {
        if (instance == null) {
            instance = new DatabaseHandler(config);
            instance.connect();
        }
        return instance;
    }

    @Override
    public void connect() {
        // setup connection
        hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(org.postgresql.Driver.class.getName());
        hikariConfig.setJdbcUrl("jdbc:postgresql://" + config.host() + ":" + config.port() + "/" + config.database());
        hikariConfig.setUsername(config.username());
        hikariConfig.setPassword(config.password());
        hikariConfig.setMaximumPoolSize(50);
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setMaxLifetime(55000);
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        this.dataSource = new HikariDataSource(hikariConfig);
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
        LogService.debug("db conn is null | is not closed?: " + Objects.isNull(dataSource.getConnection()) + " | " + !dataSource.getConnection().isClosed());
        return dataSource.getConnection();
    }

    @Override
    public DatabaseConfig getConfig() {
        return config;
    }
}
