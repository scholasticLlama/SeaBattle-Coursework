package com.seabattle.controller;

import com.seabattle.model.DragAndDropShip;
import com.seabattle.model.Ship;
import com.seabattle.view.Application;
import com.seabattle.view.Audio;
import com.seabattle.view.InfoWindow;
import com.seabattle.view.WindowControlManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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
    private Label homeButton;

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
    private Label randomShipPlace;

    @FXML
    private Label resetButton;

    @FXML
    private Label startGame;

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
        WindowControlManager.openNewWindowEvent(homeButton, "resource/fxml/menuWindow-view.fxml");
        WindowControlManager.randomShipPlace(randomShipPlace, myField, DragAndDropShip.getShips(), emptyImage);
        WindowControlManager.resetShips(resetButton, myField, DragAndDropShip.getShips(), images, emptyImage, DragAndDropShip.getCell());
        WindowControlManager.dragWindow(menuBar, closeWindowButton, minimizeWindowButton, resetButton);

        startGame.setOnMouseClicked(event -> {
            if (Ship.isAllShipUse(myField)) {
                clickButton.sound();
                try {
                    Ship.myFieldToLabel(myField);
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
                try {
                    WindowControlManager.openNewWindow(startGame, "resource/fxml/battleWindow-view.fxml");
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } else {
                String errorMassage = "You haven't used all the ships! Arrange all these ships, and try again.";
                InfoWindow infoWindow = new InfoWindow(errorMassage, "Помилка", (Stage) startGame.getScene().getWindow());
                try {
                    infoWindow.start(new Stage());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
