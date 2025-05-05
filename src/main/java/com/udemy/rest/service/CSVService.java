package com.udemy.rest.service;

import com.udemy.rest.helper.CSVHelper;
import com.udemy.rest.model.User;
import com.udemy.rest.repository.UserRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Service
public class CSVService {

    private final UserRepository userRepository;

    public CSVService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //  Upload a real CSV file (manually)
    public void saveCSVToDB(MultipartFile file) {
        try {
            List<User> users = CSVHelper.csvToUsers(file.getInputStream());
            userRepository.saveAll(users);
        } catch (Exception e) {
            throw new RuntimeException("Failed to store CSV data: " + e.getMessage());
        }
    }

    //  Download data as CSV from DB
    public ByteArrayInputStream downloadCSVFromDB() {
        List<User> users = userRepository.findAll();
        final CSVFormat format = CSVFormat.DEFAULT.withHeader("id", "age", "name");

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             PrintWriter writer = new PrintWriter(out);
             CSVPrinter csvPrinter = new CSVPrinter(writer, format)) {

            for (User user : users) {
                csvPrinter.printRecord(user.getId(), user.getAge(), user.getName());
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("Failed to download CSV: " + e.getMessage());
        }
    }

    //  Generate CSV from request JSON and insert into DB
    public void generateCSVFromUsersAndSave(List<User> users) throws IOException {
        File tempFile = File.createTempFile("users_", ".csv");

        try (FileWriter writer = new FileWriter(tempFile);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("age","name"))) {

            for (User user : users) {
                csvPrinter.printRecord( user.getAge(), user.getName());
            }
            csvPrinter.flush();
        }

        try (FileInputStream fis = new FileInputStream(tempFile)) {
            List<User> parsedUsers = CSVHelper.csvToUsers(fis);
            userRepository.saveAll(parsedUsers);
        }

        tempFile.deleteOnExit();
    }
}
