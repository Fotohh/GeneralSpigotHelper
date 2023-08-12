package com.github.fotohh.task;

import com.github.fotohh.task.tasks.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class TaskScheduler {

    private final JavaPlugin plugin;

    private final Map<UUID, Task> taskMap = new HashMap<>();

    public TaskScheduler(JavaPlugin plugin){
        this.plugin = plugin;
    }

    public Map<UUID, Task> getTasks() {
        return taskMap;
    }

    public Task getTask(UUID uuid){
        return taskMap.get(uuid);
    }

    public Task scheduleNewTask(TaskType taskType, Consumer<BukkitTask> consumer){
        UUID uuid = UUID.randomUUID();
        Task task = setTaskType(taskType, uuid, consumer);
        taskMap.put(uuid, task);
        return task;
    }

    private Task setTaskType(TaskType taskType, UUID uuid, Consumer<BukkitTask> consumer){
        switch (taskType){
            case SYNC_RUN_LATER:
                return new SyncLater(this, uuid, taskType, consumer, plugin);
            case SYNC_TASK_TIMER:
                return new SyncTimer(this, uuid, taskType, consumer, plugin);
            case ASYNC_RUN_LATER:
                return new AsyncLater(this, uuid, taskType, consumer, plugin);
            case ASYNC_TASK_TIMER:
                return new AsyncTimer(this, uuid, taskType, consumer, plugin);
            default:
                return null;
        }
    }

}
