package de.janburzinski.rank.database.helper;

import de.janburzinski.rank.database.DatabaseHandler;
import de.janburzinski.rank.logger.LogService;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelperHandler implements DatabaseHelper {
    private final DatabaseHandler databaseHandler;

    public DatabaseHelperHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    @Override
    public void setupTables() throws SQLException {
        // müssen die ganzen tabellen und default inputs überhaupt erstellt werden?
        boolean s = shouldSetup();
        LogService.info("s:" + s);
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

                connection.commit();
                LogService.info("Tabellen Setup fertig...");
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
        String query = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE tablename = 'mc_users')";
        try (Connection connection = databaseHandler.getConnection();
             Statement statement = connection.createStatement();
             var resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                boolean exists = resultSet.getBoolean(1);
                //LogService.info("Die Tabelle 'user' existiert: " + exists);
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
        
    }
}
