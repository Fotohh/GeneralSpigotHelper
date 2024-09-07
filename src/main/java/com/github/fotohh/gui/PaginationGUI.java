package com.github.fotohh.gui;

import com.github.fotohh.itemutil.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * The pagination GUI class represents a GUI with a fixed number of items per page.
 * The GUI contains forward and backward buttons to navigate between pages.
 * The GUI also contains a page number item to display the current page number.
 * The GUI is registered with the GUIManager upon creation.
 */
public class PaginationGUI extends GUI{

    private int pageNumber;
    private final int itemsPerPage;
    private ItemBuilder forwardButton;
    private final int size;
    private ItemBuilder backwardsButton;
    private ItemBuilder pageNumberItem;
    private final List<ItemStack> items = new ArrayList<>();

    /**
     * Get the forward button item.
     * @return The forward button item.
     */
    public ItemBuilder getForwardButton() {
        return forwardButton;
    }

    /**
     * Set the forward button item.
     * @param forwardButton The forward button item.
     */
    public void setForwardButton(ItemBuilder forwardButton) {
        this.forwardButton = forwardButton;
    }

    /**
     * Get the list of items in the GUI.
     * @return The list of items in the GUI.
     */
    public List<ItemStack> getItems() {
        return items;
    }

    /**
     * Set the backward button item.
     * @param backwardsButton The backward button item.
     */
    public void setBackwardsButton(ItemBuilder backwardsButton) {
        this.backwardsButton = backwardsButton;
    }

    /**
     * Get the backward button item.
     * @return The backward button item.
     */
    public ItemBuilder getBackwardsButton() {
        return backwardsButton;
    }

    /**
     * Constructs a PaginationGUI object with the specified Player, title, size, and GUIManager.
     * @param player The Player who owns the GUI.
     * @param title The title of the GUI.
     * @param size The size of the GUI inventory.
     * @param guiManager The GUIManager associated with the GUI.
     */
    public PaginationGUI(Player player, String title, int size, GUIManager guiManager){
        super(player, title, size, guiManager);
        this.size = size;
        this.pageNumber = 0;
        this.itemsPerPage = size-9;
    }

    /**
     * Set the page number item.
     * @param pageNumberItem The page number item.
     */
    public void setPageNumberItem(ItemBuilder pageNumberItem) {
        this.pageNumberItem = pageNumberItem;
    }

    /**
     * Get the page number item.
     * @return The page number item.
     */
    public ItemBuilder getPageNumberItem() {
        return pageNumberItem;
    }

    /**
     * Get the number of items per page.
     * @return The number of items per page.
     */
    public int getAmountOfItemsPerPage() {
        return itemsPerPage;
    }

    /**
     * Get the current page number.
     * @return The current page number.
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Updates the inventory using {@link #updateInventory()} and opens the GUI for the owner (Player).
     * Opens the GUI for the owner (Player).
     */
    @Override
    public void openGUI() {
        updateInventory();
        super.openGUI();
    }

    /**
     * Updates the inventory with the items in the GUI, the forward and backward buttons, and the page number item.
     */
    public void updateInventory(){
        inventory.clear();
        int count = (itemsPerPage - 1)  * pageNumber;
        int preCount = 0;
        for(ItemStack itemStack : items) {
            if(preCount < count) {
                preCount++;
                continue;
            }
            if(preCount < count + itemsPerPage ){
                inventory.addItem(itemStack);
                preCount++;
            }
        }
        int forwardIndex = size-1;
        int backwardIndex = size-10;
        int pageNumberIndex = size-5;
        inventory.setItem(forwardIndex, forwardButton);
        inventory.setItem(backwardIndex, backwardsButton);
        inventory.setItem(pageNumberIndex, pageNumberItem);
    }

    /**
     * Adds an item to the GUI.
     * @param itemStack The item to add to the GUI.
     */
    public void addItem(ItemStack itemStack){
        getItems().add(itemStack);
    }

    /**
     * Handles the InventoryClickEvent for the GUI.
     * @param event The InventoryClickEvent to handle.
     */
    @Override
    public void handleClickEvent(InventoryClickEvent event) {

        if (!event.getInventory().equals(inventory)) return;

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
}
