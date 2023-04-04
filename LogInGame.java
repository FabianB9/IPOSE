package com.hsleiden.nl.challangeweek;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LogInGame extends Application {
    private Stage primaryStage;
    private Scene loginScene;
    private TextField loginUsernameField;
    private PasswordField loginPasswordField;
    private Label loginActionTarget;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        // Create the login form
        createLoginForm();
        // Show the login form
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void createLoginForm() {
        // Create the login form UI components
        Label loginTitle = new Label("Log In");
        loginTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Label loginUsernameLabel = new Label("Username:");
        loginUsernameField = new TextField();
        Label loginPasswordLabel = new Label("Password:");
        loginPasswordField = new PasswordField();
        Button loginButton = new Button("Log In");
        loginButton.setOnAction(e -> {
            if (isValidLogin(loginUsernameField.getText(), loginPasswordField.getText())) {
                loginActionTarget.setText("Login successful!");
                // Open the main game window or scene here
            } else {
                loginActionTarget.setText("Invalid username or password!");
            }
        });
        loginActionTarget = new Label();
        // Create the login form layout
        GridPane loginLayout = new GridPane();
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setHgap(10);
        loginLayout.setVgap(10);
        loginLayout.setPadding(new Insets(25, 25, 25, 25));
        loginLayout.add(loginTitle, 0, 0, 2, 1);
        loginLayout.add(loginUsernameLabel, 0, 1);
        loginLayout.add(loginUsernameField, 1, 1);
        loginLayout.add(loginPasswordLabel, 0, 2);
        loginLayout.add(loginPasswordField, 1, 2);
        loginLayout.add(loginButton, 0, 3, 2, 1);
        loginLayout.add(loginActionTarget, 0, 4, 2, 1);
        // Create the login scene
        loginScene = new Scene(loginLayout, 400, 300);
    }

    private boolean isValidLogin(String username, String password) {
        // Validate the login information (e.g. check if username and password match an existing user)
        String userFile = "usernames.txt";
        String passFile = "passwords.txt";
        try {
            Scanner scannerUser = new Scanner(new File("usernames.txt"));
            Scanner scannerPass = new Scanner(new File("passwords.txt"));
            while (scannerUser.hasNextLine() && scannerPass.hasNextLine()) {
                String userLine = scannerUser.nextLine();
                String passLine = scannerPass.nextLine();
                if (userLine.equals(username) && passLine.equals(password)) {
                    return true;
                }
            }
            scannerUser.close();
            scannerPass.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}