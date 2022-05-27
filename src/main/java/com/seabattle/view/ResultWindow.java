package com.seabattle.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
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
import java.util.Objects;

public class ResultWindow extends Application {
    String time;
    String amountOfShoot;
    String resultText;
    String caption;
    Stage ownerStage;

    public ResultWindow(String time, String amountOfShoot, String resultText, String caption, Stage ownerStage) {
        this.time = time;
        this.amountOfShoot = amountOfShoot;
        this.resultText = resultText;
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

        Label textLabel = new Label();
        textLabel.setPrefSize(580, 100);
        textLabel.setLayoutX(10);
        textLabel.setLayoutY(80);
        textLabel.setWrapText(true);
        textLabel.setTextAlignment(TextAlignment.CENTER);
        textLabel.setAlignment(Pos.CENTER);
        textLabel.setText(resultText);
        textLabel.setFont(Font.font("Calibri", FontPosture.REGULAR, 28));
        textLabel.setStyle("-fx-text-fill:#00072c;");

        Label infoLabel = new Label();
        infoLabel.setPrefSize(580, 100);
        infoLabel.setLayoutX(10);
        infoLabel.setLayoutY(157);
        infoLabel.setWrapText(true);
        infoLabel.setTextAlignment(TextAlignment.CENTER);
        infoLabel.setAlignment(Pos.CENTER);
        infoLabel.setText("Ви зробили: " + amountOfShoot + " пострілів. " + "Гра тривала " + time);
        infoLabel.setFont(Font.font("Calibri", FontPosture.REGULAR, 28));
        infoLabel.setStyle("-fx-text-fill:#00072c;");

        Label minimizeButton = new Label();
        minimizeButton.setPrefSize(50, 50);
        minimizeButton.setLayoutX(545);
        minimizeButton.setLayoutY(5);

        Label menuButton = new Label();
        menuButton.setPrefSize(250, 80);
        menuButton.setLayoutX(10);
        menuButton.setLayoutY(260);

        Label tryAgainButton = new Label();
        tryAgainButton.setPrefSize(250, 80);
        tryAgainButton.setLayoutX(340);
        tryAgainButton.setLayoutY(260);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(0);
        dropShadow.setColor(Color.BLACK);

        minimizeButton.setEffect(dropShadow);
        minimizeButton.getStyleClass().add("minimizeButton");

        menuButton.setEffect(dropShadow);
        menuButton.getStyleClass().add("menuButton");

        tryAgainButton.setEffect(dropShadow);
        tryAgainButton.getStyleClass().add("tryAgainButton");

        WindowControlManager.minimizeWindow(minimizeButton);
        WindowControlManager.dragWindow(menuBar, minimizeButton, minimizeButton, minimizeButton);

        menuButton.setOnMouseClicked(event -> {
            try {
                WindowControlManager.openNewWindow(menuButton, "resource/fxml/menuWindow-view.fxml");
                Stage thisStage = (Stage) menuButton.getScene().getWindow();
                thisStage.close();
                ownerStage.close();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });

        tryAgainButton.setOnMouseClicked(event -> {
            try {
                WindowControlManager.openNewWindow(tryAgainButton, "resource/fxml/arrangementWindow-view.fxml");
                Stage thisStage = (Stage) menuButton.getScene().getWindow();
                thisStage.close();
                ownerStage.close();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });

        menuBar.getChildren().add(captionLabel);
        menuBar.getChildren().add(minimizeButton);
        root.getChildren().add(menuBar);
        root.getChildren().add(menuButton);
        root.getChildren().add(tryAgainButton);
        root.getChildren().add(textLabel);
        root.getChildren().add(infoLabel);
        root.setPrefSize(600, 350);
        root.getStyleClass().add("anchorPane");
        root.getStylesheets().add(Objects.requireNonNull(InfoWindow.class.getResource("resource/css/ResultWindow.css")).toExternalForm());

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
