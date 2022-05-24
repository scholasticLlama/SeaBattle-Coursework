package com.seabattle.view;

import com.seabattle.model.DragAndDropShip;
import com.seabattle.model.GridPaneControl;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashMap;

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

    public static void resetShips(Label button, GridPane gridPane, HashMap<Integer, ImageView[]> ships, Image[] images, ImageView emptyImage, Label[][] cell) {
        button.setOnMouseClicked(event -> {
            for (int i = 0; i < gridPane.getRowCount(); i++) {
                for (int j = 0; j < gridPane.getColumnCount(); j++) {
                    Label label = (Label) GridPaneControl.getNodeFromGridPane(gridPane, j, i);
                    assert label != null;
                    label.setGraphic(null);
                    label.setText(null);
                }
            }
            for (int i = 0; i < 4; i++) {
                ImageView[] imageViewsFromMap = ships.get(i + 1);
                for (ImageView imageView : imageViewsFromMap) {
                    imageView.setImage(getShipImage(i + 1, images));
                }
            }
            DragAndDropShip.startDragAndDrop(ships, cell, emptyImage);
        });
    }

    public static void dragWindow(AnchorPane menuBar, Label closeWindowButton, Label minimizeWindowButton, Label funcButton) {
        menuBar.setOnMousePressed(event -> {
            xOffset = event.getX();
            yOffset = event.getY();
            isPossibleToDrag = !(closeWindowButton.isHover() | minimizeWindowButton.isHover() | funcButton.isHover());
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

    private static Image getShipImage(int numberOfDesks, Image[] images) {
        return switch (numberOfDesks) {
            case 1 -> images[0];
            case 2 -> images[1];
            case 3 -> images[2];
            case 4 -> images[3];
            default -> null;
        };
    }
}
