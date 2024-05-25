/**
 * The GUIManager class is responsible for creating various types of GUIs for a Bukkit plugin.
 * It allows the creation of predefined GUI types (Chest, Anvil, Workbench, Creative), as well as custom Chest GUIs.
 */
package com.github.fotohh.gui;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GUIManager {

    private final JavaPlugin plugin;

    /**
     * Constructs a GUIManager object with the specified JavaPlugin instance.
     *
     * @param plugin The JavaPlugin instance that the GUIManager is associated with.
     */
    public GUIManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Creates a specific type of GUI based on the provided GUIType.
     *
     * @param type   The type of GUI to create (CHEST, ANVIL, WORKBENCH, or CREATIVE).
     * @param player The player for whom the GUI is being created.
     * @param title  The title of the GUI.
     * @return The created GUI object.
     * @throws IllegalArgumentException If an invalid GUIType is provided.
     */
    public GUI createGUI(GUIType type, Player player, String title) {
        switch (type) {
            case CHEST:
                return new ChestGUI(player, title, plugin);
            case ANVIL:
                return new AnvilGUI(player, title, plugin);
            case WORKBENCH:
                return new WorkbenchGUI(player, title, plugin);
            default:
                throw new IllegalArgumentException("Invalid GUIType provided.");
        }
    }

    public GUI createPaginationGUI(String title, Player player, int size){
        return new PaginationGUI(player, title, size, plugin);
    }

    /**
     * Creates a custom Chest GUI with the specified size, title, and associated player.
     *
     * @param size   The size of the custom Chest GUI.
     * @param title  The title of the custom Chest GUI.
     * @param player The player for whom the custom Chest GUI is being created.
     * @return The created CustomChestGUI object.
     */
    public GUI createCustomChestGUI(int size, String title, Player player) {
        return new CustomChestGUI(player, title, size, plugin);
    }
}