package com.seabattle.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class EditPosition {
    public static void addPosition(Label[][] enemyShipsLabel, int[][] enemyShips) {
        for (int i = 0; i < enemyShips.length; i++) {
            int counter = 0;
            int index = 0;
            int element = enemyShips[i][0];
            for (int j = 0; j < enemyShips[i].length; j++) {
                if (enemyShips[i][j] == element) {
                    counter++;
                    index = j;
                }

                if (enemyShips[i][j] != element || j == 9) {
                    if (counter == element && element > 1) {
                        StringBuilder position = new StringBuilder(element + "," + i + ",");
                        for (int k = index - counter + 1; k <= index; k++) {
                            position.append(k).append(",");
                        }
                        position.deleteCharAt(position.length() - 1);
                        for (int k = index - counter + 1; k <= index; k++) {
                            enemyShipsLabel[i][k] = new Label();
                            enemyShipsLabel[i][k].setPrefSize(30, 30);
                            enemyShipsLabel[i][k].setAlignment(Pos.CENTER);
                            enemyShipsLabel[i][k].setOpacity(0);
                            enemyShipsLabel[i][k].setText(String.valueOf(position));
                        }
                    }
                    element = enemyShips[i][j];
                    counter = 1;
                }
            }
        }
    }
}
