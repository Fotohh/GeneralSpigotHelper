package com.github.fotohh.task.tasks;

import com.github.fotohh.task.TaskScheduler;
import com.github.fotohh.task.TaskType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;
import java.util.function.Consumer;

public abstract class Task {

    private final UUID uuid;
    private final TaskType type;
    private final Consumer<BukkitTask> consumer;
    private final JavaPlugin plugin;
    private final TaskScheduler taskScheduler;
    private BukkitTask task;

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public Task(TaskScheduler taskScheduler, UUID uuid, TaskType taskType, Consumer<BukkitTask> consumer, JavaPlugin plugin) {
        this.uuid = uuid;
        this.type = taskType;
        this.consumer = consumer;
        this.plugin = plugin;
        this.taskScheduler = taskScheduler;
    }

    public void setTask(BukkitTask task) {
        this.task = task;
    }

    public BukkitTask getTask() {
        return task;
    }

    public TaskScheduler getTaskScheduler() {
        return taskScheduler;
    }

    public Consumer<BukkitTask> getConsumer() {
        return consumer;
    }

    public UUID getUuid() {
        return uuid;
    }

    public TaskType getType() {
        return type;
    }

    public abstract void run();

    public void remove(){
        getTask().cancel();
        taskScheduler.getTasks().remove(uuid);
    }

}
