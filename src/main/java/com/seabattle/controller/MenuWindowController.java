package com.seabattle.controller;

import com.seabattle.view.ResultWindow;
import com.seabattle.view.WindowControlManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URISyntaxException;

public class MenuWindowController {

    @FXML
    private Label battleButton;

    @FXML
    private Label creatorsButton;

    @FXML
    private Label exitButton;

    @FXML
    private AnchorPane menuBar;

    @FXML
    private Label minimizeButton;

    @FXML
    void initialize() throws URISyntaxException {
        WindowControlManager.closeWindow(exitButton);
        WindowControlManager.minimizeWindow(minimizeButton);
        WindowControlManager.dragWindow(menuBar, minimizeButton, minimizeButton, minimizeButton);
        WindowControlManager.openNewWindowEvent(battleButton, "resource/fxml/arrangementWindow-view.fxml");
        WindowControlManager.openNewWindowEvent(creatorsButton, "resource/fxml/creatorsWindow-view.fxml");
    }

}
