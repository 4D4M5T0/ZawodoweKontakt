package com.example.aplikacjazawodowekon;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class Contacts implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneLabel;

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
        try {
            HelloApplication.changeScene("main-view.fxml", "Contacts");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void Edit() {
    }

    @FXML
    protected void Delete() {
    }
}