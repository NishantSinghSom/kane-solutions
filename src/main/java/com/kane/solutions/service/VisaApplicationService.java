package com.kane.solutions.service;

import com.kane.solutions.dto.ApplicantDTO;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class VisaApplicationService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$"
    );

    public String processVisaApplications(String filePath) throws IOException {
        List<ApplicantDTO> validApplicants = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        for (int i = 1; i < lines.size(); i++) { // Skip header
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(",");
            if (parts.length < 4) continue;

            String name = parts[0].trim();
            String ageStr = parts[1].trim();
            String address = parts[2].trim();
            String email = parts[parts.length - 1].trim(); // Last field is email

            // Check constraints
            if (!isValidEmail(email)) continue; // Invalid email
            if (!isIndiaResident(parts)) continue; // Not India resident

            // Determine category
            String category = "Unknown";
            if (!ageStr.isEmpty()) {
                try {
                    int age = Integer.parseInt(ageStr);
                    category = age > 18 ? "Adult" : "Kid";
                } catch (NumberFormatException e) {
                    continue; // Invalid age
                }
            }

            validApplicants.add(new ApplicantDTO(name, category));
        }

        return generateCSV(validApplicants);
    }

    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean isIndiaResident(String[] parts) {
        // Check if "India" is in any of the address fields
        for (int i = 2; i < parts.length - 1; i++) {
            if (parts[i].trim().toLowerCase().contains("india")) {
                return true;
            }
        }
        return false;
    }

    private String generateCSV(List<ApplicantDTO> applicants) throws IOException {
        Path csvPath = Paths.get(System.getProperty("user.dir"), "output", "applicants.csv");
        Files.createDirectories(csvPath.getParent());

        try (BufferedWriter writer = Files.newBufferedWriter(csvPath)) {
           
            writer.write("Name, Category\n");
            
            for (ApplicantDTO applicant : applicants) {
                writer.write(applicant.getName() + ", " + applicant.getCategory() + "\n");
            }
        }

        return csvPath.toString();
    }
}
