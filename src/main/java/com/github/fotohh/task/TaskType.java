package com.github.fotohh.task;

public enum TaskType {
    SYNC_TASK_TIMER,
    ASYNC_TASK_TIMER,
    ASYNC_RUN_LATER,
    SYNC_RUN_LATER
    ;

    private int delay;
    private int startDelay;

    public TaskType setDelayUntilStart(int value){
        this.startDelay = value;
        return this;
    }

    public int getDelay() {
        return delay;
    }

    public int getStartDelay() {
        return startDelay;
    }

    public TaskType setTaskDelay(int value){
        this.delay = value;
        return this;
    }
}
