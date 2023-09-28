package tictactoe;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Field field = new Field();
        GameUI gameUI = new GameUI(field, scanner);
        gameUI.startMenu();

        scanner.close();
    }
}


