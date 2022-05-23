package com.seabattle.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class Test {
    public static void main(String[] args) {
        int[] t = new int[]{-1, -1, -1, -1, -1, 0, 0, -1, 2, 2};
        int element = t[0];
        int counter = 0;
        for (int i = 0; i < t.length; i++) {
            if (element == t[i]) {
                counter++;
            } else {
                System.out.println(counter);
                element = t[i];
                counter = 1;
            }
            if (i == 9) {
                System.out.println(counter);
                element = t[i];
                counter = 1;
            }
        }
    }
}
