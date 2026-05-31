package com.kane.solutions.controller;

import com.kane.solutions.service.VisaApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/visa")
public class VisaApplicationController {

    @Autowired
    private VisaApplicationService service;

    @PostMapping("/process")
    public ResponseEntity<?> processVisaApplications(@RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "success", false,
                        "error", "File is required. Please upload a text file with key name 'file'"
                ));
            }

            Path tempFile = Files.createTempFile("visa_", ".txt");
            file.transferTo(tempFile.toFile());
            String csvPath = service.processVisaApplications(tempFile.toString());
            Files.deleteIfExists(tempFile);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "csvFilePath", csvPath
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "error", e.getMessage()));
        }
    }
}
