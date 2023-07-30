package com.github.fotohh.command.errors;

import org.bukkit.command.CommandSender;

/**
 * The CommandError class is an abstract base class for custom command error handling in a Bukkit/Spigot plugin.
 * It provides methods to set an error message and send that message to a command sender.
 *
 * @since 1.0.4
 */
public abstract class CommandError {

    private String message;

    /**
     * Sets the error message to be sent to the command sender.
     *
     * @param message The error message to be set.
     * @since 1.0.4
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Sends the error message to the specified command sender.
     *
     * @param sender The CommandSender to whom the error message will be sent.
     * @since 1.0.4
     */
    public void sendMessage(CommandSender sender) {
        sender.sendMessage(message);
    }
}
