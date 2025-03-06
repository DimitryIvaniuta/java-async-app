package com.async.engine.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EngineErrorResponse {

    private LocalDateTime timestamp;

    private int status;

    private String error;

    private String message;

    private String path;

    @Override
    public String toString() {
        return "CustomErrorResponse{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

}
