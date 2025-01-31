package de.janburzinski.rank.database.helper;

import java.sql.SQLException;

public interface DatabaseHelper {
    // erstellt die standard tabellen und die dazugehörigen default inputs (für den ersten setup gedacht vor allem)
    void setupTables() throws SQLException;

    // guckt, ob die USER tabelle schon existiert
    // falls nicht, dann sollte man alle erstellen sonst nicht
    //
    // wir gucken nur ob die user tabelle existiert, da ich dann einfach davon ausgehe, dass
    // der rest auch nicht existiert
    boolean shouldSetup();

    /**
     * NUR IM DEVELOPMENT BENUTZEN
     * Warnung: Löscht alle Tabellen!
     */
    void resetTabels();
}
