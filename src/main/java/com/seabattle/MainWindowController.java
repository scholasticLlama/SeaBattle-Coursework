package com.seabattle;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label closeWindowButton;

    @FXML
    private GridPane enemyField;

    @FXML
    private Label homeWindowButton;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane menuBar;

    @FXML
    private Label minimizeWindowButton;

    @FXML
    private GridPane myField;

    @FXML
    private ImageView whoseMoveImage;

    private double x;
    private double y;
    private double xOffset;
    private  double yOffset;
    private boolean isPossibleToDrag;

    @FXML
    void initialize() {
        // close window
        closeWindowButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                Stage stage = (Stage) closeWindowButton.getScene().getWindow();
                stage.close();
            }
        });

        // minimize window
        minimizeWindowButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                Stage stage = (Stage) minimizeWindowButton.getScene().getWindow();
                stage.setIconified(true);
            }
        });

        // window dragging
        menuBar.setOnMousePressed(event -> {
            xOffset = event.getX();
            yOffset = event.getY();
            isPossibleToDrag = !(closeWindowButton.isHover() | minimizeWindowButton.isHover());
        });

        menuBar.setOnMouseDragged(event -> {
            x = event.getScreenX();
            y = event.getScreenY();
            if (event.getButton() == MouseButton.PRIMARY && isPossibleToDrag) {
                Stage stage = (Stage) menuBar.getScene().getWindow();
                stage.setX(x - xOffset);
                stage.setY(y - yOffset);
            }
        });
    }

}
