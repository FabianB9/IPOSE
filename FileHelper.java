package com.hsleiden.nl.challangeweek;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

public class FileHelper {
    private static final String USERNAMES_FILE = "usernames.txt";
    private static final String PASSWORDS_FILE = "passwords.txt";
    private static final String DELIMITER = ",";

    public static List<String> readUsernames() {
        List<String> usernames = new ArrayList<>();
        try {
            File file = new File(USERNAMES_FILE);
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split(DELIMITER);
                    usernames.add(fields[0]);
                }
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading usernames from file: " + e.getMessage());
        }
        return usernames;
    }

    public static List<String> readPasswords() {
        List<String> passwords = new ArrayList<>();
        try {
            File file = new File(PASSWORDS_FILE);
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader("file"));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split(DELIMITER);
                    passwords.addAll(Arrays.asList(fields));
                }
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading passwords from file: " + e.getMessage());
        }
        return passwords;
    }

    public static void writeUsername(String username) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(USERNAMES_FILE, true));
            writer.write(username + DELIMITER);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing username to file: " + e.getMessage());
        }
    }

    public static void writePassword(String password) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(PASSWORDS_FILE, true));
            writer.write(password + DELIMITER);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing password to file: " + e.getMessage());
        }
    }

    public static HashMap<String, String> readUsernamesAndPasswords(String usernamesFile, String passwordsFile) throws IOException {
        HashMap<String, String> map = new HashMap<>();
        BufferedReader usernamesReader = new BufferedReader(new FileReader(usernamesFile));
        BufferedReader passwordsReader = new BufferedReader(new FileReader(passwordsFile));
        String username;
        String password;
        while ((username = usernamesReader.readLine()) != null && (password = passwordsReader.readLine()) != null) {
            map.put(username, password);
        }
        usernamesReader.close();
        passwordsReader.close();
        return map;
    }

    public static void writeUsernamesAndPasswords(HashMap<String, String> map, String usernamesFile, String passwordsFile) throws IOException {
        FileWriter usernamesWriter = new FileWriter(usernamesFile);
        FileWriter passwordsWriter = new FileWriter(passwordsFile);
        for (String username : map.keySet()) {
            String password = map.get(username);
            usernamesWriter.write(username + "\n");
            passwordsWriter.write(password + "\n");
        }
        usernamesWriter.close();
        passwordsWriter.close();
    }
}
