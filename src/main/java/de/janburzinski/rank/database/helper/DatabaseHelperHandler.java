package de.janburzinski.rank.database.helper;

import de.janburzinski.rank.database.DatabaseHandler;
import de.janburzinski.rank.logger.LogService;

import java.sql.*;

public class DatabaseHelperHandler implements DatabaseHelper {
    private final DatabaseHandler databaseHandler;
    private final boolean DEBUG = true;

    public DatabaseHelperHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    @Override
    public void setupTables() throws SQLException {
        // debug = true => delete tables on startup ( :o )
        if (DEBUG) resetTabels();

        // müssen die ganzen tabellen und default inputs überhaupt erstellt werden?
        boolean s = shouldSetup();
        LogService.info("should setup tables?: " + s);
        if (!s) return;

        LogService.info("Tabellen Setup wird eingeleitet...");
        // falls ja, erstellen wir sie kurz
        try {
            Connection connection = databaseHandler.getConnection();
            connection.setAutoCommit(false);

            try (Statement statement = connection.createStatement()) {
                // tabellen erstellen
                statement.execute(TableSetupQueries.CREATE_RANK_TABLE);
                statement.execute(TableSetupQueries.CREATE_USER_TABLE);
                statement.execute(TableSetupQueries.CREATE_RANK_PERM_TABLE);
                statement.execute(TableSetupQueries.CREATE_SIGNS_TABLE);

                // default inputs erstellen
                statement.execute(TableSetupQueries.CREATE_DEFAULT_RANK);
                statement.execute(TableSetupQueries.CREATE_DEFAULT_RANK_PERM);
                LogService.debug("table setup leutis");

                connection.commit();
                LogService.debug("Tabellen Setup fertig...");
            } catch (SQLException e) {
                connection.rollback();
                LogService.warning("Fehler beim erstellen von den Default Inputs und Tables: " + e.getMessage());
                throw e;
            } finally {
                // wert wieder auf den eigentlichen wert zurücksetzen
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            LogService.warning("Fehler bei der Datenbankverbindung: " + e.getMessage());
        }
    }

    @Override
    public boolean shouldSetup() {
        if (DEBUG) return true;

        String query = "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = 'public'" +
                " AND table_name = 'mc_users')";
        try (Connection connection = databaseHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                boolean exists = resultSet.getBoolean(1);
                LogService.debug("Die Tabelle 'user' existiert: " + exists);
                return !exists;
            }
        } catch (SQLException e) {
            LogService.warning("Fehler bei der Überprüfung, ob der Setup durchgeführt werden sollte: " + e.getMessage());
        }

        // fehler = false
        return false;
    }

    @Override
    public void resetTabels() {
        LogService.warning("DROPPED ALL TABLES SINCE DEBUG IS ENABLED");
        try (Connection connection = databaseHandler.getConnection()) {
            String query = "DROP TABLE IF EXISTS rank, rank_perm, mc_users, signs;";
            connection.prepareStatement(query).executeUpdate();
            LogService.warning("DEBUG ENABLED: DROPPED ALL TABLES");
        } catch (SQLException e) {
            LogService.warning("Fehler beim droppen aller Tabellen: " + e.getMessage());
        }
    }
}
