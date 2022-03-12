package com.seabattle;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class ApplicationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button authorButton;

    @FXML
    private Button exitButton;

    @FXML
    private AnchorPane menuPane;

    @FXML
    private Button settingButton;

    @FXML
    private Button startGameButton;

    @FXML
    void initialize() {
        assert authorButton != null : "fx:id=\"authorButton\" was not injected: check your FXML file 'menu-view.fxml'.";
        assert exitButton != null : "fx:id=\"exitButton\" was not injected: check your FXML file 'menu-view.fxml'.";
        assert menuPane != null : "fx:id=\"menuPane\" was not injected: check your FXML file 'menu-view.fxml'.";
        assert settingButton != null : "fx:id=\"settingButton\" was not injected: check your FXML file 'menu-view.fxml'.";
        assert startGameButton != null : "fx:id=\"startGameButton\" was not injected: check your FXML file 'menu-view.fxml'.";

    }

}
