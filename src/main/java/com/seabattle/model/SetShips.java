package com.seabattle.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class SetShips {
    public static void setEnemyShips(Label[][] enemyShipsLabel) {
        RandomSetting randomSetting = new RandomSetting();
        randomSetting.fillArraysWithDefault();
        do {
            randomSetting.setShips();
        } while (randomSetting.ships.size() > 0 && !randomSetting.isFull);
        int[][] enemyShips = randomSetting.field;
        for (int i = 0; i < enemyShipsLabel.length; i++) {
            for (int j = 0; j < enemyShipsLabel[i].length; j++) {
                enemyShipsLabel[i][j] = new Label();
                enemyShipsLabel[i][j].setPrefSize(30, 30);
                enemyShipsLabel[i][j].setAlignment(Pos.CENTER);
                enemyShipsLabel[i][j].setOpacity(0);
                enemyShipsLabel[i][j].setText(String.valueOf(enemyShips[i][j]));
            }
        }
        EditPosition.addPosition(enemyShipsLabel, enemyShips);
    }

    public static void setMyShips(int[][] field, GridPane gridPane, Image[] images) {
        int shift = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                Label label = new Label();
                label.setPrefSize(30, 30);
                if (field[i][j] > 0 && shift < 1) {
                    label.setGraphic(new ImageView(Ship.getShipImage(field[i][j], images)));
                    shift = field[i][j];
                }
                if (shift > 0) {
                    shift--;
                }
                gridPane.add(label, j, i);
            }
            shift = 0;
        }
    }
}
