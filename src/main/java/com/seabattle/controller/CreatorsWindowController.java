package com.seabattle.controller;

import java.net.URISyntaxException;

import com.seabattle.view.WindowControlManager;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CreatorsWindowController {

    @FXML
    private Label exitButton;

    @FXML
    private Label homeButton;

    @FXML
    private AnchorPane menuBar;

    @FXML
    private Label minimizeButton;

    @FXML
    private Hyperlink telegramButtonK;

    @FXML
    private Hyperlink telegramButtonN;

    @FXML
    private Hyperlink telegramButtonV;

    @FXML
    void initialize() throws URISyntaxException {
        WindowControlManager.closeWindow(exitButton);
        WindowControlManager.minimizeWindow(minimizeButton);
        WindowControlManager.openNewWindowEvent(homeButton, "resource/fxml/menuWindow-view.fxml");
        WindowControlManager.dragWindow(menuBar, exitButton, minimizeButton, homeButton);
        WindowControlManager.openBrowse(telegramButtonV, "https://t.me/kirilvashchuk");
        WindowControlManager.openBrowse(telegramButtonK, "https://t.me/nibiru_crs");
        WindowControlManager.openBrowse(telegramButtonN, "https://t.me/slavvka");
    }

}
