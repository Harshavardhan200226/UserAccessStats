package com.githubaccessreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AccessReportController {

    @Autowired
    private AccessReportService service;

    @GetMapping("/access-report/{org}")
    public ResponseEntity<Map<String, Object>> getAccessReport(@PathVariable String org) {
        try {
            Map<String, Object> report = service.generateReport(org);
            return ResponseEntity.ok(report);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to generate report: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid organization or authentication issue: " + e.getMessage()));
        }
    }
}