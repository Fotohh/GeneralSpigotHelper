package com.github.fotohh.command.errors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The SenderOnly class is a custom SenderOptions implementation that allows the command to be executed only by players.
 * If the command sender is not a player (e.g., the console or other non-player entities), the validation will fail.
 *
 * @since 1.0.4
 */
public class SenderOnly implements SenderOptions {

    /**
     * Checks whether the given CommandSender is a player, validating that the command can only be executed by players.
     *
     * @param sender The CommandSender to be validated.
     * @return true if the CommandSender is a player, false otherwise.
     * @since 1.0.4
     */
    @Override
    public boolean isValid(CommandSender sender) {
        return sender instanceof Player;
    }
}
