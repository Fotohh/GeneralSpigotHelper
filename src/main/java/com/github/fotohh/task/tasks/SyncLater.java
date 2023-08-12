package com.github.fotohh.task.tasks;

import com.github.fotohh.task.TaskScheduler;
import com.github.fotohh.task.TaskType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;
import java.util.function.Consumer;

public class SyncLater extends Task{

    public SyncLater(TaskScheduler taskScheduler, UUID uuid, TaskType taskType, Consumer<BukkitTask> consumer, JavaPlugin plugin) {
        super(taskScheduler, uuid, taskType, consumer, plugin);
        run();
    }

    @Override
    public void run() {
        getPlugin().getServer().getScheduler().runTaskLater(getPlugin(), task -> {
            getConsumer().accept(task);
            setTask(task);
        }, getType().getStartDelay());
    }
}
