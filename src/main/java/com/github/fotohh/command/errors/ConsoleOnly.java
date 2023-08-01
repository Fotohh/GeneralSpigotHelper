package com.github.fotohh.command.errors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The ConsoleOnly class is a custom SenderOptions implementation that allows the command to be executed only by the console (server operator).
 * If the command sender is a player or any other non-console entity, the validation will fail.
 *
 * @since 1.0.4
 */
public class ConsoleOnly implements SenderOptions {

    /**
     * Checks whether the given CommandSender is the console, validating that the command can only be executed by the console.
     *
     * @param sender The CommandSender to be validated.
     * @return true if the CommandSender is the console, false otherwise.
     * @since 1.0.4
     */
    @Override
    public boolean isValid(CommandSender sender) {
        return !(sender instanceof Player);
    }
}
