package com.seabattle;

import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

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
    private ImageView emptyImage;

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
        Label[][] cell = new Label[10][10];
        for (int i = 0; i < myField.getRowCount(); i++) {
            for (int j = 0; j < myField.getColumnCount(); j++) {
                cell[i][j] = new Label();
                cell[i][j].setPrefSize(30,30);
                myField.add(cell[i][j], j, i);
            }
        }

        HashMap<Integer, ImageView[]> ships = new HashMap<>();
        ships.put(1, new ImageView[] {oneDeck_1, oneDeck_2, oneDeck_3, oneDeck_4});
        ships.put(2, new ImageView[] {twoDeck_1, twoDeck_2, twoDeck_3});
        ships.put(3, new ImageView[] {threeDeck_1, threeDeck_2});
        ships.put(4, new ImageView[] {fourDeck});
        for(int i = 1; i <= ships.size(); i++) {
            //Integer key = entry.getKey();
            ImageView[] value = ships.get(i);
            for (int j = 0; j < value.length; j++) {
                putShip(value[j], cell);
            }
        }
        ImageView[] images = new ImageView[] {oneDeck_1, twoDeck_1, threeDeck_1, fourDeck};
        for (int i = 0; i < images.length; i++) {
            putShip(images[i], cell);
        }
    }



    void  putShip(ImageView source, Label[][] targets) {
        int amountOfDecks = (int) source.getFitWidth() / 30;
        source.setOnDragDetected(event -> {
            /* drag was detected, start drag-and-drop gesture*/
            System.out.println("onDragDetected");

            /* allow any transfer mode */
            Dragboard db = source.startDragAndDrop(TransferMode.ANY);

            /* put a string on dragboard */
            ClipboardContent content = new ClipboardContent();
            content.putImage(source.getImage());
            db.setContent(content);

            event.consume();
        });

        for (int i = 0; i < targets.length; i++) {
            for (int j = 0; j < targets[i].length; j++) {
                Label target = targets[i][j];

                target.setOnDragOver(event -> {
                    /* data is dragged over the target */
                    System.out.println("onDragOver");

                    /* accept it only if it is  not dragged from the same node
                     * and if it has a string data */
                    if (event.getGestureSource() != target &&
                            event.getDragboard().hasImage()) {
                        /* allow for both copying and moving, whatever user chooses */
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }

                    event.consume();
                });

                target.setOnDragDropped(event -> {
                    /* data dropped */
                    System.out.println("onDragDropped");
                    /* if there is a string data on dragboard, read it and use it */
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    if (db.hasImage() && target.getGraphic() == null) {
                        target.setGraphic(new ImageView(db.getImage()));
                        success = true;
                    }
                    /* let the source know whether the string was successfully
                     * transferred and used */
                    event.setDropCompleted(success);

                    event.consume();
                });

                source.setOnDragDone(event -> {
                    /* the drag-and-drop gesture ended */
                    System.out.println("onDragDone");
                    /* if the data was successfully moved, clear it */
                    if (event.getTransferMode() == TransferMode.MOVE) {
                        source.setImage(emptyImage.getImage());
                    }

                    event.consume();
                });
            }
        }

    }

}
