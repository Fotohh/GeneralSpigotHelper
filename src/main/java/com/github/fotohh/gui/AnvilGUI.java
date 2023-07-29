package com.github.fotohh.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

public class AnvilGUI implements GUI, Listener {
    private final Player player;
    private final Inventory inventory;
    private final String title;
    private Consumer<InventoryClickEvent> clickEventConsumer;

    public AnvilGUI(Player player, String title, JavaPlugin plugin) {
        this.player = player;
        this.title = title;
        this.inventory = Bukkit.createInventory(player, InventoryType.ANVIL, title);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void unregisterListener() {
        HandlerList.unregisterAll(this);
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
    public Consumer<InventoryClickEvent> getConsumer() {
        return clickEventConsumer;
    }

    @Override
    public void onInventoryClick(Consumer<InventoryClickEvent> eventConsumer) {
        this.clickEventConsumer = eventConsumer;
    }

}
