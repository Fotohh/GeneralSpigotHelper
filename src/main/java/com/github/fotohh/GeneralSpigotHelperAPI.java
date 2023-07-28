package com.github.fotohh;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

/**
 * GeneralSpigotHelperAPI provides utility methods to register commands and event listeners in a Spigot-based Minecraft plugin.
 */
public class GeneralSpigotHelperAPI {

    /**
     * Represents a command registration containing the CommandExecutor and the command name.
     */
    record CommandRegister(CommandExecutor commandExecutor, String commandName) {}

    private final JavaPlugin plugin;

    /**
     * Constructs a new instance of GeneralSpigotHelperAPI with the specified JavaPlugin instance.
     *
     * @param plugin The JavaPlugin instance of the plugin using this API.
     */
    public GeneralSpigotHelperAPI(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Registers multiple commands with their respective CommandExecutors.
     *
     * @param register An array of CommandRegister objects representing the commands to register.
     */
    public void registerCommands(CommandRegister... register) {
        for (CommandRegister cmd : register) {
            plugin.getCommand(cmd.commandName).setExecutor(cmd.commandExecutor);
        }
    }

    /**
     * Registers multiple event listeners with the plugin's event manager.
     *
     * @param listeners An array of Listener objects representing the event listeners to register.
     */
    public void registerListeners(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> plugin.getServer().getPluginManager().registerEvents(listener, plugin));
    }

}
