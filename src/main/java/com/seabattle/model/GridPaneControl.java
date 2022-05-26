package com.seabattle.model;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class GridPaneControl {
    public static Node getNodeFromGridPane(GridPane gridPane, int column, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == column && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    public static void setEmptyImageLeft(int row, int column, Label[][] enemyShipsLabel, Image emptyCell) {
        if (column > 0) {
            for (int i = 0; i < 3; i++) {
                if (!((i == 0 && row == 0) || (i == 2 && row == 9))) {
                    enemyShipsLabel[row + i - 1][column - 1].setText(null);
                    enemyShipsLabel[row + i - 1][column - 1].setGraphic(new ImageView(emptyCell));
                    enemyShipsLabel[row + i - 1][column - 1].setOpacity(1);
                }
            }
        }
    }

    public static void setEmptyImageRight(int row, int column, int length, Label[][] enemyShipsLabel, Image emptyCell) {
        if (column + length <= 9) {
            for (int i = 0; i < 3; i++) {
                if (!((i == 0 && row == 0) || (i == 2 && row == 9))) {
                    enemyShipsLabel[row + i - 1][column + length].setText(null);
                    enemyShipsLabel[row + i - 1][column + length].setGraphic(new ImageView(emptyCell));
                    enemyShipsLabel[row + i - 1][column + length].setOpacity(1);
                }
            }
        }
    }

    public static void setEmptyImageUnder(int row, int column, int length, Label[][] enemyShipsLabel, Image emptyCell) {
        if (row < 9) {
            for (int i = 0; i < length; i++) {
                enemyShipsLabel[row + 1][column + i].setText(null);
                enemyShipsLabel[row + 1][column + i].setGraphic(new ImageView(emptyCell));
                enemyShipsLabel[row + 1][column + i].setOpacity(1);
            }
        }
    }

    public static void setEmptyImageOver(int row, int column, int length, Label[][] enemyShipsLabel, Image emptyCell) {
        if (row > 0) {
            for (int i = 0; i < length; i++) {
                enemyShipsLabel[row - 1][column + i].setText(null);
                enemyShipsLabel[row - 1][column + i].setGraphic(new ImageView(emptyCell));
                enemyShipsLabel[row - 1][column + i].setOpacity(1);
            }
        }
    }

    public static void setEmptyImage(int row, int column, int length, Label[][] enemyShipsLabel, Image emptyCell) {
        setEmptyImageLeft(row, column, enemyShipsLabel, emptyCell);
        setEmptyImageRight(row, column, length, enemyShipsLabel, emptyCell);
        setEmptyImageUnder(row, column, length, enemyShipsLabel, emptyCell);
        setEmptyImageOver(row, column, length, enemyShipsLabel, emptyCell);
    }

}
