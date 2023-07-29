package com.github.fotohh;

import org.bukkit.command.CommandExecutor;

/**
 * Represents a command registration containing the CommandExecutor and the command name.
 *
 */
public class CommandRegister {

    private final CommandExecutor commandExecutor;

    private final String commandName;

    /**
     * Constructs a new CommandRegister with the specified CommandExecutor and command name.
     * @param commandExecutor The CommandExecutor to set for the command.
     * @param commandName     The name of the command.
     */
    public CommandRegister(CommandExecutor commandExecutor, String commandName) {
        this.commandExecutor = commandExecutor;
        this.commandName = commandName;
    }

    /**
     * Gets the CommandExecutor associated with the command.
     * @return The CommandExecutor.
     */
    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }

    /**
     * Gets the name of the command.
     *
     * @return The command name.
     */
    public String getCommandName() {
        return commandName;
    }

}