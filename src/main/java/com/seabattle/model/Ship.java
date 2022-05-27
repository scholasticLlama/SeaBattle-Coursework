package com.seabattle.model;

import com.seabattle.view.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class Ship {
    public static void getMyShips(int[][] field, File file, GridPane gridPane, Image[] images) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String stringArray = bufferedReader.readLine();
        bufferedReader.close();
        fileReader.close();
        int index = 0;
        char element;
        for (int n = 0; n < field.length; n++) {
            for (int m = 0; m < field[n].length; m++) {
                element = stringArray.charAt(index);
                field[n][m] = Integer.parseInt(String.valueOf(element));
                index++;
            }
        }
        SetShips.setMyShips(field, gridPane, images);
    }

    public static void myFieldToLabel(GridPane myField) throws IOException, URISyntaxException {
        int[] out = new int[100];
        int index = 0;
        int lastIndex = 0;
        int amountOfColumn = 0;
        int counter = 0;
        ObservableList<Node> labels = myField.getChildren();
        for (Node node : labels) {
            Label l = (Label) node;
            if (l.getGraphic() != null) {
                counter ++;
                lastIndex = index;
            }
            if (l.getGraphic() == null || index == labels.size() - 1) {
                setShip(out, counter, lastIndex);
                counter = 0;
            }
            amountOfColumn++;
            index++;
            if (amountOfColumn % 10 == 0) {
                amountOfColumn = 0;
                setShip(out, counter, lastIndex);
                counter = 0;
            }
        }
        URL urlFileFieldInArray = Application.class.getResource("resource/file/fieldInArray");
        Path pathFileFieldInArray = Paths.get(Objects.requireNonNull(urlFileFieldInArray).toURI());
        File file = new File(String.valueOf(pathFileFieldInArray));
        FileWriter fileWriter = new FileWriter(file);
        for (int i : out) {
            fileWriter.write(String.valueOf(i));
        }
        fileWriter.close();
    }

    public static boolean isAllShipUse(GridPane gridPane) {
        int countOfDesks = 0;
        for (int i = 0; i < gridPane.getRowCount(); i++) {
            for (int j = 0; j < gridPane.getColumnCount(); j++) {
                Label label = (Label) GridPaneControl.getNodeFromGridPane(gridPane, j, i);
                assert label != null;
                if (label.getGraphic() != null) {
                    countOfDesks++;
                }
            }
        }
        return countOfDesks == 20;
    }

    public static boolean isBroken(String stringShipInfo, Label[][] enemyShipsLabel) {
        String[] shipInfo = stringShipInfo.split(",");
        int counter = 1;
        int row = Integer.parseInt(shipInfo[1]);
        for (int j = 2; j < shipInfo.length; j++) {
            if (enemyShipsLabel[row][Integer.parseInt(shipInfo[j])].getGraphic() != null) {
                counter++;
            }
        }
        return counter == Integer.parseInt(shipInfo[0]);
    }

    public static void isBrokenEnemy(ArrayList<String> brokenShips, Label[][] myShipsLabel, GridPane myFieldGrid) {
        Image emptyCell = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/EmptyCell.png")).toExternalForm());
        if (brokenShips.size() > 0) {
            for (String brokenShip : brokenShips) {
                String[] brokenShipInfoArray = brokenShip.split(",");
                int length = Integer.parseInt(brokenShipInfoArray[0]);
                int rowBroken = Integer.parseInt(brokenShipInfoArray[1]);
                int columnBroken = Integer.parseInt(brokenShipInfoArray[2]);
                GridPaneControl.clearGraphicInArray(myShipsLabel);
                GridPaneControl.setEmptyImage(rowBroken, columnBroken, length, myShipsLabel, emptyCell);
                GridPaneControl.setLabelsToGridPane(myFieldGrid, myShipsLabel);
            }
            brokenShips.clear();
        }
    }

    public static Image getShipImage(int numberOfDesks, Image[] images) {
        return switch (numberOfDesks) {
            case 1 -> images[0];
            case 2 -> images[1];
            case 3 -> images[2];
            case 4 -> images[3];
            default -> null;
        };
    }

    private static void setShip(int[] out, int counter, int lastIndex) {
        for (int i = lastIndex - counter + 1; i <= lastIndex; i ++) {
            out[i] = counter;
        }
    }
}
