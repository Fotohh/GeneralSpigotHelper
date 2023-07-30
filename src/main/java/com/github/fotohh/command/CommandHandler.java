package com.github.fotohh.command;

import com.github.fotohh.command.errors.CustomCommandError;
import com.github.fotohh.command.errors.NotEnoughArguments;
import com.github.fotohh.command.errors.SenderOptions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * The CommandHandler class is a custom command executor for Bukkit/Spigot plugins.
 * It provides an easy way to handle and execute custom commands with various options.
 *
 * @since 1.0.4
 */
public class CommandHandler implements CommandExecutor {

    private final String name;
    private final JavaPlugin plugin;
    private final NotEnoughArguments argumentOptions;
    private final SenderOptions senderOptions;

    private boolean permissionEnabled = false;

    private final List<CustomCommandError> customErrors = new ArrayList<>();

    private String usage;

    private String permission;
    private String permissionMessage;
    private String senderMessage;

    private Consumer<CommandExecution> consumer;

    /**
     * Constructs a new CommandHandler instance for a specific command name.
     *
     * @param commandName     The name of the command to handle.
     * @param plugin          The JavaPlugin instance.
     * @param senderOptions   The SenderOptions to validate the command sender.
     * @param argumentOptions The NotEnoughArguments options to validate the command arguments.
     * @since 1.0.4
     */
    public CommandHandler(String commandName, JavaPlugin plugin, SenderOptions senderOptions, NotEnoughArguments argumentOptions) {
        this.name = commandName;
        this.plugin = plugin;
        this.senderOptions = senderOptions;
        this.argumentOptions = argumentOptions;
        plugin.getCommand(commandName).setExecutor(this);
    }

    /**
     * Registers a custom error handler for this command.
     *
     * @param customError The CustomCommandError instance to handle specific errors.
     * @since 1.0.4
     */
    public void registerCustomError(CustomCommandError customError) {
        customErrors.add(customError);
    }

    /**
     * Sets the message to be sent to the sender when the command is executed.
     *
     * @param message The message to be sent to the sender.
     * @return The current CommandHandler instance.
     * @since 1.0.4
     */
    public CommandHandler setSenderMessage(String message) {
        this.senderMessage = message;
        return this;
    }

    /**
     * Sets the message to be sent to the sender when the command has incorrect syntax.
     *
     * @param message The message to be sent to the sender.
     * @return The current CommandHandler instance.
     * @since 1.0.4
     */
    public CommandHandler setIncorrectSenderMessage(String message) {
        this.senderMessage = message;
        return this;
    }

    /**
     * Sets the usage message for the command.
     *
     * @param message The usage message for the command.
     * @return The current CommandHandler instance.
     * @since 1.0.4
     */
    public CommandHandler setUsageMessage(String message) {
        this.usage = message;
        return this;
    }

    /**
     * Sets the message to be sent to the sender when they have no permission to execute the command.
     *
     * @param message    The message to be sent to the sender.
     * @param permission The permission required to execute the command.
     * @return The current CommandHandler instance.
     * @since 1.0.4
     */
    public CommandHandler setNoPermissionMessage(String message, String permission) {
        this.permissionMessage = message;
        this.permission = permission;
        permissionEnabled = true;
        return this;
    }

    /**
     * Sets the consumer function to handle command execution.
     *
     * @param consumer The consumer function that accepts a CommandExecution instance.
     * @since 1.0.4
     */
    public void onExecute(Consumer<CommandExecution> consumer) {
        this.consumer = consumer;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        CommandArgument cmdArgs = new CommandArgument(args);

        if (permissionEnabled && sender instanceof Player) {
            if (!sender.hasPermission(permission)) {
                sender.sendMessage(permissionMessage);
                return true;
            }
        }

        if (senderOptions != null) {
            if (!senderOptions.isValid(sender)) {
                sender.sendMessage(senderMessage);
                return true;
            }
        }

        if (argumentOptions != null && !cmdArgs.hasEnoughArguments(argumentOptions.getArgumentLength())) {
            argumentOptions.sendMessage(sender);
            return true;
        }

        if (!customErrors.isEmpty()) {
            for (CustomCommandError customError : customErrors) {
                if (customError.handle(sender, command, label, args)) return true;
            }
        }

        if (consumer != null) {
            consumer.accept(new CommandExecution(sender, (Player) sender, args, usage));
        }
        return true;
    }
}
