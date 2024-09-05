
package com.github.fotohh.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedList;

/**
 * The GUIManager class is responsible for creating various types of GUIs for a Bukkit plugin.
 * This streamlines the creation process of GUI's and manages the GUI's that are currently open.
 */
public class GUIManager implements Listener {

    private final LinkedList<GUI> GUIList = new LinkedList<>();

    /**
     * Constructs a GUIManager object with the specified JavaPlugin instance.
     *
     * @param plugin The JavaPlugin instance that the GUIManager is associated with.
     */
    public GUIManager(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Registers a GUI with the GUIManager.
     *
     * @param gui The GUI object to register.
     */
    public void register(GUI gui) {
        if(!GUIList.contains(gui)) GUIList.add(gui);
    }

    /**
     * Unregisters a GUI with the GUIManager.
     *
     * @param gui The GUI object to unregister.
     */
    public void unregister(GUI gui) {
        GUIList.remove(gui);
    }

    /**
     * Unregisters all GUIs with the GUIManager.
     */
    public void unregisterAll() {
        GUIList.clear();
    }

    /**
     * Get the list of GUIs that are currently registered with the GUIManager.
     *
     * @return A LinkedList of GUI objects that are currently registered.
     */
    public LinkedList<GUI> getGUIList() {
        return GUIList;
    }

    /**
     * Handles the InventoryCloseEvent for all GUIs that are currently registered.
     * @param event The InventoryCloseEvent that was triggered.
     */
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        GUIList.forEach(gui -> {
            if(event.getInventory().equals(gui.getInventory())) unregister(gui);
        });
    }

    /**
     * Handles the InventoryClickEvent for all GUIs that are currently registered.
     * @param event The InventoryClickEvent that was triggered.
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        GUIList.forEach(gui -> {
            if(event.getInventory().equals(gui.getInventory()))
                if(gui.getConsumer() != null) gui.handleClickEvent(event);
        });
    }
}