module com.seabattle {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.seabattle to javafx.fxml;
    exports com.seabattle;
}