package com.github.fotohh.gui;

import com.github.fotohh.itemutil.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

public class ChestGUI implements GUI{

    private final Player player;
    private final Inventory inventory;
    private final String title;
    private Consumer<InventoryClickEvent> clickEventConsumer;

    public ChestGUI(Player player, String title, JavaPlugin plugin) {
        this.player = player;
        this.title = title;
        this.inventory = Bukkit.createInventory(player, 27, title);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public Player getOwner() {
        return player;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void openGUI() {
        player.openInventory(inventory);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void onInventoryClick(Consumer<InventoryClickEvent> eventConsumer) {
        this.clickEventConsumer = eventConsumer;
    }

    @Override
    public ItemManager getItemManager() {
        return new ItemManager(this);
    }

    @Override
    public void unregisterListener() {
        HandlerList.unregisterAll(this);
    }

    @Override
    public Consumer<InventoryClickEvent> getConsumer() {
        return clickEventConsumer;
    }

    @EventHandler
    @Override
    public void handleClickEvent(InventoryClickEvent event) {
        if (event.getInventory() == inventory) {
            if (clickEventConsumer != null) {
                clickEventConsumer.accept(event);
            }
        }
    }
}
