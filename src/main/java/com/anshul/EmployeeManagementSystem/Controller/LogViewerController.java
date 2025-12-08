package com.anshul.EmployeeManagementSystem.Controller;

import org.apache.commons.io.input.ReversedLinesFileReader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@RestController
public class LogViewerController {

    // Log file path defined in logback-spring.xml
    private static final String LOG_FILE_PATH = "logs/request-audit.log";
    // The required line limit
    private static final int MAX_LINES = 500;

    @GetMapping(value = "/logs", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getAuditLogs() {
        File logFile = new File(LOG_FILE_PATH);

        if (!logFile.exists()) {
            return ResponseEntity.notFound().build();
        }

        try (ReversedLinesFileReader reader = new ReversedLinesFileReader(
                logFile, StandardCharsets.UTF_8)) {

            // Read the last MAX_LINES lines in reverse order
            List<String> lastLines = reader.readLines(MAX_LINES);

            // Reverse the list back to chronological order (oldest to newest)
            Collections.reverse(lastLines);

            String content = String.join("\n", lastLines);

            return ResponseEntity.ok(content);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error reading log file.");
        }
    }
}