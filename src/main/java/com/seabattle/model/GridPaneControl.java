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

    public static void setEmptyImage(int row, int column, int length, Label[][] shipsLabel, Image emptyCell) {
        setEmptyImageLeft(row, column, shipsLabel, emptyCell);
        setEmptyImageRight(row, column, length, shipsLabel, emptyCell);
        setEmptyImageUnder(row, column, length, shipsLabel, emptyCell);
        setEmptyImageOver(row, column, length, shipsLabel, emptyCell);
    }

    public static void clearGraphicInArray(Label[][] labels) {
        for (int i = 0; i < labels.length; i++) {
            for (int j = 0; j < labels[i].length; j++) {
                Label newLabel = new Label();
                newLabel.setPrefSize(30, 30);
                newLabel.setGraphic(null);
                labels[i][j] = newLabel;
            }
        }
    }

    public static void setLabelsToGridPane(GridPane gridPane, Label[][] labels) {
        for (int i = 0; i < labels.length; i++) {
            for (int j = 0; j < labels[i].length; j++) {
                if (labels[i][j].getGraphic() != null) {
                    Label label = (Label) getNodeFromGridPane(gridPane, j, i);
                    assert label != null;
                    label.setGraphic(labels[i][j].getGraphic());
                }
            }
        }
    }

    private static void setEmptyImageLeft(int row, int column, Label[][] shipsLabel, Image emptyCell) {
        if (column > 0) {
            for (int i = 0; i < 3; i++) {
                if (!((i == 0 && row == 0) || (i == 2 && row == 9))) {
                    shipsLabel[row + i - 1][column - 1].setText(null);
                    shipsLabel[row + i - 1][column - 1].setGraphic(new ImageView(emptyCell));
                    shipsLabel[row + i - 1][column - 1].setOpacity(1);
                }
            }
        }
    }

    private static void setEmptyImageRight(int row, int column, int length, Label[][] shipsLabel, Image emptyCell) {
        if (column + length <= 9) {
            for (int i = 0; i < 3; i++) {
                if (!((i == 0 && row == 0) || (i == 2 && row == 9))) {
                    shipsLabel[row + i - 1][column + length].setText(null);
                    shipsLabel[row + i - 1][column + length].setGraphic(new ImageView(emptyCell));
                    shipsLabel[row + i - 1][column + length].setOpacity(1);
                }
            }
        }
    }

    private static void setEmptyImageUnder(int row, int column, int length, Label[][] shipsLabel, Image emptyCell) {
        if (row < 9) {
            for (int i = 0; i < length; i++) {
                shipsLabel[row + 1][column + i].setText(null);
                shipsLabel[row + 1][column + i].setGraphic(new ImageView(emptyCell));
                shipsLabel[row + 1][column + i].setOpacity(1);
            }
        }
    }

    private static void setEmptyImageOver(int row, int column, int length, Label[][] shipsLabel, Image emptyCell) {
        if (row > 0) {
            for (int i = 0; i < length; i++) {
                shipsLabel[row - 1][column + i].setText(null);
                shipsLabel[row - 1][column + i].setGraphic(new ImageView(emptyCell));
                shipsLabel[row - 1][column + i].setOpacity(1);
            }
        }
    }

}
