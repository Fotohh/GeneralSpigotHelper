package com.github.fotohh;

import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        this.parser = new DependencyParser();
        this.permissionManager = new PermissionManager();
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
     * Represents a command registration containing the CommandExecutor and the command name.
     *
     */
    static class CommandRegister {

        private final CommandExecutor commandExecutor;

        private final String commandName;

        /**
         * Constructs a new CommandRegister with the specified CommandExecutor and command name.
         * @param commandExecutor The CommandExecutor to set for the command.
         * @param commandName     The name of the command.
         */
        public CommandRegister(CommandExecutor commandExecutor, String commandName) {
            this.commandExecutor = commandExecutor;
            this.commandName = commandName;
        }

        /**
         * Gets the CommandExecutor associated with the command.
         * @return The CommandExecutor.
         */
        public CommandExecutor getCommandExecutor() {
            return commandExecutor;
        }

        /**
         * Gets the name of the command.
         *
         * @return The command name.
         */
        public String getCommandName() {
            return commandName;
        }

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

    /**
     * Utility class to handle plugin dependencies and check if they are met.
     */
    private class DependencyParser {

        private final List<String> missingDependencies;
        private boolean disablePluginOnMissing;

        /**
         * Constructs a new DependencyParser instance for the given JavaPlugin.
         *
         */
        public DependencyParser() {
            this.missingDependencies = new ArrayList<>();
            this.disablePluginOnMissing = false;
        }

        /**
         * Adds a single dependency to the list of required plugins.
         *
         * @param pluginName The name of the required plugin.
         * @return The DependencyParser instance for method chaining.
         */
        public DependencyParser addDependency(String pluginName) {
            if (plugin.getServer().getPluginManager().getPlugin(pluginName) == null) {
                missingDependencies.add(pluginName);
            }
            return this;
        }

        /**
         * Adds multiple dependencies to the list of required plugins.
         *
         * @param pluginNames The names of the required plugins.
         * @return The DependencyParser instance for method chaining.
         */
        public DependencyParser addDependencies(String... pluginNames) {
            for (String pluginName : pluginNames) {
                addDependency(pluginName);
            }
            return this;
        }

        /**
         * Checks if all the required dependencies are met.
         *
         * @return {@code true} if all dependencies are met, otherwise {@code false}.
         */
        public boolean areDependenciesMet() {
            return missingDependencies.isEmpty();
        }

        /**
         * Gets the list of missing dependencies.
         *
         * @return A List containing the names of the missing dependencies.
         */
        public List<String> getMissingDependencies() {
            return missingDependencies;
        }

        /**
         * Checks if the plugin should be disabled if there are missing dependencies.
         *
         * @return {@code true} if the plugin should be disabled on missing dependencies, otherwise {@code false}.
         */
        public boolean isDisablePluginOnMissing() {
            return disablePluginOnMissing;
        }

        /**
         * Sets whether the plugin should be disabled if there are missing dependencies.
         *
         * @param disablePluginOnMissing {@code true} to disable the plugin on missing dependencies, {@code false} otherwise.
         * @return The DependencyParser instance for method chaining.
         */
        public DependencyParser setDisablePluginOnMissing(boolean disablePluginOnMissing) {
            this.disablePluginOnMissing = disablePluginOnMissing;
            return this;
        }

        /**
         * Checks the dependencies and disables the plugin if required, based on the settings.
         *
         * @return {@code true} if all dependencies are met or the plugin is not set to be disabled on missing dependencies,
         *         otherwise {@code false}.
         */
        public boolean checkDependencies() {
            if (!areDependenciesMet() && disablePluginOnMissing) {
                plugin.getServer().getPluginManager().disablePlugin(plugin);
                return false;
            }
            return true;
        }
    }

    /**
     * The PermissionManager class provides utility methods for handling player permissions in Bukkit.
     * It allows checking, granting, and revoking permissions for players.
     */
    private class PermissionManager {

        /**
         * Checks if a player has a specific permission node.
         *
         * @param player        The Player to check for permissions.
         * @param permissionNode The permission node to check.
         * @return true if the player has the specified permission, false otherwise.
         */
        public boolean hasPermission(Player player, String permissionNode) {
            return player.hasPermission(permissionNode);
        }

        /**
         * Grants a specific permission node to a player.
         *
         * @param player        The Player to grant the permission.
         * @param permissionNode The permission node to grant.
         */
        public void givePermission(Player player, String permissionNode) {
            player.addAttachment(plugin).setPermission(permissionNode, true);
        }

        /**
         * Revokes a specific permission node from a player.
         *
         * @param player        The Player from whom to revoke the permission.
         * @param permissionNode The permission node to revoke.
         */
        public void revokePermission(Player player, String permissionNode) {
            player.addAttachment(plugin).unsetPermission(permissionNode);
        }
    }

}
