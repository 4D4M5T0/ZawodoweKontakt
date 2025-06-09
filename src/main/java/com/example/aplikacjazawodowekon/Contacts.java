package com.example.aplikacjazawodowekon;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Contacts implements Initializable {

    @FXML private Label nameLabel, emailLabel, phoneLabel;
    private Contact currentContact;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentContact = Main.getSelectedContact();
        if (currentContact != null) {
            nameLabel.setText("Name: " + currentContact.getName());
            emailLabel.setText("E-mail: " + currentContact.getEmail());
            phoneLabel.setText("Number: " + currentContact.getPhone());
        }
    }

    @FXML
    protected void Back() {
        changeScene("main-view.fxml", "Contacts");
    }

    @FXML
    protected void Edit() {
        if (currentContact == null) {
            showAlert("Błąd", "Nie wybrano kontaktu");
            return;
        }
        changeScene("edit-contact-view.fxml", "Edit Contact");
    }

    @FXML
    protected void Delete() {
        if (currentContact == null) {
            showAlert("Błąd", "Nie wybrano kontaktu");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Potwierdź usunięcie");
        confirmAlert.setHeaderText("Usuń kontakt");
        confirmAlert.setContentText("Czy na pewno chcesz usunąć ten kontakt?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Database.deleteContact(currentContact.getEmail(), Login.currentUserId);
            Main.refreshContacts();
            changeScene("main-view.fxml", "Contacts");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void changeScene(String fxml, String title) {
        try {
            HelloApplication.changeScene(fxml, title);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Błąd", "Nie udało się zmienić widoku");
        }
    }
}