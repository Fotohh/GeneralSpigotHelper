package com.github.fotohh.task.tasks;

import com.github.fotohh.task.TaskScheduler;
import com.github.fotohh.task.TaskType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;
import java.util.function.Consumer;

public class AsyncTimer extends Task{

    public AsyncTimer(TaskScheduler taskScheduler, UUID uuid, TaskType taskType, Consumer<BukkitTask> consumer, JavaPlugin plugin) {
        super(taskScheduler, uuid, taskType, consumer, plugin);
        run();
    }

    @Override
    public void run() {
        getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), task -> {
            getConsumer().accept(task);
            setTask(task);
        }, getType().getStartDelay(), getType().getDelay());
    }
}
