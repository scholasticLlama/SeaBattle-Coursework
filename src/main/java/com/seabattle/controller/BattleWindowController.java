package com.seabattle.controller;

import com.seabattle.model.Ship;
import com.seabattle.view.Application;
import com.seabattle.view.WindowControlManager;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.ResourceBundle;

public class BattleWindowController {

    @FXML
    private Label closeWindowButton;

    @FXML
    private GridPane enemyFieldGrid;

    @FXML
    private Label homeWindowButton;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane menuBar;

    @FXML
    private Label minimizeWindowButton;

    @FXML
    private GridPane myFieldGrid;

    @FXML
    private ImageView whoseMoveImage;

    private byte[] myField;
    private Image[] images;
    private final Label[][] cell = new Label[10][10];

    @FXML
    void initialize() throws IOException, URISyntaxException {
        Image oneShipImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_1x1_H.png")).toExternalForm());
        Image twoShipImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_2x1_H.png")).toExternalForm());
        Image threeShipImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_3x1_H.png")).toExternalForm());
        Image fourShipImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_4x1_H.png")).toExternalForm());
        images = new Image[] {oneShipImage, twoShipImage, threeShipImage, fourShipImage};

        URL url = Application.class.getResource("resource/file/fieldInArray");
        Path path = Paths.get(Objects.requireNonNull(url).toURI());
        FileInputStream file = new FileInputStream(String.valueOf(path));

        Ship.placeMyShip(myField, file, myFieldGrid, images);
        enemyField();

        WindowControlManager.closeWindow(closeWindowButton);
        WindowControlManager.minimizeWindow(minimizeWindowButton);
        WindowControlManager.dragWindow(menuBar, closeWindowButton, minimizeWindowButton);

    }

    void enemyField() {
        Image hitShip = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/HitCell.png")).toExternalForm());
        Image emptyCell = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/EmptyCell.png")).toExternalForm());
        for (int i = 0; i < enemyFieldGrid.getRowCount(); i++) {
            for (int j = 0; j < enemyFieldGrid.getColumnCount(); j++) {
                cell[i][j] = new Label();
                cell[i][j].setPrefSize(30,30);
                cell[i][j].setAlignment(Pos.CENTER);
                cell[i][j].setOpacity(0);
                if (i % 2 == 0) {
                    cell[i][j].setText("1");
                } else cell[i][j].setText("0");

                cell[i][j].setOnMouseClicked(event -> {
                    Label label = (Label) event.getSource();
                    if (Objects.equals(label.getText(), "1")) {
                        label.setText(null);
                        label.setGraphic(new ImageView(hitShip));
                        label.setOpacity(1);
                    } else if (Objects.equals(label.getText(), "0")) {
                        label.setText(null);
                        label.setGraphic(new ImageView(emptyCell));
                        label.setOpacity(1);
                    }
                });

                enemyFieldGrid.add(cell[i][j], j, i);
            }
        }
    }

}
