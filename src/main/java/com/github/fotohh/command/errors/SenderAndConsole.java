package com.github.fotohh.command.errors;

import org.bukkit.command.CommandSender;

/**
 * The SenderAndConsole class is a custom SenderOptions implementation that allows the command to be executed by any type of CommandSender.
 * This includes both players and the console (server operator).
 *
 * @since 1.0.4
 */
public class SenderAndConsole extends CommandError implements SenderOptions {

    /**
     * Allows the command to be executed by any type of CommandSender (players and the console).
     *
     * @param sender The CommandSender to be validated.
     * @return true to allow the command execution for any sender, false otherwise.
     * @since 1.0.4
     */
    @Override
    public boolean isValid(CommandSender sender) {
        return true;
    }
}
