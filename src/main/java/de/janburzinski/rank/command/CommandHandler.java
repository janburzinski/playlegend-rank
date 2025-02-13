package de.janburzinski.rank.command;

import de.janburzinski.rank.Main;
import de.janburzinski.rank.command.commands.PlayerCommand;

public class CommandHandler {
    private final Main plugin;

    public CommandHandler(Main main) {
        this.plugin = main;
    }

    public void initializeCommands() {
        plugin.getCommand("player").setExecutor(new PlayerCommand());
    }
}
