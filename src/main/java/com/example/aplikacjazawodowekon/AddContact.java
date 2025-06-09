package com.example.aplikacjazawodowekon;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class AddContact {

    @FXML private TextField nameField, emailField, phoneField;

    @FXML
    protected void saveContact() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        if (!validateInput(name, email, phone)) return;

        String[] parts = name.split(" ", 2);
        String firstName = parts[0];
        String lastName = parts.length > 1 ? parts[1] : "";

        Database.addContact(firstName, lastName, email, phone, Login.currentUserId);
        Main.refreshContacts();
        goToMainView();
    }

    @FXML
    protected void cancel() {
        goToMainView();
    }

    private boolean validateInput(String name, String email, String phone) {
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            showAlert("Brakujące informacje", "Proszę wypełnić wszystkie pola");
            return false;
        }

        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            showAlert("Nieprawidłowy adres e-mail", "Proszę wprowadzić prawidłowy adres e-mail");
            return false;
        }

        if (!phone.matches("\\d+")) {
            showAlert("Nieprawidłowy numer telefonu", "Numer telefonu powinien zawierać tylko cyfry");
            return false;
        }

        return true;
    }

    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void goToMainView() {
        try {
            HelloApplication.changeScene("main-view.fxml", "Contacts");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}