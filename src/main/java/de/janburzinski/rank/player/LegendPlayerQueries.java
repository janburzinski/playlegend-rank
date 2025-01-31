package de.janburzinski.rank.player;

public class LegendPlayerQueries {
    // erstelle den user, falls er allerdings bereits existiert wird der eintrag nur geupdatet
    // todo: only update display_name if changed
    // public final String UPDATE_USER_QUERY =
    //         "INSERT INTO mc_users (uuid, display_name) VALUES (?, ?) " + // user existiert nicht
    //                 "ON DUPLICATE KEY UPDATE display_name = VALUES(display_name);"; // user nur updaten, da er schon existiert
    public final String UPDATE_USER_QUERY =
            "INSERT INTO mc_users (uuid, display_name) VALUES (?, ?) " +
                    "ON CONFLICT (uuid) DO UPDATE SET display_name = EXCLUDED.display_name;";


    // das feld rank_until bekommen, um zu überprüfen ob der spieler aus einer gruppe raus muss oder ob er noch einen rank hat
    // wird auch genutzt um zu timen, wann der spieler aus dieser gruppe rausgenommen werden muss
    public final String USER_HAS_TIMED_GROUP = "SELECT rank_until FROM mc_users WHERE uuid = ? AND rank_until != -1;";
    // + "AND rank_until <= EXTRACT(EPOCH FROM NOW())"
}
