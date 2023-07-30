package com.github.fotohh;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to handle plugin dependencies and check if they are met.
 */
public class DependencyParser {

    private final JavaPlugin plugin;

    private final List<String> missingDependencies;
    private boolean disablePluginOnMissing;

    /**
     * Constructs a new DependencyParser instance for the given JavaPlugin.
     *
     */
    public DependencyParser(JavaPlugin plugin) {
        this.plugin = plugin;
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
