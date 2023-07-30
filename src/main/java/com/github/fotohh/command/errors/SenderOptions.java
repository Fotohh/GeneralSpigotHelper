package com.github.fotohh.command.errors;

import org.bukkit.command.CommandSender;

/**
 * The SenderOptions interface allows for defining custom validation rules for command senders.
 * It provides a method to check whether a given CommandSender is valid according to the defined rules.
 *
 * @since 1.0.4
 */
public interface SenderOptions {

    /**
     * Checks whether the given CommandSender is valid according to the defined rules.
     *
     * @param sender The CommandSender to be validated.
     * @return true if the CommandSender is valid, false otherwise.
     * @since 1.0.4
     */
    boolean isValid(CommandSender sender);
}
