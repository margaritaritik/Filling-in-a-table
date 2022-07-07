module com.example.tablefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.tablefx to javafx.fxml;
    exports com.example.tablefx;
}