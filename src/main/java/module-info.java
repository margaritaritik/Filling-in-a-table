module com.example.tablefx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tablefx to javafx.fxml;
    exports com.example.tablefx;
}