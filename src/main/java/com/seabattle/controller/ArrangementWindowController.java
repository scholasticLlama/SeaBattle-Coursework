package com.seabattle.controller;

import com.seabattle.model.DragAndDropShip;
import com.seabattle.model.Ship;
import com.seabattle.view.Application;
import com.seabattle.view.Audio;
import com.seabattle.view.WindowControlManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ArrangementWindowController {

    @FXML
    public GridPane myField;
    @FXML
    private Label closeWindowButton;
    @FXML
    private ImageView emptyImage;
    @FXML
    private ImageView fourDeck;
    @FXML
    private AnchorPane menuBar;
    @FXML
    private Label minimizeWindowButton;
    @FXML
    private ImageView oneDeck_1;
    @FXML
    private ImageView oneDeck_2;
    @FXML
    private ImageView oneDeck_3;
    @FXML
    private ImageView oneDeck_4;
    @FXML
    private Button randomShipPlaceButton;
    @FXML
    private Label resetButton;
    @FXML
    private Button startGameButton;
    @FXML
    private ImageView threeDeck_1;
    @FXML
    private ImageView threeDeck_2;
    @FXML
    private ImageView twoDeck_1;
    @FXML
    private ImageView twoDeck_2;
    @FXML
    private ImageView twoDeck_3;

    @FXML
    void initialize() throws URISyntaxException {
        Image oneShipImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_1x1_H.png")).toExternalForm());
        Image twoShipImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_2x1_H.png")).toExternalForm());
        Image threeShipImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_3x1_H.png")).toExternalForm());
        Image fourShipImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_4x1_H.png")).toExternalForm());
        Image[] images = new Image[]{oneShipImage, twoShipImage, threeShipImage, fourShipImage};

        URL urlAudioClickButton = Application.class.getResource("resource/sound/ClickButton.wav");
        Path pathAudioClickButton = Paths.get(Objects.requireNonNull(urlAudioClickButton).toURI());
        Audio clickButton = new Audio(String.valueOf(pathAudioClickButton));

        ImageView[] imageViews = new ImageView[]{oneDeck_1, oneDeck_2, oneDeck_3, oneDeck_4, twoDeck_1, twoDeck_2, twoDeck_3, threeDeck_1, threeDeck_2, fourDeck};
        DragAndDropShip.start(myField, imageViews, emptyImage);

        WindowControlManager.closeWindow(closeWindowButton);
        WindowControlManager.minimizeWindow(minimizeWindowButton);
        WindowControlManager.randomShipPlace(randomShipPlaceButton, myField, DragAndDropShip.getShips(), emptyImage);
        WindowControlManager.resetShips(resetButton, myField, DragAndDropShip.getShips(), images, emptyImage, DragAndDropShip.getCell());
        WindowControlManager.dragWindow(menuBar, closeWindowButton, minimizeWindowButton, resetButton);

        startGameButton.setOnAction(event -> {
            clickButton.sound();
            try {
                Ship.myFieldToLabel(myField);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
            Stage gameStage = new Stage();
            Stage stage = (Stage) startGameButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("resource/fxml/battleWindow-view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(loader.load(), 720, 420);
            } catch (IOException e) {
                e.printStackTrace();
            }
            gameStage.setTitle("Sea Battle");
            gameStage.initStyle(StageStyle.UNDECORATED);
            gameStage.setX(stage.getX());
            gameStage.setY(stage.getY());

            gameStage.setScene(scene);
            gameStage.show();
            stage.hide();
        });

    }

}
