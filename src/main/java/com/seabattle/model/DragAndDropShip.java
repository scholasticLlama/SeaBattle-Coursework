package com.seabattle.model;

import com.seabattle.view.Application;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DragAndDropShip {
    static HashMap<Integer, ImageView[]> outShips;
    static Label[][] outCell;
    public static void start(GridPane myField, ImageView[] imageViews, ImageView emptyImage) {
        Label[][] cell = new Label[10][10];
        outCell = cell;
        HashMap<Integer, ImageView[]> ships = new HashMap<>();
        outShips = ships;

        for (int i = 0; i < myField.getRowCount(); i++) {
            for (int j = 0; j < myField.getColumnCount(); j++) {
                cell[i][j] = new Label();
                cell[i][j].setPrefSize(30, 30);
                myField.add(cell[i][j], j, i);
            }
        }
        ships.put(1, new ImageView[]{imageViews[0], imageViews[1], imageViews[2], imageViews[3]});
        ships.put(2, new ImageView[]{imageViews[4], imageViews[5], imageViews[6]});
        ships.put(3, new ImageView[]{imageViews[7], imageViews[8]});
        ships.put(4, new ImageView[]{imageViews[9]});
        startDragAndDrop(ships, cell, emptyImage);
    }

    public static void startDragAndDrop(HashMap<Integer, ImageView[]> ships, Label[][] cell, ImageView emptyImage) {
        for (int i = 1; i <= ships.size(); i++) {
            //Integer key = entry.getKey();
            ImageView[] value = ships.get(i);
            for (ImageView imageView : value) {
                dragAndDropShip(imageView, cell, emptyImage);
            }
        }
    }

    public static HashMap<Integer, ImageView[]> getShips() {
        return outShips;
    }

    public static Label[][] getCell() {
        return outCell;
    }


    private static void dragAndDropShip(ImageView source, Label[][] targets, ImageView emptyImage) {
        int[] amountOfDecks = new int[1];
        Image possibleCellImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/PossibleCell.png")).toExternalForm());
        Image impossibleCellImage = new Image(Objects.requireNonNull(Objects.requireNonNull(Application.class.getResource("resource/photo/ImpossibleCell.png")).toExternalForm()));

        source.setOnDragDetected(event -> {
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
                Label[] possibleTargets = new Label[]{null, null, null};
                Label[][] fullCells = new Label[][] {{null, null, null, null, null, null}, {null, null, null, null, null, null}, {null, null, null, null, null, null}};

                for (int k = 0; k < possibleTargets.length; k++) {
                    if (j + k + 1 < targets[i].length) {
                        possibleTargets[k] = targets[i][j + k + 1];
                    }
                }

                for (int l = 0; l < fullCells.length; l++) {
                    for (int k = 0; k < fullCells[l].length; k++) {
                        if (j + k - 1 < targets[i].length && i + l - 1 < targets.length) {
                            if (i != 0 && j != 0) {
                                fullCells[l][k] = targets[i - 1 + l][j - 1 + k];
                            } else {
                                if (i == 0 && l != 0 && j != 0)
                                    fullCells[l][k] = targets[l - 1][j - 1 + k];
                                if (j == 0 && k != 0 && i != 0)
                                    fullCells[l][k] = targets[i - 1 + l][k - 1];
                                if (i == 0 && j == 0 && l != 0 && k != 0)
                                    fullCells[l][k] = targets[i + l - 1][j + k - 1];
                            }

                        }
                    }
                }

                target.setOnDragOver(event -> {
                    /* accept it only if it is  not dragged from the same node
                     * and if it has a string data */
                    ImageView object = (ImageView) event.getGestureSource();
                    amountOfDecks[0] = (int) object.getFitWidth() / 30;
                    if (event.getGestureSource() != target &&
                            event.getDragboard().hasImage()) {
                        /* allow for both copying and moving, whatever user chooses */
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }

                    event.consume();
                });

                target.setOnDragEntered(event -> {
                    /* show to the user that it is an actual gesture target */
                    if (event.getGestureSource() != target &&
                            event.getDragboard().hasImage()) {
                        for (int k = 0; k < amountOfDecks[0] + 2; k++) {
                            if (fullCells[0][k] != null) {
                                if (Objects.equals(fullCells[0][k].getText(), "\u200E") && fullCells[0][k].getGraphic() == null) {
                                    fullCells[0][k].setGraphic(new ImageView(impossibleCellImage));
                                } else if (fullCells[0][k].getGraphic() == null)
                                    fullCells[0][k].setGraphic(new ImageView(possibleCellImage));
                            }
                            if (fullCells[1][k] != null) {
                                if (Objects.equals(fullCells[1][k].getText(), "\u200E") && fullCells[1][k].getGraphic() == null) {
                                    fullCells[1][k].setGraphic(new ImageView(impossibleCellImage));
                                } else if (fullCells[1][k].getGraphic() == null)
                                    fullCells[1][k].setGraphic(new ImageView(possibleCellImage));
                            }
                            if (fullCells[2][k] != null) {
                                if (Objects.equals(fullCells[2][k].getText(), "\u200E") && fullCells[2][k].getGraphic() == null) {
                                    fullCells[2][k].setGraphic(new ImageView(impossibleCellImage));
                                } else if (fullCells[2][k].getGraphic() == null)
                                    fullCells[2][k].setGraphic(new ImageView(possibleCellImage));
                            }
                        }
                    }

                    event.consume();
                });

                target.setOnDragExited(event -> {
                    /* mouse moved away, remove the graphical cues */
                    for (int k = 0; k < amountOfDecks[0] + 2; k++) {
                        if (fullCells[0][k] != null) {
                            ImageView a = (ImageView) fullCells[0][k].getGraphic();
                            if (a != null && (a.getImage() == possibleCellImage || a.getImage() == impossibleCellImage))
                                fullCells[0][k].setGraphic(null);
                        }
                        if (fullCells[1][k] != null) {
                            ImageView a = (ImageView) fullCells[1][k].getGraphic();
                            if (a != null && (a.getImage() == possibleCellImage || a.getImage() == impossibleCellImage))
                                fullCells[1][k].setGraphic(null);
                        }
                        if (fullCells[2][k] != null) {
                            ImageView a = (ImageView) fullCells[2][k].getGraphic();
                            if (a != null && (a.getImage() == possibleCellImage || a.getImage() == impossibleCellImage))
                                fullCells[2][k].setGraphic(null);
                        }

                    }

                    event.consume();
                });

                target.setOnDragDropped(event -> {
                    /* if there is a string data on dragboard, read it and use it */
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    ImageView imageView = (ImageView) target.getGraphic();
                    Set<Image> images = new HashSet<>();
                    images.add(null);
                    images.add(possibleCellImage);
                    images.add(impossibleCellImage);
                    boolean a = images.contains(imageView.getImage());
                    if (db.hasImage() && a && !Objects.equals(target.getText(), "\u200E")) {

                        for (int m = possibleTargets.length - 1; m >= 3 - (possibleTargets.length + 1 - amountOfDecks[0]); m--) {
                            possibleTargets[m] = null;
                        }

                        int count = 1;
                        int countEmpty = 1;
                        for (Label possibleTarget : possibleTargets) {
                            if (possibleTarget != null) {
                                if (!Objects.equals(possibleTarget.getText(), "\u200E")) {
                                    countEmpty++;
                                }
                                count++;
                            }
                        }

                        if (amountOfDecks[0] == count && amountOfDecks[0] == countEmpty) {
                            for (int k = 0; k < amountOfDecks[0] + 2; k++) {
                                if (fullCells[0][k] != null) {
                                    fullCells[0][k].setText("\u200E");
                                }
                                if (fullCells[1][k] != null) {
                                    fullCells[1][k].setText("\u200E");
                                }
                                if (fullCells[2][k] != null) {
                                    fullCells[2][k].setText("\u200E");
                                }

                            }

                            for (int l = 0; l < count - 1; l++) {
                                possibleTargets[l].setGraphic(new ImageView(emptyImage.getImage()));
                            }
                            target.setGraphic(new ImageView(db.getImage()));
                            success = true;

                        }

                    }
                    /* let the source know whether the string was successfully
                     * transferred and used */
                    event.setDropCompleted(success);

                    event.consume();
                });

                source.setOnDragDone(event -> {
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
