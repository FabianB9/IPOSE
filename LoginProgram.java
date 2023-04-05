package com.hsleiden.nl.challangeweek;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginProgram extends Application {
    private final Map<String, String> loginDataMap = new HashMap<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Read login data from file
        readLoginData();

        // Create UI components
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Label statusLabel = new Label();

        // Set action for login button
        loginButton.setOnAction(event -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            // Check if entered username and password match any entry in the login data
            if (loginDataMap.containsKey(username) && loginDataMap.get(username).equals(password)) {
                statusLabel.setText("Logged in successfully.");
            } else {
                statusLabel.setText("Invalid username or password.");
            }
        });

        // Create a layout for the UI components
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, statusLabel);

        // Create a scene with the layout
        Scene scene = new Scene(vbox);

        // Set the scene and show the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    private void readLoginData() {
        String filePath = "C:\\Users\\Fabia\\IdeaProjects\\Challangeweek\\src\\main\\java\\com\\hsleiden\\nl\\challangeweek\\logindata.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] loginPair = line.split(",");
                for (String pair : loginPair) {
                    String[] loginData = pair.split(":");
                    String username = loginData[0].trim();
                    String password = loginData[1].trim();
                    loginDataMap.put(username, password);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
