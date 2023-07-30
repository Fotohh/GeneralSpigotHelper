package com.github.fotohh;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

/**
 * GeneralSpigotHelperAPI provides utility methods to register commands and event listeners in a Spigot-based Minecraft plugin.
 */
public class GeneralSpigotHelperAPI {

    private final DependencyParser parser;

    private final PermissionManager permissionManager;

    private final JavaPlugin plugin;

    /**
     * Constructs a new instance of GeneralSpigotHelperAPI with the specified JavaPlugin instance.
     * Suggest calling this
     *
     * @param plugin The JavaPlugin instance of the plugin using this API.
     */
    public GeneralSpigotHelperAPI(JavaPlugin plugin) {
        this.plugin = plugin;
        this.parser = new DependencyParser(plugin);
        this.permissionManager = new PermissionManager(plugin);
    }

    /**
     * Get the dependency parser instance associated with this GeneralSpigotHelperAPI.
     *
     * @return The DependencyParser instance.
     */
    public DependencyParser getParser() {
        return parser;
    }

    /**
     * Get the permission manager instance associated with this GeneralSpigotHelperAPI.
     *
     * @return The DependencyParser instance.
     */
    public PermissionManager getPermissionManager() {
        return permissionManager;
    }

    /**
     * Creates a new CommandRegister object for bundling a CommandExecutor with a command name.
     *
     * @param commandExecutor The CommandExecutor for the command.
     * @param commandName     The name of the command.
     * @return A CommandRegister object representing the command registration.
     */
    public CommandRegister bundler(CommandExecutor commandExecutor, String commandName){
        return new CommandRegister(commandExecutor, commandName);
    }

    /**
     * Returns the instance of the plugin.
     * @return java plugin instance.
     */
    public JavaPlugin getPlugin() {
        return plugin;
    }

    /**
     *  Registers multiple commands with their respective CommandExecutors.
     *
     * @param command Can be created using {@link #bundler(CommandExecutor, String)}
     */
    public void registerCommands(CommandRegister... command){
        Arrays.stream(command).forEach(commandRegister -> plugin.getCommand(commandRegister.getCommandName()).setExecutor(commandRegister.getCommandExecutor()));
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
