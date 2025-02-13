package de.janburzinski.rank.database.helper;

public class TableSetupQueries {
    /**
     * Rank Table
     */
    public static final String CREATE_RANK_TABLE = "CREATE TABLE IF NOT EXISTS rank (\n" +
            "    rank_id SERIAL PRIMARY KEY,\n" +
            "    rank_dpname VARCHAR(255) NOT NULL UNIQUE,\n" +
            "    prio INT NOT NULL,\n" +
            "    prefix VARCHAR(255) NOT NULL,\n" +
            "    color VARCHAR(50) NOT NULL\n" +
            ")";

    public static final String CREATE_RANK_PERM_TABLE = "CREATE TABLE IF NOT EXISTS rank_perm (\n" +
            "    rank_id INT NOT NULL,\n" +
            "    permission VARCHAR(255) NOT NULL,\n" +
            "    has_op BOOLEAN NOT NULL DEFAULT FALSE,\n" +
            "    PRIMARY KEY (rank_id, permission),\n" +
            "    FOREIGN KEY (rank_id) REFERENCES rank(rank_id) ON DELETE CASCADE\n" +
            ");";

    public static final String CREATE_DEFAULT_RANK =
            "INSERT INTO rank (rank_dpname, prio, prefix, color) VALUES ('default', 0, 'PLAYER', '7') " +
                    "ON CONFLICT (rank_dpname) DO NOTHING RETURNING rank_id";

    public static final String CREATE_DEFAULT_RANK_PERM =
            "INSERT INTO rank_perm (rank_id, permission, has_op) VALUES ((SELECT rank_id FROM rank WHERE rank_dpname = 'default'), '*', FALSE)";

    /**
     * User Table
     */
    public static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS mc_users (\n" +
            "    uuid UUID PRIMARY KEY,\n" +
            "    display_name VARCHAR(255) NOT NULL,\n" +
            "    rank_id INT NOT NULL DEFAULT 1,\n" +
            "    rank_until BIGINT NOT NULL DEFAULT -1,\n" +
            "    language VARCHAR(10) NOT NULL DEFAULT 'de',\n" +
            "    FOREIGN KEY (rank_id) REFERENCES rank(rank_id) ON DELETE SET DEFAULT\n" +
            ");";

    /**
     * Signs Table
     */
    public static final String CREATE_SIGNS_TABLE = "CREATE TABLE IF NOT EXISTS signs (\n" +
            "    id SERIAL PRIMARY KEY,\n" +
            "    world VARCHAR(255) NOT NULL,\n" +
            "    x INT NOT NULL\n" +
            "    y INT NOT NULL\n" +
            "    z INT NOT NULL\n" +
            ");";
}