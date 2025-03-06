package com.async.engine.controller;

import com.async.engine.dto.TaskProgress;
import com.async.engine.service.VirtualTaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final VirtualTaskService taskService;

    public TaskController(VirtualTaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/run")
    public ResponseEntity<String> runTasks() {
        // Trigger tasks asynchronously; no need to wait for their result.
        taskService.performLongRunningTask();
        taskService.performAnotherTask();
        // Immediately return a response.
        return ResponseEntity.ok("Tasks started");
    }

    @GetMapping("/run-await")
    public CompletableFuture<String> runTasksAwait() {
        CompletableFuture<String> task1 = taskService.performLongRunningTask();
        CompletableFuture<String> task2 = taskService.performAnotherTask();

        return task1.thenCombine(task2, (result1, result2) ->
                "Results: " + result1 + " | " + result2);
    }

    @GetMapping("/progress")
    public Map<String, Map<String, Object>> getProgress() {
        // Convert the progress map into a JSON-friendly structure.
        return taskService.getProgressMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            TaskProgress tp = entry.getValue();
                            return Map.of(
                                    "thread", tp.getThreadName(),
                                    "progress", tp.getProgress()
                            );
                        }
                ));
    }

}