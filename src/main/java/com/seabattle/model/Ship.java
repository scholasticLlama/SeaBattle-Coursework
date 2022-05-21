package com.seabattle.model;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.IOException;

public class Ship {
    public static void placeMyShip(byte[] myField, FileInputStream file, GridPane myFieldGrid, Image[] images) throws IOException {
        myField = file.readAllBytes();
        int countRow = 0;
        for (int i = 0; i < myField.length; i++) {
            Label label = new Label();
            label.setPrefSize(30, 30);
            if (myField[i] == 1) {
                if (i + 1 < myField.length && myField[i + 1] == 1) {
                    if (i + 2 < myField.length && myField[i + 2] == 1) {
                        if (i + 3 < myField.length && myField[i + 3] == 1) {
                            label.setGraphic(new ImageView(images[3]));
                        } else label.setGraphic(new ImageView(images[2]));
                    } else label.setGraphic(new ImageView(images[1]));
                } else label.setGraphic(new ImageView(images[0]));
                myFieldGrid.add(label,9 - ((countRow + 1) * 10 - (i + 1)), countRow);
            }
            if ((i + 1) % 10 == 0) {
                countRow++;
            }
        }
    }
}
