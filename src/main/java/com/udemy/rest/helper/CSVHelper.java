package com.udemy.rest.helper;

import com.udemy.rest.model.User;
import org.apache.commons.csv.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

public class CSVHelper {
    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<User> csvToUsers(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<User> users = new ArrayList<>();

            for (CSVRecord csvRecord : csvParser) {
                String name = csvRecord.get("name");
                int age = Integer.parseInt(csvRecord.get("age"));
                users.add(new User(name, age));
            }

            return users;

        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }
    }
}
