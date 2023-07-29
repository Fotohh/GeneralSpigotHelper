package com.github.fotohh.utility;


import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class SpigotRunnable implements Runnable {

    private Logger logger;

    private final JavaPlugin plugin;

    /**
     * Constructs a new SpigotRunnable with the plugin's default logger.
     *
     * @param plugin The plugin instance that owns this task.
     */
    public SpigotRunnable(JavaPlugin plugin) {
        this(plugin, plugin.getLogger());
    }

    /**
     * Constructs a new SpigotRunnable with a custom logger.
     *
     * @param plugin The plugin instance that owns this task.
     * @param logger The custom logger to use for logging messages.
     */
    public SpigotRunnable(JavaPlugin plugin, Logger logger){
        this.plugin = plugin;
        if(logger != null){
            this.logger = logger;
        }
    }

    /**
     * Gets the logger associated with this task.
     *
     * @return The logger instance.
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Executes the task. Override this method in your implementation to define the task's behavior.
     */
    @Override
    public abstract void run();

    /**
     * Handles exceptions that may occur during the task execution.
     *
     * @param ex The exception to handle.
     */
    protected void handleException(Exception ex) {
        logger.log(Level.SEVERE, "An exception occurred in SpigotRunnable: " + this, ex);
    }

}
