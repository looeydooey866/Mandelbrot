module com.example.mandelbrot {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mandelbrot to javafx.fxml;
    exports com.example.mandelbrot;
}