package com.udemy.rest.controller;
import com.udemy.rest.helper.CSVHelper;
import com.udemy.rest.model.User;
import com.udemy.rest.service.CSVService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/csv")
public class CSVController {

    private final CSVService csvService;

    public CSVController(CSVService csvService) {
        this.csvService = csvService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        if (!CSVHelper.hasCSVFormat(file)) {
            return ResponseEntity.badRequest().body("Please upload a valid CSV file.");
        }
        csvService.saveCSVToDB(file);
        return ResponseEntity.ok("CSV uploaded and data saved to database.");
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadCSV() {
        String filename = "users.csv";
        InputStreamResource file = new InputStreamResource(csvService.downloadCSVFromDB());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(file);
    }

    @PostMapping("/generate-and-upload")
    public ResponseEntity<String> generateCSVAndUpload(@RequestBody List<User> users) {
        try {
            csvService.generateCSVFromUsersAndSave(users);
            return ResponseEntity.ok("CSV created and data uploaded to database.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
