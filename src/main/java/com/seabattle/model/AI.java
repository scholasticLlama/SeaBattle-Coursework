package com.seabattle.model;

import java.util.ArrayList;

/**
 * Class defines model of AI moves
 * @author Yaroslava Kozhemiako
 */
public class AI {
    private final int[][] field;
    private final ArrayList<String> startShots = new ArrayList<>();
    private final ArrayList<String> cells = new ArrayList<>();
    private final ArrayList<String> currentShip = new ArrayList<>();
    public final ArrayList<String> shots = new ArrayList<>();
    public final ArrayList<String> brokenShips = new ArrayList<>();
    private boolean shiftRight = true;
    public int shipsLeft;

    /**
     * Constructor
     * @param field enemy's field to shot
     */
    public AI(int[][] field) {
        this.field = field;
        shipsLeft = 10;
        fillCellsDefault();
    }

    /**
     * fills cells of the field with information about its row and column;
     * fills startShots the most likely positions of ships location
     */
    private void fillCellsDefault() {
        boolean isSettable;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cells.add(String.valueOf(i * 10 + j));
                isSettable = Math.random() < 0.5;
                if (isSettable && (i + j == 2 || i + j == 5 || i + j == 8 || i + j == 10 || i + j == 13 || i + j == 16)) {
                    startShots.add(String.valueOf(i * 10 + j));
                }
            }
        }
    }

    /**
     * makes new shot: if there is hit, but not broken ship, go and sink it, otherwise random shot
     */
    public void newShot() {
        if (currentShip.size() == 0) shootTillHit();
        else continueSinkTheShip();
    }

    /**
     * check is the shot a hit or a miss
     * @param row row of the shot
     * @param column column of the shot
     * @return whether it was hit or not
     */
    public boolean isHit(int row, int column) {
        cells.remove(String.valueOf(row * 10 + column));
        startShots.remove(String.valueOf(row * 10 + column));
        shots.add(String.valueOf(row * 10 + column));
        return field[row][column] >= 1;
    }

    /**
     * pseudo-random shot in free cells
     */
    public void shootTillHit() {
        int index;
        String position;
        if (startShots.size() != 0) {
            index = (int) (Math.random() * startShots.size());
            position = startShots.get(index);
        } else {
            index = (int) (Math.random() * cells.size());
            position = cells.get(index);
        }
        int row = Integer.parseInt(position) / 10;
        int column = Integer.parseInt(position) - row * 10;
        if (isHit(row, column)) {
            sinkTheShip(row, column);
        }
    }

    /**
     * go here, when we've got hit; if length of the ship we try to sink and number of shots are the same, we're done
     * otherwise, move right or left to continue its sinking
     * @param row row of the shot
     * @param column column of the shot
     */
    public void sinkTheShip(int row, int column) {
        currentShip.add(String.valueOf(row * 10 + column));
        if (currentShip.size() == field[row][column]) {
            deleteEmptyCellsAroundShip();
            addBrokenShip(currentShip, brokenShips);
            currentShip.clear();
            shipsLeft--;
            shiftRight = true;
            if (shipsLeft > 0) newShot();
        } else {
            if (shiftRight) {
                if (column < 9 && cells.contains(String.valueOf(row * 10 + (column + 1)))) moveRight(row, column);
                else {
                    shiftRight = false;
                    int rowRight = Integer.parseInt(currentShip.get(0)) / 10;
                    int columnRight = Integer.parseInt(currentShip.get(0)) - row * 10;
                    moveLeft(rowRight, columnRight);
                }
            } else moveLeft(row, column);
        }
    }

    /**
     * delete empty space around the sunken ship
     */
    public void deleteEmptyCellsAroundShip() {
        int leftPosition = Integer.parseInt(currentShip.get(0));
        for (String position : currentShip) {
            if (Integer.parseInt(position) < leftPosition)
                leftPosition = Integer.parseInt(position);
        }
        int row = leftPosition / 10;
        int column = leftPosition - row * 10;
        deleteEmptyCellsAboveShip(row, column);
        deleteEmptyCellsUnderShip(row, column);
        deleteEmptyCellsLeftwardShip(row, column);
        deleteEmptyCellsRightwardShip(row, column);
    }

    /**
     * delete empty space above the sunken ship
     * @param row row of the sunken ship
     * @param column far left column of the sunken ship
     */
    public void deleteEmptyCellsAboveShip(int row, int column) {
        if (row > 0) {
            for (int i = 0; i < currentShip.size(); i++) {
                String position = String.valueOf((row - 1) * 10 + column + i);
                startShots.remove(position);
                cells.remove(position);
            }
        }
    }

    /**
     * delete empty space under the sunken ship
     * @param row row of the sunken ship
     * @param column far left column of the sunken ship
     */
    public void deleteEmptyCellsUnderShip(int row, int column) {
        if (row < 9) {
            for (int i = 0; i < currentShip.size(); i++) {
                String position = String.valueOf((row + 1) * 10 + column + i);
                startShots.remove(position);
                cells.remove(position);
            }
        }
    }

    /**
     * delete empty space leftward the sunken ship
     * @param row row of the sunken ship
     * @param column far left column of the sunken ship
     */
    public void deleteEmptyCellsLeftwardShip(int row, int column) {
        if (column > 0) {
            for (int i = 0; i < 3; i++) {
                if (!((i == 0 && row == 0) || (i == 2 && row == 9))){
                    String position = String.valueOf((row - 1 + i) * 10 + column - 1);
                    startShots.remove(position);
                    cells.remove(position);
                }
            }
        }
    }

    /**
     * delete empty space rightward the sunken ship
     * @param row row of the sunken ship
     * @param column far left column of the sunken ship
     */
    public void deleteEmptyCellsRightwardShip(int row, int column) {
        if (column + currentShip.size() <= 9) {
            for (int i = 0; i < 3; i++) {
                if (!((i == 0 && row == 0) || (i == 2 && row == 9))) {
                    String position = String.valueOf((row - 1 + i) * 10 + column + currentShip.size());
                    startShots.remove(position);
                    cells.remove(position);
                }
            }
        }
    }

    /**
     * shot one cell right
     * @param row previous shot row
     * @param column previous shot column
     */
    public void moveRight(int row, int column) {
        if (column < 9) {
            if (isHit(row, column + 1)) {
                sinkTheShip(row, column + 1);
            } else shiftRight = false;
        }
    }

    /**
     * shot one cell left
     * @param row previous shot row
     * @param column previous shot column
     */
    public void moveLeft(int row, int column) {
        if (column > 0) {
            if (isHit(row, column - 1)) {
                sinkTheShip(row, column - 1);
            }
        }
    }

    /**
     * go here, when we've got miss after hit part of a ship;
     * continue sinking the ship by shooting one cell left
     */
    public void continueSinkTheShip() {
        int row = Integer.parseInt(currentShip.get(0)) / 10;
        int column = Integer.parseInt(currentShip.get(0)) - row * 10;
        moveLeft(row, column);
    }

    /**
     * find far right column of the broken ship; add information about it to array
     * @param currentShip array consist of positions of the sunken ship
     * @param brokenShips array consist of uniq information about the sunken ships
     */
    private void addBrokenShip(ArrayList<String> currentShip, ArrayList<String> brokenShips) {
        String position = currentShip.get(0);
        for (int i = 1; i < currentShip.size(); i++) {
            if (Integer.parseInt(position) > Integer.parseInt(currentShip.get(i))) {
                position = currentShip.get(i);
            }
        }
        int row = Integer.parseInt(position) / 10;
        int column = Integer.parseInt(position) - row * 10;
        brokenShips.add(currentShip.size() + "," + row + "," + column);
    }

}
