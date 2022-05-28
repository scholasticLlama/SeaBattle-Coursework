package com.seabattle.controller;

import java.net.URISyntaxException;

import com.seabattle.view.WindowControlManager;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * This class processes data of the program creators window.
 * @author Kiril Vashchuk
 * @author Yaroslava Kozhemiako
 * @author Yaroslava Nehrych
 */
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
    /**
     * Method to initialize buttons for game functionality and providing information about game creators
     * @throws URISyntaxException error handling if file path is not found
     */
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
