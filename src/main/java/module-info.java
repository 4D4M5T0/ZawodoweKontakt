module com.example.aplikacjazawodowekon {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.aplikacjazawodowekon to javafx.fxml;
    exports com.example.aplikacjazawodowekon;
}