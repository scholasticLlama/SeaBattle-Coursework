package com.seabattle.model;

import com.seabattle.view.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Ship {
    public static void getMyShip(int[][] field, File file, GridPane gridPane, Image[] images) throws IOException {
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
        placeMyShip(field, gridPane, images);
    }

    public static void placeMyShip(int[][] field, GridPane gridPane, Image[] images) {
        int shift = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j = shift) {
                if (field[i][j] > 0) {
                    Label label = new Label();
                    label.setPrefSize(30, 30);
                    label.setGraphic(new ImageView(getShipImage(field[i][j], images)));
                    gridPane.add(label, j, i);
                    shift += field[i][j];
                } else shift++;
            }
            shift = 0;
        }
    }

    public static void myFieldToLabel(GridPane myField) throws IOException, URISyntaxException {
        int[] out = new int[100];
        int index = 0;
        int lastIndex = 0;
        int counter = 0;
        ObservableList<Node> labels = myField.getChildren();
        for (Node node : labels) {
            Label l = (Label) node;
            if (l.getGraphic() != null) {
                counter ++;
                lastIndex = index;
            }
            if (l.getGraphic() == null || index == labels.size() - 1) {
                out[index] = 0;
                for (int i = lastIndex - counter + 1; i <= lastIndex; i ++) {
                    out[i] = counter;
                }
                counter = 0;
            }
            index++;
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

    public static Image getShipImage(int numberOfDesks, Image[] images) {
        return switch (numberOfDesks) {
            case 1 -> images[0];
            case 2 -> images[1];
            case 3 -> images[2];
            case 4 -> images[3];
            default -> null;
        };
    }
}
