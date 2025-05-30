module com.example.julia {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.julia to javafx.fxml;
    exports com.example.julia;
}