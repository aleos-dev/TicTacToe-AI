package tictactoe;

import tictactoe.Field.Symbol;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Field field = new Field();
        var fieldController = new FieldController(field);
        initFieldState(fieldController);
        fieldController.print();

        while (!fieldController.inputCoordinateSuccess()) {
            // NOPE
        }

        fieldController.print();
        System.out.println(fieldController.checkGameStatus());

    }

    static void initFieldState(FieldController fc) {

        System.out.print("Enter the cells: ");
        String defaultState = scanner.nextLine();
        System.out.println();

        fc.initDefaultFieldState(defaultState);
    }

    public static Scanner getScanner() {
        return scanner;
    }
}


class FieldController {
    private final Field field;
    private final int dimension;
    public FieldController(Field field) {
        this.field = field;
        this.dimension = field.getDimension();
    }

    public void initDefaultFieldState(String defaultState) {
        field.initDefaultState(defaultState);
    }

    public boolean inputCoordinateSuccess() {

        System.out.print("Enter the coordinates: ");
        String input = Main.getScanner().nextLine();

        try {

            field.applyNextTurn(input);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("You should enter numbers!");
        } catch (Field.WrongCoordinateException e) {
            System.out.println("Coordinates should be from 1 to 3!");
        } catch (Field.AlreadyOccupiedException e) {
            System.out.println("This cell is occupied! Choose another one!");
        }


        return false;
    }


    public String checkGameStatus() {
        String gameNotFinished = "Game not finished";
        String draw = "Draw";
        String xWon = "%s wins";


        if (isPlayerWin(Symbol.O)) {
            return String.format(xWon, Symbol.O);
        }

        if (isPlayerWin(Symbol.X)) {
            return String.format(xWon, Symbol.X);
        }

        if (field.turnCount() == Field.FIELD_SIZE) {
            return draw;
        }


        return gameNotFinished;

    }

    private boolean isPlayerWin(Symbol symbol) {

        for (int i = 0; i < field.getDimension(); i++) {
            if (isPlayerCloseRow(symbol, i)
                            || isPlayerCloseColumn(symbol, i)
                            || isPlayerCloseDiagonal(symbol)) {
                return true;
            }
        }
        return false;
    }


    private boolean isPlayerCloseRow(Symbol symbol, int row) {

        int counter = 0;
        for (int i = 0; i < 3; i++) {
            if (field.get(row * dimension + i) == symbol) {
                ++counter;
            }
        }

        return counter == 3;

    }

    private boolean isPlayerCloseColumn(Symbol symbol, int column) {
        int counter = 0;
        for (int i = 0; i < 3; i += dimension) {
            if (field.get(column + i * dimension) == symbol) {
                ++counter;
            }
        }

        return counter == 3;
    }

    private boolean isPlayerCloseDiagonal(Symbol symbol) {
        int counter1 = 0;
        int counter2 = 0;
        for (int i = 0; i < 3; i++) {
            if (field.get(i * dimension + i) == symbol) {
                ++counter1;
            }

            if (field.get((i + 1) * dimension - i - 1) == symbol) {
                ++counter2;
            }
        }

        return counter1 == 3 || counter2 == 3;
    }


    public void print() {
        field.print();
    }
}
