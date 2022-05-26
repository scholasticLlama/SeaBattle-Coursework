package com.seabattle.model;

import java.util.ArrayList;

public class AI {
    private final int[][] field;
    private final ArrayList<String> startShots = new ArrayList<>();
    private final ArrayList<String> cells = new ArrayList<>();
    private final ArrayList<String> currentShip = new ArrayList<>();
    public final ArrayList<String> shots = new ArrayList<>();
    public final ArrayList<String> brokenShips = new ArrayList<>();
    private boolean shiftRight = true;
    private int shipsLeft;
    private int steps;

    public AI(int[][] field) {
        this.field = field;
        shipsLeft = 10;
        steps = 0;
        fillCellsDefault();
        System.out.println(startShots);
    }

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

    public void newShot() {
        System.out.println("newShot " + startShots.size() + " " + cells.size());
        if (currentShip.size() == 0) shootTillHit();
        else continueSinkTheShip();
    }

    public boolean isHit(int row, int column) {
        System.out.println("boolean " + (field[row][column] >= 1));
        steps++;
        cells.remove(String.valueOf(row * 10 + column));
        startShots.remove(String.valueOf(row * 10 + column));
        shots.add(String.valueOf(row * 10 + column));
        return field[row][column] >= 1;
    }

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
        //startShots.remove(position);
        //cells.remove(position);
        System.out.println("row " + row + ", " + column + " str " + position);
        if (isHit(row, column)) {
            sinkTheShip(row, column);
        }
    }

    public void sinkTheShip(int row, int column) {
        currentShip.add(String.valueOf(row * 10 + column));
        //startShots.remove(String.valueOf(row * 10 + column));
        //cells.remove(String.valueOf(row * 10 + column));
        System.out.println(currentShip + " currentShip; " + currentShip.size() + " " + field[row][column]);
        if (currentShip.size() == field[row][column]) {
            deleteEmptyCellsAroundShip();
            addBrokenShip(currentShip, brokenShips);
            currentShip.clear();
            shipsLeft--;
            shiftRight = true;
            System.out.println("shipsLeft " + shipsLeft);
            if (shipsLeft > 0) newShot();
            else System.out.println("all ships sunk");
        } else {
            System.out.println("shiftRight " + shiftRight);
            if (shiftRight) {
                System.out.println("contains " + cells.contains(String.valueOf(row * 10 + (column + 1))));
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

    public void deleteEmptyCellsAboveShip(int row, int column) {
        if (row > 0) {
            for (int i = 0; i < currentShip.size(); i++) {
                String position = String.valueOf((row - 1) * 10 + column + i);
                System.out.println("position over " + position);
                startShots.remove(position);
                cells.remove(position);
            }
        }
    }

    public void deleteEmptyCellsUnderShip(int row, int column) {
        if (row < 9) {
            for (int i = 0; i < currentShip.size(); i++) {
                String position = String.valueOf((row + 1) * 10 + column + i);
                System.out.println("position under " + position);
                startShots.remove(position);
                cells.remove(position);
            }
        }
    }

    public void deleteEmptyCellsLeftwardShip(int row, int column) {
        if (column > 0) {
            for (int i = 0; i < 3; i++) {
                if (!((i == 0 && row == 0) || (i == 2 && row == 9))){
                    String position = String.valueOf((row - 1 + i) * 10 + column - 1);
                    System.out.println("position left " + position);
                    startShots.remove(position);
                    cells.remove(position);
                }
            }
        }
    }

    public void deleteEmptyCellsRightwardShip(int row, int column) {
        if (column + currentShip.size() <= 9) {
            for (int i = 0; i < 3; i++) {
                if (!((i == 0 && row == 0) || (i == 2 && row == 9))) {
                    String position = String.valueOf((row - 1 + i) * 10 + column + currentShip.size());
                    System.out.println("position right " + position);
                    startShots.remove(position);
                    cells.remove(position);
                }
            }
        }
    }

    public void moveRight(int row, int column) {
        System.out.println("moveRight");
        if (column < 9) {
            if (isHit(row, column + 1)) {
                sinkTheShip(row, column + 1);
            } else shiftRight = false;
        }
    }

    public void moveLeft(int row, int column) {
        System.out.println("moveLeft");
        if (column > 0) {
            if (isHit(row, column - 1)) {
                sinkTheShip(row, column - 1);
            }
        }
    }

    public void continueSinkTheShip() {
        System.out.println("continueSinkTheShip");
        int row = Integer.parseInt(currentShip.get(0)) / 10;
        int column = Integer.parseInt(currentShip.get(0)) - row * 10;
        moveLeft(row, column);
    }

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
