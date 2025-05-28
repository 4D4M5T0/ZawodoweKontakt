module com.example.aplikacjazawodowekon {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.aplikacjazawodowekon to javafx.fxml;
    exports com.example.aplikacjazawodowekon;
}