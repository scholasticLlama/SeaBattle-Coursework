package com.seabattle.view;

import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class WindowControlManager {
    private static double x;
    private static double y;
    private static double xOffset;
    private static double yOffset;
    private static boolean isPossibleToDrag;

    public static void closeWindow(Label button) {
        button.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                Stage stage = (Stage) button.getScene().getWindow();
                stage.close();
            }
        });
    }

    public static void minimizeWindow(Label button) {
        button.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                Stage stage = (Stage) button.getScene().getWindow();
                stage.setIconified(true);
            }
        });
    }

    public static void dragWindow(AnchorPane menuBar, Label closeWindowButton, Label minimizeWindowButton) {
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
