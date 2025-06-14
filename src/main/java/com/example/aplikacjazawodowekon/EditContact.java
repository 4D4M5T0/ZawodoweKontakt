package com.example.aplikacjazawodowekon;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditContact implements Initializable {

    @FXML private TextField nameField, emailField, phoneField;
    private Contact currentContact;
    private String originalEmail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentContact = Main.getSelectedContact();
        if (currentContact != null) {
            nameField.setText(currentContact.getName());
            emailField.setText(currentContact.getEmail());
            phoneField.setText(currentContact.getPhone());
            originalEmail = currentContact.getEmail();
        }
    }

    @FXML
    protected void updateContact() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        if (!validateInput(name, email, phone)) return;

        String[] parts = name.split(" ", 2);
        String firstName = parts[0];
        String lastName = parts.length > 1 ? parts[1] : "";

        boolean success = Database.updateContact(originalEmail, firstName, lastName, email, phone, Login.currentUserId);

        if (success) {
            Main.refreshContacts();
            showSuccessAlert();
            goToMainView();
        } else {
            showAlert("Aktualizacja nieudana", "Nie udało się zaktualizować kontaktu. Adres e-mail może już istnieć.");
        }
    }

    @FXML
    protected void cancel() {
        try {
            HelloApplication.changeScene("contacts-views.fxml", "Contact Details");
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sukces");
        alert.setHeaderText("Kontakt zaktualizowany");
        alert.setContentText("Kontakt został pomyślnie zaktualizowany.");
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