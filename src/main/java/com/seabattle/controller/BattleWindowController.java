package com.seabattle.controller;

import com.seabattle.model.AI;
import com.seabattle.model.GridPaneControl;
import com.seabattle.model.SetShips;
import com.seabattle.model.Ship;
import com.seabattle.view.Application;
import com.seabattle.view.Audio;
import com.seabattle.view.ResultWindow;
import com.seabattle.view.WindowControlManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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

    private AI ai;
    Thread thread;
    String threadId = "null";
    private int enemyShipsLeft;
    private long startTime;
    private int amountOfShot = 0;
    private final boolean[] turn = new boolean[]{true};
    private final Label[][] enemyShipsLabel = new Label[10][10];
    private final Label[][] myShipsLabel = new Label[10][10];
    private final int[][] myField = new int[10][10];

    @FXML
    void initialize() throws IOException, URISyntaxException {
        Image oneShipImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_1x1_H.png")).toExternalForm());
        Image twoShipImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_2x1_H.png")).toExternalForm());
        Image threeShipImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_3x1_H.png")).toExternalForm());
        Image fourShipImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_4x1_H.png")).toExternalForm());
        setMoveImage(turn[0]);

        Image[] images = new Image[]{oneShipImage, twoShipImage, threeShipImage, fourShipImage};

        URL url = Application.class.getResource("resource/file/fieldInArray");
        Path path = Paths.get(Objects.requireNonNull(url).toURI());
        File file = new File(String.valueOf(path));

        Ship.getMyShips(myField, file, myFieldGrid, images);
        SetShips.setEnemyShips(enemyShipsLabel);
        enemyShipsLeft = 10;

        ai = new AI(myField);
        enemyField();
        startTime = System.currentTimeMillis();

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
        Audio emptyCellAudio = new Audio(String.valueOf(getFilePath("resource/sound/EmptyCell.wav")));
        Audio hitShipAudio = new Audio(String.valueOf(getFilePath("resource/sound/HitShip.wav")));
        Audio brokenShipAudio = new Audio(String.valueOf(getFilePath("resource/sound/BrokenShip.wav")));
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
                            amountOfShot++;
                            emptyCellAudio.sound();
                            label.setText(null);
                            label.setGraphic(new ImageView(emptyCell));
                            label.setOpacity(1);
                            turn[0] = false;
                        } else if (Objects.equals(labelValue, "0")) {
                            amountOfShot++;
                            emptyCellAudio.sound();
                            label.setText(null);
                            label.setGraphic(new ImageView(emptyCell));
                            label.setOpacity(1);
                            turn[0] = false;
                        } else if (Objects.equals(labelValue, "1")) {
                            amountOfShot++;
                            enemyShipsLeft--;
                            brokenShipAudio.sound();
                            label.setText(null);
                            label.setGraphic(new ImageView(oneDeskHit));
                            label.setOpacity(1);
                            GridPaneControl.setEmptyImage(row, column, Integer.parseInt(labelValue), enemyShipsLabel, emptyCell);
                            turn[0] = true;
                        } else if (Objects.equals(labelValue, "2")) {
                            amountOfShot++;
                            if (Ship.isBroken(label.getText(), enemyShipsLabel)) {
                                enemyShipsLeft--;
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
                            amountOfShot++;
                            if (Ship.isBroken(label.getText(), enemyShipsLabel)) {
                                enemyShipsLeft--;
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
                            amountOfShot++;
                            if (Ship.isBroken(label.getText(), enemyShipsLabel)) {
                                enemyShipsLeft--;
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
                    if (ai.shipsLeft > 0 && enemyShipsLeft > 0) {
                        if (turn[0]) {
                            setMoveImage(true);
                        } else {
                            setMoveImage(false);
                            ai.newShot();
                            aiShotSetImage(ai.shots);
                            if (ai.shipsLeft == 0 || enemyShipsLeft == 0) {
                                resultWindow();
                            }
                        }
                    } else {
                        resultWindow();
                    }
                });
                enemyFieldGrid.add(enemyShipsLabel[i][j], j, i);
            }
        }
    }

    private void aiShotSetImage(ArrayList<String> shots) {
        Image hitShip = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/HitCell.png")).toExternalForm());
        Image emptyCell = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/EmptyCell.png")).toExternalForm());
        ArrayList<Label> labels = new ArrayList<>();
        ArrayList<Integer[]> positions = new ArrayList<>();
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
            labels.add(label);
            positions.add(new Integer[] {row, column});
        }
        ai.shots.clear();
        setElementsWithDelay(positions, labels);
    }

    private void setMoveImage(boolean myMove) {
        Image myMoveArrowImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/MyMoveArrow.png")).toExternalForm());
        Image enemyMoveArrowImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/EnemyMoveArrow.png")).toExternalForm());
        if (myMove) whoseMoveImage.setImage(myMoveArrowImage);
        else whoseMoveImage.setImage(enemyMoveArrowImage);
    }

    private Path getFilePath(String name) throws URISyntaxException {
        URL url = Application.class.getResource(name);
        return Paths.get(Objects.requireNonNull(url).toURI());
    }

    private void setElementsWithDelay(ArrayList<Integer[]> position, ArrayList<Label> labels) {
        if (Objects.equals(threadId, "null")) {
            enemyFieldGrid.setDisable(true);
            thread = new Thread(() -> {
                for (int i = 0; i < labels.size(); i++) {
                    int finalI = i;
                    Platform.runLater(() -> myFieldGrid.add(labels.get(finalI), position.get(finalI)[1],  position.get(finalI)[0]));
                    threadId = String.valueOf(thread.getId());
                    int delay = (int) ((Math.random() * (1500 - 700)) + 700);
                    try{
                        Thread.sleep(delay);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i == labels.size() - 1) {
                        turn[0] = true;
                        setMoveImage(true);
                        threadId = "null";
                        Platform.runLater(() -> Ship.isBrokenEnemy(ai.brokenShips, myShipsLabel, myFieldGrid));
                        enemyFieldGrid.setDisable(false);
                    }
                }
            });
            thread.start();
        } else enemyFieldGrid.setDisable(true);
    }

    private void resultWindow() {
        long endTime = System.currentTimeMillis();
        long allTime = (endTime - startTime) / 1000;
        String time = "";
        if (allTime > 60) {
            time = allTime/60 + " хв. ";
            allTime = allTime - (allTime/60) * 60;
        }
        time += allTime + " сек.";
        String caption = (ai.shipsLeft == 0)? "Ви програли" : "Ви перемогли";
        String text = (ai.shipsLeft == 0)? "Яка поразка...Мастросе, Вас переміг Штучний інтелект." : "Матросе! Вітаю Вас з черговим яскравим досягненням.";
        ResultWindow resultWindow = new ResultWindow(time, String.valueOf(amountOfShot), text, caption, (Stage) menuBar.getScene().getWindow());
        try {
            resultWindow.start(new Stage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
