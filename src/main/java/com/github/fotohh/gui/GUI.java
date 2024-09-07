/**
 * The GUI interface represents a graphical user interface for a Bukkit plugin.
 * It provides methods to interact with and manage the GUI, as well as handle events.
 * Implementing classes should handle GUI creation and interaction based on their specific type.
 * This interface extends the Listener interface to allow event handling.
 */
package com.github.fotohh.gui;

import com.github.fotohh.itemutil.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

public class GUI {

    protected final Player player;
    protected final ItemManager itemManager;
    protected final String title;
    protected GUIManager manager;
    protected Inventory inventory;
    private Consumer<InventoryClickEvent> consumer;

    /**
     * Constructs a GUI object with the specified Player, title, size, and GUIManager.
     * The GUI is registered with the GUIManager upon creation.
     * The GUI's inventory is created with the specified size and title.
     *
     * @param player The Player who owns the GUI.
     * @param title The title of the GUI.
     * @param size The size of the GUI inventory.
     * @param manager The GUIManager associated with the GUI.
     */
    public GUI(Player player, String title, int size, GUIManager manager){
        this.player = player;
        this.manager = manager;
        this.itemManager = new ItemManager(this);
        this.title = title;
        this.inventory = Bukkit.createInventory(player, size, title);
    }

    /**
     * Get the owner (Player) of this GUI.
     *
     * @return The Player who owns this GUI.
     */
    public Player getOwner(){
        return player;
    }

    /**
     * Get the underlying Inventory associated with this GUI.
     *
     * @return The Inventory object representing this GUI.
     */
    public Inventory getInventory(){
        return inventory;
    }

    /**
     * Open the GUI for the owner (Player).
     */
    public void openGUI(){
        manager.register(this);
        player.openInventory(inventory);
    }

    /**
     * Get the title of the GUI.
     *
     * @return The title of the GUI.
     */
    public String getTitle(){
        return title;
    }

    /**
     * Gets the Consumer for handling InventoryClickEvent in the GUI.
     *
     * @return The Consumer for InventoryClickEvent, or null if none is set.
     * @since 1.0.2
     */
    protected Consumer<InventoryClickEvent> getConsumer(){
        return consumer;
    }

    /**
     * Set a custom action to be executed when an InventoryClickEvent occurs.
     * The Consumer will receive the InventoryClickEvent as input.
     *
     * @param event The custom action to be executed on InventoryClickEvent.
     */
    public void onInventoryClick(Consumer<InventoryClickEvent> event){
        consumer = event;
    }

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
    protected void handleClickEvent(InventoryClickEvent event){
        if (event.getInventory().equals(inventory)) {
            if (consumer != null) {
                consumer.accept(event);
            }
        }
    };


    /**
     * Gets the ItemManager instance for managing ItemEvents associated with this GUI.
     *
     * @return The ItemManager associated with this GUI.
     * @since 1.0.4
     */
    public ItemManager getItemManager(){
        return itemManager;
    }

}
