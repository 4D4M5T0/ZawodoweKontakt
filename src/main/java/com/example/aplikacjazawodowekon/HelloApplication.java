package com.example.aplikacjazawodowekon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        changeScene("login-view.fxml", "Login");
        mainStage.show();
    }
    public static void changeScene(String fxmlFile, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/aplikacjazawodowekon/" + fxmlFile));
        Scene scene = new Scene(fxmlLoader.load());
        mainStage.setScene(scene);
        mainStage.setTitle(title);
    }

    public static void main(String[] args) {
        launch();
    }
}