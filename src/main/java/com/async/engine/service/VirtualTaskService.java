package com.async.engine.service;

import com.async.engine.dto.TaskProgress;
import com.async.engine.exception.EngineTaskException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class VirtualTaskService {

    private final Executor virtualThreadExecutor;

    // Concurrent map to track each task's progress
    // Keys are task names; values contain the thread name and progress value.
    private final ConcurrentHashMap<String, TaskProgress> progressMap = new ConcurrentHashMap<>();

    public VirtualTaskService(Executor virtualThreadExecutor) {
        this.virtualThreadExecutor = virtualThreadExecutor;
    }

    public CompletableFuture<String> performLongRunningTask() {
        return CompletableFuture.supplyAsync(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("Started long task on {}", threadName);
            // Put the thread info in the progress map before work starts
            progressMap.put("longTask", new TaskProgress(threadName));
            try {
                // Simulate work in 10 steps; total ~5 seconds (500 ms per step)
                for (int i = 1; i <= 10; i++) {
                    TimeUnit.MILLISECONDS.sleep(1700);
                    progressMap.get("longTask").updateProgress(i * 10);
                    log.debug("Long task progress: {}%", i * 10);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new EngineTaskException("Long task interrupted on " + threadName, e);
            }
            log.info("Completed long task on {}", threadName);
            return "Long task completed on " + threadName;
        }, virtualThreadExecutor);
    }

    public CompletableFuture<String> performAnotherTask() {
        return CompletableFuture.supplyAsync(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("Started another task on {}", threadName);
            // Store thread information before work starts
            progressMap.put("anotherTask", new TaskProgress(threadName));
            try {
                // Simulate work in 10 steps; total ~3 seconds (300 ms per step)
                for (int i = 1; i <= 10; i++) {
                    TimeUnit.MILLISECONDS.sleep(1300);
                    progressMap.get("anotherTask").updateProgress(i * 10);
                    log.debug("Another task progress: {}%", i * 10);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new EngineTaskException("Another task interrupted on " + threadName, e);
            }
            log.info("Completed another task on {}", threadName);
            return "Another task completed on " + threadName;
        }, virtualThreadExecutor);
    }


    // Getter for the progress map using the ConcurrentMap interface
    public ConcurrentMap<String, TaskProgress> getProgressMap() {
        return progressMap;
    }
}
