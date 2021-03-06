package com.seabattle.view;

import com.seabattle.model.DragAndDropShip;
import com.seabattle.model.GridPaneControl;
import com.seabattle.model.RandomSetting;
import com.seabattle.model.Ship;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;

/**
 * A class that contains methods for managing window functions
 * @author Kiril Vashchuk
 * @author Yaroslava Kozhemiako
 * @author Yaroslava Nehrych
 */
public class WindowControlManager {
    private static double x;
    private static double y;
    private static double xOffset;
    private static double yOffset;
    private static boolean isPossibleToDrag;

    /**
     * This method terminates the program
     * @param button the button to assign this action to
     * @throws URISyntaxException error handling if file path not found
     */
    public static void closeWindow(Label button) throws URISyntaxException {
        URL urlAudioClickButton = Application.class.getResource("resource/sound/ClickButton.wav");
        Path pathAudioClickButton = Paths.get(Objects.requireNonNull(urlAudioClickButton).toURI());
        Audio clickButton = new Audio(String.valueOf(pathAudioClickButton));
        button.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                clickButton.sound();
                Stage stage = (Stage) button.getScene().getWindow();
                stage.close();
            }
        });
    }

    /**
     * This method hides the application on the taskbar
     * @param button the button to assign this action to
     * @throws URISyntaxException error handling if file path not found
     */
    public static void minimizeWindow(Label button) throws URISyntaxException {
        URL urlAudioClickButton = Application.class.getResource("resource/sound/ClickButton.wav");
        Path pathAudioClickButton = Paths.get(Objects.requireNonNull(urlAudioClickButton).toURI());
        Audio clickButton = new Audio(String.valueOf(pathAudioClickButton));
        button.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                clickButton.sound();
                Stage stage = (Stage) button.getScene().getWindow();
                stage.setIconified(true);
            }
        });
    }

    /**
     * This method allows you to drag and drop an application on the desktop
     * @param menuBar part of the application with which you can drag the application (by standard, the top thing, that is, the menu bar)
     * @param closeWindowButton window close button
     * @param minimizeWindowButton window minimize button
     * @param funcButton other buttons located on this menubar
     */
    public static void dragWindow(AnchorPane menuBar, Label closeWindowButton, Label minimizeWindowButton, Label funcButton) {
        menuBar.setOnMousePressed(event -> {
            xOffset = event.getX();
            yOffset = event.getY();
            isPossibleToDrag = !(closeWindowButton.isHover() | minimizeWindowButton.isHover() | funcButton.isHover());
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

    /**
     * This method clears the field on which to place the ships
     * @param button the button to assign this action to
     * @param gridPane grid on which ships are placed
     * @param ships list of ships that can be placed on the grid
     * @param images array of ship images
     * @param emptyImage empty (transparent) picture
     * @param cell an array of labels that are placed on the grid and store images of ships
     */
    public static void resetShips(Label button, GridPane gridPane, HashMap<Integer, ImageView[]> ships, Image[] images, ImageView emptyImage, Label[][] cell) {
        button.setOnMouseClicked(event -> {
            URL urlAudioResetButton = Application.class.getResource("resource/sound/ResetButton.wav");
            Path pathAudioResetButton = null;
            try {
                pathAudioResetButton = Paths.get(Objects.requireNonNull(urlAudioResetButton).toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            Audio resetButton = new Audio(String.valueOf(pathAudioResetButton));
            resetButton.sound();
            for (int i = 0; i < gridPane.getRowCount(); i++) {
                for (int j = 0; j < gridPane.getColumnCount(); j++) {
                    Label label = (Label) GridPaneControl.getNodeFromGridPane(gridPane, j, i);
                    assert label != null;
                    label.setGraphic(null);
                    label.setText(null);
                }
            }
            for (int i = 0; i < 4; i++) {
                ImageView[] imageViewsFromMap = ships.get(i + 1);
                for (ImageView imageView : imageViewsFromMap) {
                    imageView.setImage(Ship.getShipImage(i + 1, images));
                }
            }
            DragAndDropShip.startDragAndDrop(ships, cell, emptyImage);
        });
    }

    /**
     * This method places ships on the grid in a random way.
     * @param button the button to assign this action to
     * @param gridPane grid on which ships are placed
     * @param ships list of ships that can be placed on the grid
     * @param emptyImage empty (transparent) picture
     */
    public static void randomShipPlace(Label button, GridPane gridPane, HashMap<Integer, ImageView[]> ships, ImageView emptyImage) {
        button.setOnMouseClicked(event -> {
            URL urlAudioRandomPlaceButton = Application.class.getResource("resource/sound/RandomPlaceButton.wav");
            Path pathAudioRandomPlaceButton = null;
            try {
                pathAudioRandomPlaceButton = Paths.get(Objects.requireNonNull(urlAudioRandomPlaceButton).toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            Audio randomPlaceButton = new Audio(String.valueOf(pathAudioRandomPlaceButton));
            randomPlaceButton.sound();

            Image oneShipImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_1x1_H.png")).toExternalForm());
            Image twoShipImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_2x1_H.png")).toExternalForm());
            Image threeShipImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_3x1_H.png")).toExternalForm());
            Image fourShipImage = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/Ship_4x1_H.png")).toExternalForm());
            Image[] images = new Image[] {oneShipImage, twoShipImage, threeShipImage, fourShipImage};

            int[][] myShips;
            RandomSetting randomSetting;
            do {
                randomSetting = new RandomSetting();
                randomSetting.fillArraysWithDefault();
                do {
                    randomSetting.setShips();
                } while (randomSetting.ships.size() > 0 && !randomSetting.isFull);
            } while (randomSetting.isFull);
            myShips = randomSetting.field;

            int shift = 0;
            for (int i = 0; i < gridPane.getRowCount(); i++) {
                for (int j = 0; j < gridPane.getColumnCount(); j++) {
                    Label label = (Label) GridPaneControl.getNodeFromGridPane(gridPane, j, i);
                    assert label != null;
                    if (myShips[i][j] > 0 && shift <= 1) {
                        label.setGraphic(new ImageView(Ship.getShipImage(myShips[i][j], images)));
                        shift = myShips[i][j];
                    } else {
                        if (shift > 1) {
                            label.setGraphic(new ImageView(emptyImage.getImage()));
                        } else label.setGraphic(null);
                        shift--;
                    }
                    label.setText(null);
                }
                shift = 0;
            }

            for (int i = 0; i < 4; i++) {
                ImageView[] imageViewsFromMap = ships.get(i + 1);
                for (ImageView imageView : imageViewsFromMap) {
                    imageView.setImage(emptyImage.getImage());
                }
            }
        });
    }

    /**
     * This method assigns the action of opening a new window to the button.
     * @param button the button to assign this action to
     * @param path path to the fxml file that contains the window structure
     */
    public static void openNewWindowEvent(Label button, String path) {
        button.setOnMouseClicked(event -> {
            try {
                openNewWindow(button, path);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * This method opens new window
     * @param button a button that will perform this action, and by referring to which you can get the parent stage
     * @param path path to the fxml file that contains the window structure
     * @throws URISyntaxException error handling if file path not found
     */
    public static void openNewWindow(Label button, String path) throws URISyntaxException {
        Image icon = new Image(Objects.requireNonNull(Application.class.getResource("resource/photo/icon-ship.png")).toExternalForm());
        URL urlAudioClickButton = Application.class.getResource("resource/sound/ClickButton.wav");
        Path pathAudioClickButton = Paths.get(Objects.requireNonNull(urlAudioClickButton).toURI());
        Audio clickButton = new Audio(String.valueOf(pathAudioClickButton));
        clickButton.sound();
        Stage gameStage = new Stage();
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Application.class.getResource(path));
        Scene scene = null;
        try {
            scene = new Scene(loader.load(), 720, 420);
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameStage.setTitle("Sea Battle");
        gameStage.initStyle(StageStyle.UNDECORATED);
        gameStage.getIcons().add(icon);
        gameStage.setX(stage.getX());
        gameStage.setY(stage.getY());

        gameStage.setScene(scene);
        gameStage.show();
        stage.hide();
    }

    /**
     * This method opens the default browser and visits the specified site
     * @param hyperlink the "Hyperlink" element, when clicked, will open the site at the specified link
     * @param url link
     */
    public static void openBrowse(Hyperlink hyperlink, String url) {
        hyperlink.setOnMouseClicked(event -> {
            if(Desktop.isDesktopSupported()){
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new URI(url));
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }else{
                Runtime runtime = Runtime.getRuntime();
                try {
                    runtime.exec("xdg-open " + url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
