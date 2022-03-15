package com.seabattle;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class ArrangementWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView fourDeck;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private HBox menuBar;

    @FXML
    private GridPane myField;

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
    private GridPane shipField;

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
    void initialize() {
        System.out.println(myField.getWidth());
        Label[][] cell = new Label[10][10];
        for (int i = 0; i < myField.getRowCount(); i++) {
            for (int j = 0; j < myField.getColumnCount(); j++) {
                cell[i][j] = new Label();
                cell[i][j].setPrefSize(30,30);
                myField.add(cell[i][j], i, j);
            }
        }
//        ImageView[][] cell = new ImageView[10][10];
//        for (int i = 0; i < myField.getRowCount(); i++) {
//            for (int j = 0; j < myField.getColumnCount(); j++) {
//                cell[i][j] = new ImageView();
//                myField.add(cell[i][j], i, j);
//            }
//        }

        twoDeck_1.setOnDragDetected(event -> {
            /* drag was detected, start drag-and-drop gesture*/
            System.out.println("onDragDetected");

            /* allow any transfer mode */
            Dragboard db = twoDeck_1.startDragAndDrop(TransferMode.ANY);

            /* put a string on dragboard */
            ClipboardContent content = new ClipboardContent();
            content.putImage(twoDeck_1.getImage());
            db.setContent(content);

            event.consume();
        });

       cell[1][1].setOnDragOver(event -> {
            /* data is dragged over the target */
            System.out.println("onDragOver");

            /* accept it only if it is  not dragged from the same node
             * and if it has a string data */
            if (event.getGestureSource() != cell[1][1] &&
                    event.getDragboard().hasString()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }

            event.consume();
        });

        cell[1][1].setOnDragEntered(event -> {
            /* the drag-and-drop gesture entered the target */
            System.out.println("onDragEntered");
            /* show to the user that it is an actual gesture target */
            if (event.getGestureSource() != cell[1][1] &&
                    event.getDragboard().hasString()) {
                cell[1][1].setGraphic(twoDeck_1);
            }

            event.consume();
        });

        cell[1][1].setOnDragExited(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
                cell[1][1].setGraphic(twoDeck_1);

                event.consume();
            }
        });

        twoDeck_1.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    twoDeck_1.setImage(db.getImage());
                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });

    }

}
