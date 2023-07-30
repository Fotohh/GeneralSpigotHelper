package com.github.fotohh.itemutil;

import com.github.fotohh.gui.GUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;

import java.util.function.Consumer;

/**
 * The ItemEvent class is a custom event handler designed to handle various inventory-related events
 * for a specific GUI instance and associated ItemBuilder.
 * <p>
 * This class provides methods to register consumers for different inventory events like click, drag,
 * and item move. When an event occurs, the associated consumer is called, allowing custom handling
 * of the event.
 *
 * @since 1.0.4
 */
public class ItemEvent implements Listener {

    private final ItemBuilder itemBuilder;
    private final GUI gui;

    private Consumer<InventoryMoveItemEvent> inventoryMoveItemEventConsumer;
    private Consumer<InventoryDragEvent> inventoryDragEventConsumer;
    private Consumer<InventoryClickEvent> inventoryClickEventConsumer;

    /**
     * Constructs a new ItemEvent instance for the given ItemBuilder and GUI.
     *
     * @param itemBuilder The ItemBuilder associated with this ItemEvent.
     * @param gui         The GUI associated with this ItemEvent.
     * @since 1.0.4
     */
    public ItemEvent(ItemBuilder itemBuilder, GUI gui) {
        this.itemBuilder = itemBuilder;
        this.gui = gui;
    }

    /**
     * Sets the consumer to handle inventory click events.
     *
     * @param event The consumer that handles the InventoryClickEvent.
     * @since 1.0.4
     */
    public void onClick(Consumer<InventoryClickEvent> event) {
        this.inventoryClickEventConsumer = event;
    }

    /**
     * Sets the consumer to handle inventory drag events.
     *
     * @param event The consumer that handles the InventoryDragEvent.
     * @since 1.0.4
     */
    public void onDrag(Consumer<InventoryDragEvent> event) {
        this.inventoryDragEventConsumer = event;
    }

    /**
     * Sets the consumer to handle inventory move item events.
     *
     * @param event The consumer that handles the InventoryMoveItemEvent.
     * @since 1.0.4
     */
    public void onMove(Consumer<InventoryMoveItemEvent> event) {
        this.inventoryMoveItemEventConsumer = event;
    }

    /**
     * Event handler for handling InventoryClickEvent.
     * If the event's inventory matches the GUI's inventory, the associated consumer is called.
     *
     * @param event The InventoryClickEvent to be handled.
     * @since 1.0.4
     */
    @EventHandler
    public void handleClick(InventoryClickEvent event) {
        if (event.getInventory() == gui.getInventory()) {
            if (inventoryClickEventConsumer == null) return;
            inventoryClickEventConsumer.accept(event);
        }
    }

    /**
     * Event handler for handling InventoryDragEvent.
     * If the event's inventory matches the GUI's inventory, the associated consumer is called.
     *
     * @param event The InventoryDragEvent to be handled.
     * @since 1.0.4
     */
    @EventHandler
    public void handleDrag(InventoryDragEvent event) {
        if (event.getInventory() == gui.getInventory()) {
            if (inventoryClickEventConsumer == null) return;
            inventoryDragEventConsumer.accept(event);
        }
    }

    /**
     * Event handler for handling InventoryMoveItemEvent.
     * If the event's initiator or destination inventory matches the GUI's inventory,
     * the associated consumer is called.
     *
     * @param event The InventoryMoveItemEvent to be handled.
     * @since 1.0.4
     */
    @EventHandler
    public void handleMove(InventoryMoveItemEvent event) {
        if (event.getInitiator() == gui.getInventory() || event.getDestination() == gui.getInventory()) {
            if (inventoryClickEventConsumer == null) return;
            inventoryMoveItemEventConsumer.accept(event);
        }
    }

    /**
     * Event handler for handling InventoryCloseEvent.
     * If the event's inventory matches the GUI's inventory, the ItemEvent instance is unregistered.
     *
     * @param event The InventoryCloseEvent to be handled.
     * @since 1.0.4
     */
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory() == gui.getInventory()) {
            unregister();
        }
    }

    /**
     * Unregisters this ItemEvent instance from Bukkit's event handling system.
     * After calling this method, the ItemEvent will no longer respond to inventory events.
     *
     * @since 1.0.4
     */
    public void unregister() {
        HandlerList.unregisterAll(this);
    }

}
