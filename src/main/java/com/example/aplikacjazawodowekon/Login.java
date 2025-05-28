package com.example.aplikacjazawodowekon;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login {
    public static int currentUserId = -1;

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML
    protected void Login() {
        String login = emailField.getText();
        String password = passwordField.getText();

        if (!validateFields(login, password)) return;

        try {
            String hash = HashUtil.sha256(password);
            int userId = Database.loginUser(login, hash);

            if (userId != -1) {
                Login.currentUserId = userId;
                HelloApplication.changeScene("main-view.fxml", "Contacts");
            } else {
                showAlert("Login failed", "Invalid login or password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred during login");
        }
    }

    @FXML
    protected void Register() {
        String login = emailField.getText();
        String password = passwordField.getText();

        if (!validateFields(login, password)) return;

        try {
            String hash = HashUtil.sha256(password);
            boolean success = Database.registerUser(login, hash);

            if (success) {
                showAlert("Success", "Registration successful! Now log in.");
            } else {
                showAlert("Error", "User already exists or registration failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred during registration");
        }
    }

    private boolean validateFields(String login, String password) {
        if (login.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill in all fields");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}