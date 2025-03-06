# Spring Boot Async Tasks with Virtual Threads

## Overview
This is a Spring Boot application that demonstrates how to execute asynchronous tasks using Java 21 Virtual Threads (`Executors.newVirtualThreadPerTaskExecutor()`). The application provides REST endpoints to:

- Trigger long-running asynchronous tasks (`/run-tasks`)
- Check the progress of running tasks (`/progress`)

## Features
- Uses **Java 21 Virtual Threads** for lightweight concurrency.
- Tracks **progress of async tasks** using a `ConcurrentHashMap` with `AtomicInteger`.
- Provides **a REST API to trigger and monitor tasks**.
- Uses **Lombok for logging (`@Slf4j`)**.
- Implements **Spring Security with public access for specific endpoints**.

---

## Project Structure
```
my-spring-boot-project/
├── build.gradle
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/demo/
│   │   │       ├── DemoApplication.java
│   │   │       ├── controller/TaskController.java
│   │   │       ├── service/VirtualTaskService.java
│   │   │       ├── dto/TaskProgress.java
│   │   │       ├── config/
│   │   │       │   ├── SecurityConfig.java
│   │   │       │   ├── VirtualThreadConfig.java
│   │   │       ├── exception/CustomTaskException.java
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── logback-spring.xml
└── test/
```

---

## Setup Instructions

### Prerequisites
- **Java 21**
- **Gradle**
- **Spring Boot 3.1+**

### Clone the Repository
```sh
git clone https://github.com/your-repo/spring-boot-async-virtual-threads.git
cd spring-boot-async-virtual-threads
```

### Build the Project
```sh
./gradlew build
```

### Run the Application
```sh
./gradlew bootRun
```

---

## API Endpoints

### 1. Run Async Tasks
**Trigger background tasks asynchronously.**
```http
GET /run-tasks
```
#### Response:
```json
"Tasks started"
```

### 2. Check Task Progress
**Get the progress of running tasks.**
```http
GET /progress
```
#### Example Response:
```json
{
  "longTask": { "thread": "vt-1", "progress": 40 },
  "anotherTask": { "thread": "vt-2", "progress": 70 }
}
```

---

## Configuration Files

### 1. `application.yml`
```yaml
server:
  port: 8080

spring:
  application:
    name: demo

logging:
  level:
    root: INFO
    com.example.demo: DEBUG
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level [%thread] %logger{36} - %msg%n"
```

### 2. `logback-spring.xml`
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <property name="LOG_PATTERN" value="%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level [%thread] %logger{36} - %msg%n"/>
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>${LOG_PATTERN}</pattern>
        </encoder>
    </appender>
    <root level="INFO">
        <appender-ref ref="CONSOLE" />
    </root>
</configuration>
```

---

## Technologies Used
- **Spring Boot 3.1+**
- **Java 21 (Virtual Threads)**
- **Gradle**
- **Spring Security** (Configured to allow public API access)
- **Lombok** (`@Slf4j` for logging)
- **Logback** (for custom logging pattern)

---

## Contributors
- **Dzmitry Ivaniuta** - [Your GitHub Profile](https://github.com/DimitryIvaniuta)

---

## License
This project is provided for educational purposes. You may use and modify the code as needed.

