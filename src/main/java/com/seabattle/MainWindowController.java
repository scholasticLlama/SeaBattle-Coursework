package com.seabattle;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class MainWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane enemyField;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private HBox menuBar;

    @FXML
    private GridPane myField;

    @FXML
    private ImageView whoseMoveImage;

    @FXML
    void initialize() {
    }

}
