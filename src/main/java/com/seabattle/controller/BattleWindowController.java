package com.seabattle.controller;

import com.seabattle.model.RandomSetting;
import com.seabattle.model.Ship;
import com.seabattle.view.Application;
import com.seabattle.view.Audio;
import com.seabattle.view.WindowControlManager;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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
import java.util.Objects;

public class BattleWindowController {

    private final boolean[] turn = new boolean[]{true, false};
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
    private Label[][] enemyShipsLabel = new Label[10][10];
    private int[][] enemyShips = new int[10][10];
    private int[][] myField = new int[10][10];
    private int myShipCount;
    private int enemyShipCount;

    @FXML
    void initialize() throws IOException, URISyntaxException {
        myShipCount = 10;
        enemyShipCount = 10;
        setEnemyShips();

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

        Ship.getMyShip(myField, file, myFieldGrid, images);

        enemyField();

        WindowControlManager.closeWindow(closeWindowButton);
        WindowControlManager.minimizeWindow(minimizeWindowButton);
        WindowControlManager.dragWindow(menuBar, closeWindowButton, minimizeWindowButton, homeWindowButton);

    }

    private void setEnemyShips() {
        RandomSetting randomSetting = new RandomSetting();
        randomSetting.fillArraysWithDefault();
        do {
            randomSetting.setShips();
        } while (randomSetting.ships.size() > 0 && !randomSetting.isFull);
        enemyShips = randomSetting.field;
        for (int i = 0; i < enemyShipsLabel.length; i++) {
            for (int j = 0; j < enemyShipsLabel[i].length; j++) {
                enemyShipsLabel[i][j] = new Label();
                enemyShipsLabel[i][j].setPrefSize(30, 30);
                enemyShipsLabel[i][j].setAlignment(Pos.CENTER);
                enemyShipsLabel[i][j].setOpacity(0);
                enemyShipsLabel[i][j].setText(String.valueOf(enemyShips[i][j]));
            }
        }
        enemyShipsLabel = addPosition(enemyShipsLabel, enemyShips);
    }

