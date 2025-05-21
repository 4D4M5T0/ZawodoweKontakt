package com.example.aplikacjazawodowekon;

import javafx.fxml.FXML;

public class Login {
    @FXML
    protected void Login() {
        try {
            HelloApplication.changeScene("main-view.fxml", "Contacts");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void Register() {
        try {
            HelloApplication.changeScene("main-view.fxml", "Contacts");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
