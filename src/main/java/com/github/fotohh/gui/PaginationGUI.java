package com.github.fotohh.gui;

import com.github.fotohh.itemutil.ItemBuilder;
import com.github.fotohh.itemutil.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PaginationGUI implements GUI{

    private final Player player;
    private final Inventory inventory;
    private int pageNumber;
    private final int itemsPerPage;
    private final String title;
    private Consumer<InventoryClickEvent> clickEventConsumer;
    private final ItemManager itemManager;
    private ItemBuilder forwardButton;
    private ItemBuilder backwardsButton;
    private ItemBuilder pageNumberItem;
    private final List<ItemStack> items = new ArrayList<>();

    public ItemBuilder getForwardButton() {
        return forwardButton;
    }

    public void setForwardButton(ItemBuilder forwardButton) {
        this.forwardButton = forwardButton;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public void setBackwardsButton(ItemBuilder backwardsButton) {
        this.backwardsButton = backwardsButton;
    }

    public ItemBuilder getBackwardsButton() {
        return backwardsButton;
    }

    public PaginationGUI(Player player, String title, int size, JavaPlugin plugin){
        this.player = player;
        this.itemManager = new ItemManager(this);
        this.title = title;
        this.pageNumber = 0;
        this.itemsPerPage = size-9;
        this.inventory = Bukkit.createInventory(player, size, title);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void setPageNumberItem(ItemBuilder pageNumberItem) {
        this.pageNumberItem = pageNumberItem;
    }

    public ItemBuilder getPageNumberItem() {
        return pageNumberItem;
    }

    public int getAmountOfItemsPerPage() {
        return itemsPerPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Get the owner (Player) of this GUI.
     *
     * @return The Player who owns this GUI.
     */
    @Override
    public Player getOwner() {
        return player;
    }

    /**
     * Get the underlying Inventory associated with this GUI.
     *
     * @return The Inventory object representing this GUI.
     */
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Open the GUI for the owner (Player).
     */
    @Override
    public void openGUI() {
        updateInventory();
        player.openInventory(inventory);
    }

    private void updateInventory(){
        inventory.clear();
        int count = (itemsPerPage - 1)  * pageNumber;
        for(int i = count; i < count + itemsPerPage; i++){
            int index = i - count;
            inventory.setItem(index, items.get(i));
        }
        int forwardIndex = itemsPerPage-1;
        int backwardIndex = itemsPerPage-10;
        int pageNumberIndex = itemsPerPage-5;
        inventory.setItem(forwardIndex, forwardButton);
        inventory.setItem(backwardIndex, backwardsButton);
        inventory.setItem(pageNumberIndex, pageNumberItem);
    }

    /**
     * Get the title of the GUI.
     *
     * @return The title of the GUI.
     */
    @Override
    public String getTitle() {
        return title;
    }

    public void addItem(ItemStack itemStack){
        getItems().add(itemStack);
    }

    /**
     * Gets the Consumer for handling InventoryClickEvent in the GUI.
     *
     * @return The Consumer for InventoryClickEvent, or null if none is set.
     * @since 1.0.2
     */
    @Override
    public Consumer<InventoryClickEvent> getConsumer() {
        return clickEventConsumer;
    }

    /**
     * Set a custom action to be executed when an InventoryClickEvent occurs.
     * The Consumer will receive the InventoryClickEvent as input.
     *
     * @param event The custom action to be executed on InventoryClickEvent.
     */
    @Override
    public void onInventoryClick(Consumer<InventoryClickEvent> event) {
        this.clickEventConsumer = clickEventConsumer;
    }

    @Override
    @EventHandler
    public void handleClickEvent(InventoryClickEvent event) {

        if (event.getInventory() != getInventory()) return;

        if(event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;

        event.setCancelled(true);

        if(pageNumber == 0 && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(backwardsButton.getItemMeta().getDisplayName())) return;

        if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(forwardButton.getItemMeta().getDisplayName())){
            if(inventory.getContents().length < itemsPerPage * pageNumber+1) return;
            pageNumber++;
            updateInventory();
        }
        if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(backwardsButton.getItemMeta().getDisplayName())){
            if(pageNumber == 0) return;
            pageNumber--;
            updateInventory();
        }

        if (getConsumer() != null) {
            getConsumer().accept(event);
        }

    }

    /**
     * Gets the ItemManager instance for managing ItemEvents associated with this GUI.
     *
     * @return The ItemManager associated with this GUI.
     * @since 1.0.4
     */
    @Override
    public ItemManager getItemManager() {
        return itemManager;
    }
}
