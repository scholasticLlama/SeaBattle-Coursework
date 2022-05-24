package com.seabattle.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class InfoWindow extends Application {
    String errorMassage;
    String caption;
    Stage ownerStage;

    public InfoWindow(String errorMassage,  String caption, Stage ownerStage) {
        this.errorMassage = errorMassage;
        this.caption = caption;
        this.ownerStage = ownerStage;
    }

    @Override
    public void start(Stage stage) throws URISyntaxException {
        AnchorPane root = new AnchorPane();

        AnchorPane menuBar = new AnchorPane();
        menuBar.setPrefSize(420, 60);

        Label captionLabel = new Label();
        captionLabel.setPrefSize(355, 60);
        captionLabel.setLayoutX(5);
        captionLabel.setText(caption);
        captionLabel.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 36));
        captionLabel.setStyle("-fx-text-fill:#1f39be;");

        Label errorLabel = new Label();
        errorLabel.setPrefSize(410, 170);
        errorLabel.setLayoutY(50);
        errorLabel.setLayoutX(5);
        errorLabel.setAlignment(Pos.CENTER);
        errorLabel.setTextAlignment(TextAlignment.CENTER);
        errorLabel.setWrapText(true);
        errorLabel.setText(errorMassage);
        errorLabel.setStyle("-fx-text-fill:#333333; -fx-font: 26px Calibri;");

        Label exitButton = new Label();
        exitButton.setPrefSize(50, 50);
        exitButton.setLayoutX(365);
        exitButton.setLayoutY(5);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(0);
        dropShadow.setColor(Color.BLACK);

        exitButton.setEffect(dropShadow);
        exitButton.getStyleClass().add("exitButton");

        WindowControlManager.closeWindow(exitButton);
        WindowControlManager.dragWindow(menuBar, exitButton, exitButton, exitButton);

        menuBar.getChildren().add(captionLabel);
        menuBar.getChildren().add(exitButton);
        root.getChildren().add(errorLabel);
        root.getChildren().add(menuBar);
        root.setPrefSize(420, 230);
        root.getStyleClass().add("anchorPane");
        root.getStylesheets().add(Objects.requireNonNull(InfoWindow.class.getResource("resource/css/InfoWindow.css")).toExternalForm());

        Scene scene = new Scene(root);

        stage.setTitle("SeaBattle");
        stage.initOwner(ownerStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
