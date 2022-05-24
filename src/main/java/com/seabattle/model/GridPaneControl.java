package com.seabattle.model;

import javafx.scene.Node;
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
}
