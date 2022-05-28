package com.seabattle.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Class defines random setting of ships
 * @author Yaroslava Kozhemiako
 */

public class RandomSetting {
    public int[][] field = new int[10][10];
    public ArrayList<Integer> ships = new ArrayList<>(Arrays.asList(1, 1, 1, 1, 2, 2, 2, 3, 3, 4));
    int iteration = 0;
    int fullRows = 10;
    public boolean isFull = false;


    /**
     * fills array FIELD with zeros
     */
    public void fillArraysWithDefault() {
        Arrays.stream(field).forEach(a -> Arrays.fill(a, 0));
    }

    /**
     * if field not full, method sets the remaining ships
     */
    public void setShips() {
        boolean isSettableRow = true;
        int freeSpaceMaxCount, freeSpaceMaxIndex, numberOfDesks;
        int[] result;
        int index;
        iteration++;
        if (fullRows == 0) {
            isFull = true;
        }
        fullRows = 10;

        for (int i = 0; i < field.length; i++) {
            if (iteration == 1) isSettableRow = Math.random() < 0.5;
            if (isSettableRow && ships.size() != 0) {
                result = findMaxFreeSpaces(i);
                freeSpaceMaxCount = result[0];
                freeSpaceMaxIndex = result[1];
                if (freeSpaceMaxCount < Collections.min(ships)) {
                    fullRows--;
                    continue;
                }
                if (!isFull) {
                    index = randomizeNumberOfDesks(freeSpaceMaxCount);
                    numberOfDesks = ships.get(index);
                    setShipInARow(numberOfDesks, i, freeSpaceMaxCount, freeSpaceMaxIndex - freeSpaceMaxCount, index);
                }

            }
        }
    }

    /**
     * sets space over the ship, if it is possible
     * @param desks length of the ship
     * @param row row where the ship's located
     * @param column far left column where the ship's located
     */
    private void setSpaceOver(int desks, int row, int column) {
        if (row > 0) {
            for (int i = 0; i < desks; i++) {
                field[row - 1][column + i + 1] = -1;
            }
        }
    }

    /**
     * sets space under the ship, if it is possible
     * @param desks length of the ship
     * @param row row where the ship's located
     * @param column far left column where the ship's located
     */
    private void setSpaceUnder(int desks, int row, int column) {
        if (row < 9) {
            for (int i = 0; i < desks; i++) {
                field[row + 1][column + i + 1] = -1;
            }
        }
    }

    /**
     * sets space leftward the ship, if it is possible
     * @param row row where the ship's located
     * @param column far left column where the ship's located
     */
    private void setSpaceLeft(int row, int column) {
        if (column >= 0) {
            for (int i = 0; i < 3; i++) {
                if (!((i == 0 && row == 0) || (i == 2 && row == 9))) {
                    field[row + i - 1][column] = -1;
                }
            }
        }
    }

    /**
     * sets space rightward the ship, if it is possible
     * @param row row where the ship's located
     * @param column far right column where the ship's located
     */
    private void setSpaceRight(int row, int column) {
        if (column < 9) {
            for (int i = 0; i < 3; i++) {
                if (!((i == 0 && row == 0) || (i == 2 && row == 9))) {
                    field[row + i - 1][column + 1] = -1;
                }
            }
        }
    }

    /**
     * defines is there free space over the ship
     * @param desks length of the ship
     * @param row row where the ship's located
     * @param column far left column where the ship's located
     * @return whether it is free or not
     */
    private boolean isFreeSpaceOver(int desks, int row, int column) {
        if (row == 0) return true;
        for (int i = 0; i < desks; i++) {
            int elem = field[row - 1][column + i + 1];
            if (!(elem == -1 || elem == 0)) return false;
        }
        return true;
    }

    /**
     * defines is there free space under the ship
     * @param desks length of the ship
     * @param row row where the ship's located
     * @param column far left column where the ship's located
     * @return whether it is free or not
     */
    private boolean isFreeSpaceUnder(int desks, int row, int column) {
        if (row == 9) return true;
        for (int i = 0; i < desks; i++) {
            int elem = field[row + 1][column + i + 1];
            if (!(elem == -1 || elem == 0)) return false;
        }
        return true;
    }

    /**
     * defines is there free space leftward the ship
     * @param row row where the ship's located
     * @param column far left column where the ship's located
     * @return whether it is free or not
     */
    private boolean isFreeSpaceLeft(int row, int column) {
        column++;
        if (column == 0) return true;
        for (int i = 0; i < 3; i++) {
            if (!((i == 0 && row == 0) || (i == 2 && row == 9))) {
                int elem = field[row + i - 1][column - 1];
                if (!(elem == -1 || elem == 0)) return false;
            }
        }
        return true;
    }

    /**
     * defines is there free space rightward the ship
     * @param row row where the ship's located
     * @param column far right column where the ship's located
     * @return whether it is free or not
     */
    private boolean isFreeSpaceRight(int row, int column) {
        if (column == 9) return true;
        for (int i = 0; i < 3; i++) {
            if (!((i == 0 && row == 0) || (i == 2 && row == 9))) {
                int elem = field[row + i - 1][column + 1];
                if (!(elem == -1 || elem == 0)) return false;
            }
        }
        return true;
    }

    /**
     * defines is there free space around the ship
     * @param desks length of the ship
     * @param row row where the ship's located
     * @param column far left column where the ship's located
     * @return whether it is free or not
     */
    private boolean isFreeSpaceAround(int desks, int row, int column) {
        return isFreeSpaceOver(desks, row, column) && isFreeSpaceUnder(desks, row, column) && isFreeSpaceLeft(row, column) && isFreeSpaceRight(row, column + desks);
    }

    /**
     * sets ship in a row
     * @param desks length of the ship
     * @param row row where the ship will be located
     * @param count amount of free cells in a row
     * @param minIndex far left column where the ship will be located
     * @param index index of the ship in ArrayList SHIPS
     */
    private void setShipInARow(int desks, int row, int count, int minIndex, int index) {
        int shift = (int) (Math.random() * 5);
        if (shift + desks > count) shift = 0;
        if (isFreeSpaceAround(desks, row, minIndex + shift)) {
            for (int i = 0; i < desks; i++) {
                field[row][minIndex + shift + i + 1] = desks;
            }
            setSpaceOver(desks, row, minIndex + shift);
            setSpaceUnder(desks, row, minIndex + shift);
            setSpaceLeft(row, minIndex + shift);
            setSpaceRight(row, minIndex + shift + desks);
            ships.remove(index);
        }
    }

    /**
     * randomize number of desks to set
     * @param counter maximum free space
     * @return number of desks
     */
    private int randomizeNumberOfDesks(int counter) {
        int index;
        do {
            index = (int) (Math.random() * ships.size());
        } while (counter < ships.get(index));
        return index;
    }

    /**
     * finds maximum free space in a row
     * @param i row we're looking for maximum free space at
     * @return maximum free space, last index of sequence
     */
    private int[] findMaxFreeSpaces(int i) {
        int counter = 0, maxCounter = 0, maxIndex = 0;
        for (int j = 0; j < field[i].length; j++) {
            if (field[i][j] == 0) {
                counter++;
                if (counter > maxCounter) {
                    maxCounter = counter;
                    maxIndex = j;
                }
            } else counter = 0;
        }
        return new int[]{maxCounter, maxIndex};
    }

}
