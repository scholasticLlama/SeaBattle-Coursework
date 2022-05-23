package com.seabattle.model;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.HashMap;

public class CreateField {
    public Label[][] cell;
    public HashMap<Integer, ImageView[]> ships;
    GridPane myField;
    ImageView[] imageViews;

    public CreateField(GridPane myField, ImageView[] imageViews) {
        this.myField = myField;
        this.imageViews = imageViews;
    }

    public void createField() {
        for (int i = 0; i < myField.getRowCount(); i++) {
            for (int j = 0; j < myField.getColumnCount(); j++) {
                cell[i][j] = new Label();
                cell[i][j].setPrefSize(30,30);
//                myField.add(cell[i][j], j, i);
            }
        }
        ships = new HashMap<>();
        ships.put(1, new ImageView[] {imageViews[0], imageViews[1], imageViews[2], imageViews[3]});
        ships.put(2, new ImageView[] {imageViews[4], imageViews[5], imageViews[6]});
        ships.put(3, new ImageView[] {imageViews[7], imageViews[8]});
        ships.put(4, new ImageView[] {imageViews[9]});
    }
}
