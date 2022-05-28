package com.seabattle.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

/**
 * This class creates an instance of the main window, that is, the main stage on which the sub-elements will be placed.
 */
public class Application extends javafx.application.Application {
    /**
     * Method that starts the application
     * @param stage the stage on which all elements will be placed
     * @throws IOException error handling if file path not found
     */
    @Override
    public void start(Stage stage) throws IOException {
        Image icon = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/icon-ship.png")).toExternalForm());

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("resource/fxml/menuWindow-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 420);
        stage.setTitle("SeaBattle");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}