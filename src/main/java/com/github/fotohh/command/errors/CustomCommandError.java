package com.github.fotohh.command.errors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * The CustomCommandError interface allows for defining custom error handlers for commands in a Bukkit/Spigot plugin.
 * It provides a method to handle specific command errors, giving full control over the error handling process.
 *
 * @since 1.0.4
 */
public interface CustomCommandError {

    /**
     * Handles a specific error related to command execution.
     *
     * @param sender  The CommandSender that executed the command.
     * @param command The executed command.
     * @param label   The command's label (i.e., its alias).
     * @param args    The arguments provided with the command.
     * @return true if the error was handled and further command processing should stop, false otherwise.
     * @since 1.0.4
     */
    boolean handle(CommandSender sender, Command command, String label, String[] args);
}
