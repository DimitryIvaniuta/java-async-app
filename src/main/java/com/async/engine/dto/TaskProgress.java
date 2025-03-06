package com.async.engine.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskProgress {

    @Getter
    @Setter
    private String threadName;

    private final AtomicInteger progress = new AtomicInteger(0);

    public TaskProgress(String threadName) {
        this.threadName = threadName;
    }

    public int getProgress() {
        return progress.get();
    }

    public void updateProgress(int value) {
        progress.set(value);
    }

}
