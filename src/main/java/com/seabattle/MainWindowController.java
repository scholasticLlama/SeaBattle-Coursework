package com.seabattle;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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

    private double x;
    private double y;
    private double xOffset;
    private double yOffset;
    private boolean isPossibleToDrag;
    private byte[] myField;
    private final Label[][] cell = new Label[10][10];

    @FXML
    void initialize() throws IOException {
        placeShip();
        enemyField();
        // close window
        closeWindowButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                Stage stage = (Stage) closeWindowButton.getScene().getWindow();
                stage.close();
            }
        });

        // minimize window
        minimizeWindowButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                Stage stage = (Stage) minimizeWindowButton.getScene().getWindow();
                stage.setIconified(true);
            }
        });

        // window dragging
        menuBar.setOnMousePressed(event -> {
            xOffset = event.getX();
            yOffset = event.getY();
            isPossibleToDrag = !(closeWindowButton.isHover() | minimizeWindowButton.isHover());
        });

        menuBar.setOnMouseDragged(event -> {
            x = event.getScreenX();
            y = event.getScreenY();
            if (event.getButton() == MouseButton.PRIMARY && isPossibleToDrag) {
                Stage stage = (Stage) menuBar.getScene().getWindow();
                stage.setX(x - xOffset);
                stage.setY(y - yOffset);
            }
        });

    }

    void placeShip() throws IOException {
        Image oneShipImage = new Image(Objects.requireNonNull(MainWindowController.class.getResource("Ship_1x1_H.png")).toExternalForm());
        Image twoShipImage = new Image(Objects.requireNonNull(MainWindowController.class.getResource("Ship_2x1_H.png")).toExternalForm());
        Image threeShipImage = new Image(Objects.requireNonNull(MainWindowController.class.getResource("Ship_3x1_H.png")).toExternalForm());
        Image fourShipImage = new Image(Objects.requireNonNull(MainWindowController.class.getResource("Ship_4x1_H.png")).toExternalForm());
        FileInputStream file = new FileInputStream("src/main/resources/com/seabattle/fieldInArray");
        myField = file.readAllBytes();
        int countRow = 0;
        for (int i = 0; i < myField.length; i++) {
            Label label = new Label();
            label.setPrefSize(30, 30);
            if (myField[i] == 1) {
                if (i + 1 < myField.length && myField[i + 1] == 1) {
                    if (i + 2 < myField.length && myField[i + 2] == 1) {
                        if (i + 3 < myField.length && myField[i + 3] == 1) {
                            label.setGraphic(new ImageView(fourShipImage));
                        } else label.setGraphic(new ImageView(threeShipImage));
                    } else label.setGraphic(new ImageView(twoShipImage));
                } else label.setGraphic(new ImageView(oneShipImage));
                myFieldGrid.add(label,9 - ((countRow + 1) * 10 - (i + 1)), countRow);
            }
            if ((i + 1) % 10 == 0) {
                countRow++;
            }
        }
    }

    void enemyField() {
        Image hitShip = new Image(Objects.requireNonNull(MainWindowController.class.getResource("HitCell.png")).toExternalForm());
        Image emptyCell = new Image(Objects.requireNonNull(MainWindowController.class.getResource("EmptyCell.png")).toExternalForm());
        for (int i = 0; i < enemyFieldGrid.getRowCount(); i++) {
            for (int j = 0; j < enemyFieldGrid.getColumnCount(); j++) {
                cell[i][j] = new Label();
                cell[i][j].setPrefSize(30,30);
                cell[i][j].setAlignment(Pos.CENTER);
                cell[i][j].setOpacity(0);
                if (i % 2 == 0) {
                    cell[i][j].setText("1");
                } else cell[i][j].setText("0");

                cell[i][j].setOnMouseClicked(event -> {
                    Label label = (Label) event.getSource();
                    if (Objects.equals(label.getText(), "1")) {
                        label.setText(null);
                        label.setGraphic(new ImageView(hitShip));
                        label.setOpacity(1);
                    } else if (Objects.equals(label.getText(), "0")) {
                        label.setText(null);
                        label.setGraphic(new ImageView(emptyCell));
                        label.setOpacity(1);
                    }
                });

                enemyFieldGrid.add(cell[i][j], j, i);
            }
        }
    }

}
