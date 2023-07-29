/**
 * The GUI interface represents a graphical user interface for a Bukkit plugin.
 * It provides methods to interact with and manage the GUI, as well as handle events.
 * Implementing classes should handle GUI creation and interaction based on their specific type.
 * This interface extends the Listener interface to allow event handling.
 */
package com.github.fotohh.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.function.Consumer;

public interface GUI extends Listener {

    /**
     * Get the owner (Player) of this GUI.
     *
     * @return The Player who owns this GUI.
     */
    Player getOwner();

    /**
     * Get the underlying Inventory associated with this GUI.
     *
     * @return The Inventory object representing this GUI.
     */
    Inventory getInventory();

    /**
     * Update the content of the GUI. Not required but highly advised.
     */
    default void updateGUI(){}

    /**
     * Open the GUI for the owner (Player).
     */
    void openGUI();

    /**
     * Get the title of the GUI.
     *
     * @return The title of the GUI.
     */
    String getTitle();

    /**
     * Gets the Consumer for handling InventoryClickEvent in the GUI.
     *
     * @return The Consumer for InventoryClickEvent, or null if none is set.
     * @since 1.0.2
     */
    Consumer<InventoryClickEvent> getConsumer();

    /**
     * Set a custom action to be executed when an InventoryClickEvent occurs.
     * The Consumer will receive the InventoryClickEvent as input.
     *
     * @param event The custom action to be executed on InventoryClickEvent.
     */
    void onInventoryClick(Consumer<InventoryClickEvent> event);

    /**
     * Handles the InventoryClickEvent for this GUI.
     * This method is annotated with @EventHandler to allow event handling.
     * When an InventoryClickEvent occurs, this method checks if the clicked inventory matches
     * the GUI's inventory. If they match, and a custom click event consumer is set using the
     * {@link #onInventoryClick(Consumer)} method, the consumer will be called, passing the
     * InventoryClickEvent as input to execute the custom action.
     *
     * @param event The InventoryClickEvent to handle.
     * @see #onInventoryClick(Consumer)
     * @since 1.0.2
     */
    @EventHandler
    default void handleClickEvent(InventoryClickEvent event){
        if (event.getInventory() == getInventory()) {
            if (getConsumer() != null) {
                getConsumer().accept(event);
            }
        }
    };

    /**
     * Unregister the GUI as a listener for event handling.
     * This should be called when the GUI is no longer needed to avoid memory leaks.
     */
    void unregisterListener();
}
