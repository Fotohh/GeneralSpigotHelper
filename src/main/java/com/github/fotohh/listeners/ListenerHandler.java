package com.github.fotohh.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

public class ListenerHandler implements Listener {

    private final JavaPlugin plugin;
    private Consumer<Event> consumer;

    public ListenerHandler(JavaPlugin plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void onEventTrigger(Consumer<Event> consumer){
        this.consumer = consumer;
    }

    @EventHandler
    public void eventHandle(Event event){
        consumer.accept(event);
    }
    
    public void unregisterAll(){
        HandlerList.unregisterAll(this);
    }

}
