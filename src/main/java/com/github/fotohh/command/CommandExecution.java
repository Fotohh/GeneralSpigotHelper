package com.github.fotohh.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The CommandExecution class represents the context of a command execution in a Bukkit/Spigot plugin.
 * It contains information about the sender, player (if applicable), command arguments, and usage message.
 *
 * @since 1.0.4
 */
public class CommandExecution {

    private final CommandSender sender;
    private final String usageMessage;
    private final Player player;
    private final String[] args;

    /**
     * Constructs a new CommandExecution instance.
     *
     * @param sender       The CommandSender executing the command.
     * @param player       The Player executing the command (if applicable, otherwise null).
     * @param args         The command arguments provided by the sender.
     * @param usageMessage The usage message of the command to be displayed for incorrect usage.
     * @since 1.0.4
     */
    public CommandExecution(CommandSender sender, Player player, String[] args, String usageMessage) {
        this.sender = sender;
        this.player = player;
        this.args = args;
        this.usageMessage = usageMessage;
    }

    /**
     * Sends the usage message to the command sender.
     * This message is typically displayed when the command is used with incorrect syntax.
     *
     * @since 1.0.4
     */
    public void sendIncorrectUsageMessage() {
        sender.sendMessage(usageMessage);
    }

    /**
     * Gets the CommandSender executing the command.
     *
     * @return The CommandSender instance.
     * @since 1.0.4
     */
    public CommandSender getSender() {
        return sender;
    }

    /**
     * Gets the Player executing the command (if applicable).
     *
     * @return The Player instance, or null if the sender is not a player.
     * @since 1.0.4
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the command arguments provided by the sender.
     *
     * @return An array of command arguments.
     * @since 1.0.4
     */
    public String[] getArgs() {
        return args;
    }
}
