package com.seabattle.model;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Ship {
    public static void placeMyShip(int[][] myField, File file, GridPane myFieldGrid, Image[] images) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String stringArray = bufferedReader.readLine();
        bufferedReader.close();
        fileReader.close();
        int index = 0;
        int shift = 0;
        char element;
        for (int n = 0; n < myField.length; n++) {
            for (int m = 0; m < myField[n].length; m++) {
                element = stringArray.charAt(index);
                myField[n][m] = Integer.parseInt(String.valueOf(element));
                index++;
            }
        }
        for (int i = 0; i < myField.length; i++) {
            for (int j = 0; j < myField[i].length; j = shift) {
                if (myField[i][j] > 0) {
                    Label label = new Label();
                    label.setPrefSize(30, 30);
                    label.setGraphic(new ImageView(getShipImage(myField[i][j], images)));
                    myFieldGrid.add(label, j, i);
                    shift += myField[i][j];
                } else shift++;
            }
            shift = 0;
        }
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
