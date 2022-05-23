package com.seabattle.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RandomSetting {
    public int[][] field = new int[10][10];
    public ArrayList<Integer> ships = new ArrayList<>(Arrays.asList(1, 1, 1, 1, 2, 2, 2, 3, 3, 4));
    int iteration = 0;
    int fullRows = 10;
    public boolean isFull = false;


    public void fillArraysWithDefault() {
        Arrays.stream(field).forEach(a -> Arrays.fill(a, 0));
    }

    public void setShips() {
        boolean isSettableRow = true;
        int freeSpaceMaxCount, freeSpaceMaxIndex, numberOfDesks, shift;
        int[] result;
        int index;
//        ************************************************************************************************
        // створюємо новий о'бєкт, бо при даному розташуванні існуючих кораблів використані не всі
        iteration++;
        if (fullRows == 0) {
            isFull = true;
        }
        fullRows = 10;
//        ************************************************************************************************

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
//        ************************************************************************************************
                // створюємо новий о'бєкт, бо при даному розташуванні існуючих кораблів використані не всі
                if (isFull) {

//        ************************************************************************************************
                } else {
                    index = randomizeNumberOfDesks(freeSpaceMaxCount);
                    numberOfDesks = ships.get(index);
                    setShipInARow(numberOfDesks, i, freeSpaceMaxCount, freeSpaceMaxIndex - freeSpaceMaxCount, index);
                }

            }
        }
    }

    void setSpaceOver(int desks, int row, int minIndex, int shift) {
        if (row > 0) {
            for (int i = 0; i < desks; i++) {
                field[row - 1][minIndex + shift + i + 1] = -1;
            }
        }
    }

    void setSpaceUnder(int desks, int row, int minIndex, int shift) {
        if (row < 9) {
            for (int i = 0; i < desks; i++) {
                field[row + 1][minIndex + shift + i + 1] = -1;
            }
        }
    }

    void setSpaceLeft(int row, int minIndex, int shift) {
        int column = minIndex + shift + 1;
        if (column > 0) {
            for (int i = 0; i < 3; i++) {
                if (!((i == 0 && row == 0) || (i == 2 && row == 9))) {
                    field[row + i - 1][column - 1] = -1;
                }
            }
        }
    }

    void setSpaceRight(int row, int minIndex, int shift, int desks) {
        int column = minIndex + shift + desks;
        if (column < 9) {
            for (int i = 0; i < 3; i++) {
                if (!((i == 0 && row == 0) || (i == 2 && row == 9))) {
                    field[row + i - 1][column + 1] = -1;
                }
            }
        }
    }

    boolean isFreeSpaceOver(int desks, int row, int minIndex, int shift) {
        if (row == 0) return true;
        for (int i = 0; i < desks; i++) {

            int elem = field[row - 1][minIndex + shift + i + 1];
            if (!(elem == -1 || elem == 0)) return false;
        }
        return true;
    }

    boolean isFreeSpaceUnder(int desks, int row, int minIndex, int shift) {
        if (row == 9) return true;
        for (int i = 0; i < desks; i++) {
            int elem = field[row + 1][minIndex + shift + i + 1];
            if (!(elem == -1 || elem == 0)) return false;
        }
        return true;
    }

    boolean isFreeSpaceLeft(int row, int minIndex, int shift) {
        int column = minIndex + shift + 1;
        if (column == 0) return true;
        for (int i = 0; i < 3; i++) {
            if (!((i == 0 && row == 0) || (i == 2 && row == 9))) {
                int elem = field[row + i - 1][column - 1];
                if (!(elem == -1 || elem == 0)) return false;
            }
        }
        return true;
    }

    boolean isFreeSpaceRight(int row, int minIndex, int shift, int desks) {
        int column = minIndex + shift + desks;
        if (column == 9) return true;
        for (int i = 0; i < 3; i++) {
            if (!((i == 0 && row == 0) || (i == 2 && row == 9))) {
                int elem = field[row + i - 1][column + 1];
                if (!(elem == -1 || elem == 0)) return false;
            }
        }
        return true;
    }

    boolean isFreeSpaceAround(int desks, int row, int minIndex, int shift) {
        return isFreeSpaceOver(desks, row, minIndex, shift) && isFreeSpaceUnder(desks, row, minIndex, shift) && isFreeSpaceLeft(row, minIndex, shift) && isFreeSpaceRight(row, minIndex, shift, desks);
    }

    void setShipInARow(int desks, int row, int count, int minIndex, int index) {
        int shift = (int) (Math.random() * 5);
        if (shift + desks > count) shift = 0;
        if (isFreeSpaceAround(desks, row, minIndex, shift)) {
            for (int i = 0; i < desks; i++) {
                field[row][minIndex + shift + i + 1] = desks;
            }
            setSpaceOver(desks, row, minIndex, shift);
            setSpaceUnder(desks, row, minIndex, shift);
            setSpaceLeft(row, minIndex, shift);
            setSpaceRight(row, minIndex, shift, desks);
            ships.remove(index);
        }
    }

    int randomizeNumberOfDesks(int counter) {
        int index;
        do {
            index = (int) (Math.random() * ships.size());
        } while (counter < ships.get(index));
        return index;
    }

    int[] findMaxFreeSpaces(int i) {
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
