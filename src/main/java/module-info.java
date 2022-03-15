module com.seabattle {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.seabattle to javafx.fxml;
    exports com.seabattle;
}