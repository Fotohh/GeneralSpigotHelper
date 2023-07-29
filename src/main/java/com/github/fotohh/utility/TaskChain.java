package com.github.fotohh.utility;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Utility class for executing a chain of tasks sequentially or asynchronously.
 */
public class TaskChain {

    private final Plugin plugin;
    private final boolean async;
    private final Queue<SpigotRunnable> tasks;
    private SpigotRunnable finalTask;

    /**
     * Constructs a new TaskChain with the given plugin instance and execution mode.
     *
     * @param plugin The plugin instance to use for task scheduling.
     * @param async  Whether to execute the tasks asynchronously or not.
     */
    public TaskChain(Plugin plugin, boolean async) {
        this.plugin = plugin;
        this.async = async;
        this.tasks = new LinkedList<>();
    }

    /**
     * Adds a new task to the task chain.
     *
     * @param task The task to add to the chain.
     * @return This TaskChain instance to support method chaining.
     */
    public TaskChain addTask(SpigotRunnable task) {
        tasks.offer(task);
        return this;
    }

    /**
     * Sets the final task to be executed after all tasks in the chain are completed.
     *
     * @param finalTask The final task to be executed.
     * @return This TaskChain instance to support method chaining.
     */
    public TaskChain setFinalTask(SpigotRunnable finalTask) {
        this.finalTask = finalTask;
        return this;
    }

    /**
     * Executes the task chain sequentially or asynchronously, depending on the chosen mode.
     */
    public void execute() {
        executeNextTask();
    }

    private void executeNextTask() {
        SpigotRunnable task = tasks.poll();
        if (task != null) {
            if (async) {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                    task.run();
                    executeNextTask();
                });
            } else {
                Bukkit.getScheduler().runTask(plugin, () -> {
                    task.run();
                    executeNextTask();
                });
            }
        } else {
            if (finalTask != null) {
                if (async) {
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, finalTask);
                } else {
                    Bukkit.getScheduler().runTask(plugin, finalTask);
                }
            }
        }
    }
}
