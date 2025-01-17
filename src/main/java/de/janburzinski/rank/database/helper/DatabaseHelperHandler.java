package de.janburzinski.rank.database.helper;

import de.janburzinski.rank.database.DatabaseHandler;
import de.janburzinski.rank.logger.LogService;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelperHandler implements  DatabaseHelper {
    private final DatabaseHandler databaseHandler;

    public DatabaseHelperHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    @Override
    public void setupTables() throws SQLException {
        try (Connection connection = databaseHandler.getConnection()) {
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
                } catch (SQLException e) {
                    connection.rollback();
                    e.printStackTrace();
                    throw e;
                }
        } catch (SQLException e) {
            LogService.warning("Fehler bei der Datenbankverbindung: " + e.getMessage());
        }
    }
}