    Label[][] addPosition(Label[][] enemyShipsLabel, int[][] enemyShips) {
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
        return enemyShipsLabel;
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
        Audio emptyCellAudio = new Audio(String.valueOf(getAudioPath("resource/sound/EmptyCell.wav")));
        Audio hitShipAudio = new Audio(String.valueOf(getAudioPath("resource/sound/HitShip.wav")));
        Audio brokenShipAudio = new Audio(String.valueOf(getAudioPath("resource/sound/BrokenShip.wav")));
        for (int i = 0; i < enemyFieldGrid.getRowCount(); i++) {
            for (int j = 0; j < enemyFieldGrid.getColumnCount(); j++) {
                enemyShipsLabel[i][j].setOnMouseClicked(event -> {
                    if (turn[0]) {
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
                            turn[1] = true;
                        } else if (Objects.equals(labelValue, "0")) {
                            emptyCellAudio.sound();
                            label.setText(null);
                            label.setGraphic(new ImageView(emptyCell));
                            label.setOpacity(1);
                            turn[0] = false;
                            turn[1] = true;
                        } else if (Objects.equals(labelValue, "1")) {
                            brokenShipAudio.sound();
                            label.setText(null);
                            label.setGraphic(new ImageView(oneDeskHit));
                            label.setOpacity(1);
                            turn[0] = true;
                            turn[1] = false;
                            //setEmptyImage(row, column, Integer.parseInt(labelValue), enemyShipsLabel, emptyCell);
                        } else if (Objects.equals(labelValue, "2")) {
                            if (isBroken(label.getText(), enemyShipsLabel)) {
                                brokenShipAudio.sound();
                                enemyShipsLabel[row][column + 1].setGraphic(null);
                                enemyShipsLabel[row][column].setGraphic(new ImageView(twoDeskHit));
                                setEmptyImage(row, column, Integer.parseInt(labelValue), enemyShipsLabel, emptyCell);
                            } else {
                                hitShipAudio.sound();
                                label.setGraphic(new ImageView(hitShip));
                            }
                            label.setText(null);
                            label.setOpacity(1);
                            turn[0] = true;
                            turn[1] = false;
                        } else if (Objects.equals(labelValue, "3")) {
                            if (isBroken(label.getText(), enemyShipsLabel)) {
                                brokenShipAudio.sound();
                                enemyShipsLabel[row][column + 1].setGraphic(null);
                                enemyShipsLabel[row][column + 2].setGraphic(null);
                                enemyShipsLabel[row][column].setGraphic(new ImageView(threeDeskHit));
                                setEmptyImage(row, column, Integer.parseInt(labelValue), enemyShipsLabel, emptyCell);
                            } else {
                                hitShipAudio.sound();
                                label.setGraphic(new ImageView(hitShip));
                            }
                            label.setText(null);
                            label.setOpacity(1);
                            turn[0] = true;
                            turn[1] = false;
                        } else if (Objects.equals(labelValue, "4")) {
                            if (isBroken(label.getText(), enemyShipsLabel)) {
                                brokenShipAudio.sound();
                                enemyShipsLabel[row][column + 1].setGraphic(null);
                                enemyShipsLabel[row][column + 2].setGraphic(null);
                                enemyShipsLabel[row][column + 3].setGraphic(null);
                                enemyShipsLabel[row][column].setGraphic(new ImageView(fourDeskHit));
                                setEmptyImage(row, column, Integer.parseInt(labelValue), enemyShipsLabel, emptyCell);
                            } else {
                                hitShipAudio.sound();
                                label.setGraphic(new ImageView(hitShip));
                            }
                            label.setText(null);
                            label.setOpacity(1);
                            turn[0] = true;
                            turn[1] = false;
                        }
                    }

                    if (turn[0]) {
                        whoseMoveImage.setImage(myMoveArrowImage);
                    } else {
                        whoseMoveImage.setImage(enemyMoveArrowImage);
                    }
                });

                enemyFieldGrid.add(enemyShipsLabel[i][j], j, i);
            }
        }
    }

    private boolean isBroken(String stringShipInfo, Label[][] enemyShipsLabel) {
        String[] shipInfo = stringShipInfo.split(",");
        int counter = 1;
        int row = Integer.parseInt(shipInfo[1]);
        for (int j = 2; j < shipInfo.length; j++) {
            if (enemyShipsLabel[row][Integer.parseInt(shipInfo[j])].getGraphic() != null) {
                counter++;
            }
        }
        return counter == Integer.parseInt(shipInfo[0]);
    }

    private Path getAudioPath(String name) throws URISyntaxException {
        URL url = Application.class.getResource(name);
        return Paths.get(Objects.requireNonNull(url).toURI());
    }

    private void setEmptyImageLeft(int row, int column, Label[][] enemyShipsLabel, Image emptyCell) {
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

    private void setEmptyImageRight(int row, int column, int length, Label[][] enemyShipsLabel, Image emptyCell) {
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

    private void setEmptyImageUnder(int row, int column, int length, Label[][] enemyShipsLabel, Image emptyCell) {
        if (row < 9) {
            for (int i = 0; i < length; i++) {
                enemyShipsLabel[row + 1][column + i].setText(null);
                enemyShipsLabel[row + 1][column + i].setGraphic(new ImageView(emptyCell));
                enemyShipsLabel[row + 1][column + i].setOpacity(1);
            }
        }
    }

    private void setEmptyImageOver(int row, int column, int length, Label[][] enemyShipsLabel, Image emptyCell) {
        if (row > 0) {
            for (int i = 0; i < length; i++) {
                enemyShipsLabel[row - 1][column + i].setText(null);
                enemyShipsLabel[row - 1][column + i].setGraphic(new ImageView(emptyCell));
                enemyShipsLabel[row - 1][column + i].setOpacity(1);
            }
        }
    }

    private void setEmptyImage(int row, int column, int length, Label[][] enemyShipsLabel, Image emptyCell) {
        setEmptyImageLeft(row, column, enemyShipsLabel, emptyCell);
        setEmptyImageRight(row, column, length, enemyShipsLabel, emptyCell);
        setEmptyImageUnder(row, column, length, enemyShipsLabel, emptyCell);
        setEmptyImageOver(row, column, length, enemyShipsLabel, emptyCell);
    }

}
