package com.github.fotohh.itemutil;

import com.github.fotohh.gui.GUI;

import java.util.ArrayList;

/**
 * The ItemManager class is responsible for managing ItemEvent instances for a specific GUI.
 * It allows registering, removing, and unregistering all ItemEvents associated with the GUI.
 *
 * @since 1.0.4
 */
public class ItemManager {

    private final GUI gui;
    private final ArrayList<ItemEvent> items = new ArrayList<>();

    /**
     * Constructs a new ItemManager for the given GUI.
     *
     * @param gui The GUI instance to manage ItemEvents for.
     * @since 1.0.4
     */
    public ItemManager(GUI gui) {
        this.gui = gui;
    }

    /**
     * Registers a new ItemEvent for a custom item in the GUI.
     *
     * @param itemBuilder The ItemBuilder representing the custom item in the GUI.
     * @return The registered ItemEvent.
     * @since 1.0.4
     */
    public ItemEvent registerItemEvent(ItemBuilder itemBuilder) {
        ItemEvent itemEvent = new ItemEvent(itemBuilder, gui);
        items.add(itemEvent);
        return itemEvent;
    }

    /**
     * Gets a list of all registered ItemEvents associated with the GUI.
     *
     * @return A list of ItemEvents.
     * @since 1.0.4
     */
    public ArrayList<ItemEvent> getItems() {
        return items;
    }

    /**
     * Removes an ItemEvent from the manager and unregisters it from Bukkit's event system.
     *
     * @param itemEvent The ItemEvent to be removed.
     * @since 1.0.4
     */
    public void removeItem(ItemEvent itemEvent) {
        itemEvent.unregister();
        items.remove(itemEvent);
    }

    /**
     * Unregisters all ItemEvents associated with the GUI from Bukkit's event system.
     *
     * @since 1.0.4
     */
    public void unregisterAll() {
        items.forEach(itemEvent -> {
            itemEvent.unregister();
            items.remove(itemEvent);
        });
    }
}
