module com.example.proyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.proyecto to javafx.fxml;
    exports com.example.proyecto;
}