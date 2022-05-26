package com.seabattle.model;

import javafx.scene.control.Label;

public class EditPosition {
    public static void addPosition(Label[][] enemyShipsLabel, int[][] enemyShips) {
        for (int i = 0; i < enemyShips.length; i++) {
            int counter = 1;
            int index = 0;
            int endIndex;
            int element = enemyShips[i][0];
            for (int j = 1; j < enemyShips[i].length; j++) {
                if (enemyShips[i][j] == element || element == 1) {
                    if (element > 1) counter++;
                    index = j;
                }
                if (enemyShips[i][j] != element || j == 9) {
                    if (counter == element && element > 0) {
                        endIndex = index;
                        StringBuilder position = new StringBuilder(element + "," + i + ",");
                        if (element == 1) {
                            position.append(index - 1);
                            counter++;
                        } else {
                            for (int k = index - counter + 1; k <= index; k++) {
                                position.append(k).append(",");
                            }
                            position.deleteCharAt(position.length() - 1);
                            endIndex++;
                        }
                        for (int k = index - counter + 1; k < endIndex; k++) {
                            enemyShipsLabel[i][k] = new Label();
                            enemyShipsLabel[i][k].setPrefSize(30, 30);
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
