package tictactoe;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Field field = new Field();
        Player playerOne = new User("aleos");
        Player playerTwo = new PlayerAI(field);

        var gc = new GameController(field, playerOne, playerTwo);
        gc.startGame();

    }

    public static Scanner getScanner() {
        return scanner;
    }
}


