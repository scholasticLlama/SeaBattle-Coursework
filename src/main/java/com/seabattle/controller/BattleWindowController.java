package com.seabattle.controller;

import com.seabattle.model.*;
import com.seabattle.view.Application;
import com.seabattle.view.Audio;
import com.seabattle.view.WindowControlManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class BattleWindowController {

    @FXML
    private Label closeWindowButton;

    @FXML
    private GridPane enemyFieldGrid;

    @FXML
    private Label homeWindowButton;

    @FXML
    private AnchorPane menuBar;

    @FXML
    private Label minimizeWindowButton;

    @FXML
    private GridPane myFieldGrid;

    @FXML
    private ImageView whoseMoveImage;

    private final boolean[] turn = new boolean[] {true};
    private final Label[][] enemyShipsLabel = new Label[10][10];
    private final Label[][] myShipsLabel = new Label[10][10];
    private final int[][] myField = new int[10][10];

    @FXML
    void initialize() throws IOException, URISyntaxException {
        Image oneShipImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_1x1_H.png")).toExternalForm());
        Image twoShipImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_2x1_H.png")).toExternalForm());
        Image threeShipImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_3x1_H.png")).toExternalForm());
        Image fourShipImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_4x1_H.png")).toExternalForm());
        Image myMoveArrowImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/MyMoveArrow.png")).toExternalForm());
        whoseMoveImage.setImage(myMoveArrowImage);

        Image[] images = new Image[] {oneShipImage, twoShipImage, threeShipImage, fourShipImage};

        URL url = Application.class.getResource("resource/file/fieldInArray");
        Path path = Paths.get(Objects.requireNonNull(url).toURI());
        File file = new File(String.valueOf(path));

        Ship.getMyShips(myField, file, myFieldGrid, images);
        SetShips.setEnemyShips(enemyShipsLabel);

        enemyField();

        WindowControlManager.closeWindow(closeWindowButton);
        WindowControlManager.minimizeWindow(minimizeWindowButton);
        WindowControlManager.openNewWindowEvent(homeWindowButton, "resource/fxml/menuWindow-view.fxml");
        WindowControlManager.dragWindow(menuBar, closeWindowButton, minimizeWindowButton, homeWindowButton);

    }

    void enemyField() throws URISyntaxException {
        Image hitShip = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/HitCell.png")).toExternalForm());
        Image emptyCell = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/EmptyCell.png")).toExternalForm());
        Image oneDeskHit = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_1x1_H_B.png")).toExternalForm());
        Image twoDeskHit = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_2x1_H_B.png")).toExternalForm());
        Image threeDeskHit = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_3x1_H_B.png")).toExternalForm());
        Image fourDeskHit = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_4x1_H_B.png")).toExternalForm());
        Image myMoveArrowImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/MyMoveArrow.png")).toExternalForm());
        Image enemyMoveArrowImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/EnemyMoveArrow.png")).toExternalForm());
        Audio emptyCellAudio = new Audio(String.valueOf(getFilePath("resource/sound/EmptyCell.wav")));
        Audio hitShipAudio = new Audio(String.valueOf(getFilePath("resource/sound/HitShip.wav")));
        Audio brokenShipAudio = new Audio(String.valueOf(getFilePath("resource/sound/BrokenShip.wav")));
        AI ai = new AI(myField);
        for (int i = 0; i < enemyFieldGrid.getRowCount(); i++) {
            for (int j = 0; j < enemyFieldGrid.getColumnCount(); j++) {
                Label labelGraphic = enemyShipsLabel[i][j];
                enemyShipsLabel[i][j].setOnMouseClicked(event -> {
                    if (turn[0] && labelGraphic.getGraphic() == null) {
                        Label label = (Label) event.getSource();
                        String labelValue = label.getText();
                        int row = 0;
                        int column = 0;
                        if (label.getText().contains(",")) {
                            String[] stringShipInfo = label.getText().split(",");
                            labelValue = stringShipInfo[0];
                            row = Integer.parseInt(stringShipInfo[1]);
                            column = Integer.parseInt(stringShipInfo[2]);
                        }
                        if (Objects.equals(labelValue, "-1")) {
                            emptyCellAudio.sound();
                            label.setText(null);
                            label.setGraphic(new ImageView(emptyCell));
                            label.setOpacity(1);
                            turn[0] = false;
                        } else if (Objects.equals(labelValue, "0")) {
                            emptyCellAudio.sound();
                            label.setText(null);
                            label.setGraphic(new ImageView(emptyCell));
                            label.setOpacity(1);
                            turn[0] = false;
                        } else if (Objects.equals(labelValue, "1")) {
                            brokenShipAudio.sound();
                            label.setText(null);
                            label.setGraphic(new ImageView(oneDeskHit));
                            label.setOpacity(1);
                            GridPaneControl.setEmptyImage(row, column, Integer.parseInt(labelValue), enemyShipsLabel, emptyCell);
                            turn[0] = true;
                        } else if (Objects.equals(labelValue, "2")) {
                            if (Ship.isBroken(label.getText(), enemyShipsLabel)) {
                                brokenShipAudio.sound();
                                enemyShipsLabel[row][column + 1].setGraphic(null);
                                enemyShipsLabel[row][column].setGraphic(new ImageView(twoDeskHit));
                                GridPaneControl.setEmptyImage(row, column, Integer.parseInt(labelValue), enemyShipsLabel, emptyCell);
                            } else {
                                hitShipAudio.sound();
                                label.setGraphic(new ImageView(hitShip));
                            }
                            label.setText(null);
                            label.setOpacity(1);
                            turn[0] = true;
                        } else if (Objects.equals(labelValue, "3")) {
                            if (Ship.isBroken(label.getText(), enemyShipsLabel)) {
                                brokenShipAudio.sound();
                                enemyShipsLabel[row][column + 1].setGraphic(null);
                                enemyShipsLabel[row][column + 2].setGraphic(null);
                                enemyShipsLabel[row][column].setGraphic(new ImageView(threeDeskHit));
                                GridPaneControl.setEmptyImage(row, column, Integer.parseInt(labelValue), enemyShipsLabel, emptyCell);
                            } else {
                                hitShipAudio.sound();
                                label.setGraphic(new ImageView(hitShip));
                            }
                            label.setText(null);
                            label.setOpacity(1);
                            turn[0] = true;
                        } else if (Objects.equals(labelValue, "4")) {
                            if (Ship.isBroken(label.getText(), enemyShipsLabel)) {
                                brokenShipAudio.sound();
                                enemyShipsLabel[row][column + 1].setGraphic(null);
                                enemyShipsLabel[row][column + 2].setGraphic(null);
                                enemyShipsLabel[row][column + 3].setGraphic(null);
                                enemyShipsLabel[row][column].setGraphic(new ImageView(fourDeskHit));
                                GridPaneControl.setEmptyImage(row, column, Integer.parseInt(labelValue), enemyShipsLabel, emptyCell);
                            } else {
                                hitShipAudio.sound();
                                label.setGraphic(new ImageView(hitShip));
                            }
                            label.setText(null);
                            label.setOpacity(1);
                            turn[0] = true;
                        }
                    }
                    if (turn[0]) {
                        whoseMoveImage.setImage(myMoveArrowImage);
                    } else {
                        whoseMoveImage.setImage(enemyMoveArrowImage);
                        ai.newShot();
                        aiShotSetImage(ai.shots, ai.brokenShips);
                        ai.shots.clear();
                        turn[0] = true;
                    }
                });

                enemyFieldGrid.add(enemyShipsLabel[i][j], j, i);
            }
        }
    }

    private void aiShotSetImage(ArrayList<String> shots, ArrayList<String> brokenShips) {
        Image hitShip = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/HitCell.png")).toExternalForm());
        Image emptyCell = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/EmptyCell.png")).toExternalForm());
        for (String shot : shots) {
            int row = Integer.parseInt(shot) / 10;
            int column = Integer.parseInt(shot) - row * 10;
            Label label = new Label();
            label.setPrefSize(30, 30);
            if (myField[row][column] > 0) {
                label.setGraphic(new ImageView(hitShip));
            } else {
                label.setGraphic(new ImageView(emptyCell));
            }
            myFieldGrid.add(label, column, row);
        }
        if (brokenShips.size() > 0) {
            for (String brokenShip : brokenShips) {
                String[] brokenShipInfoArray = brokenShip.split(",");
                int length = Integer.parseInt(brokenShipInfoArray[0]);
                int row = Integer.parseInt(brokenShipInfoArray[1]);
                int column = Integer.parseInt(brokenShipInfoArray[2]);
                GridPaneControl.clearGraphicInArray(myShipsLabel);
                GridPaneControl.setEmptyImage(row, column, length, myShipsLabel, emptyCell);
                GridPaneControl.setLabelsToGridPane(myFieldGrid, myShipsLabel);
            }
        }
    }


    private Path getFilePath(String name) throws URISyntaxException {
        URL url = Application.class.getResource(name);
        return Paths.get(Objects.requireNonNull(url).toURI());
    }


}
