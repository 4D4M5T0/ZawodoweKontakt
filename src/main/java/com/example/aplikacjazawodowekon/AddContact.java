package com.example.aplikacjazawodowekon;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class AddContact {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    protected void saveContact() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }

        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Email");
            alert.setContentText("Please enter a valid email address");
            alert.showAndWait();
            return;
        }

        if (!phone.matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Phone Number");
            alert.setContentText("Phone number should contain only digits");
            alert.showAndWait();
            return;
        }

        Contact newContact = new Contact(name, email, phone);

        Main.addContact(newContact);

        try {
            HelloApplication.changeScene("main-view.fxml", "Contacts");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void cancel() {
        try {
            HelloApplication.changeScene("main-view.fxml", "Contacts");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}