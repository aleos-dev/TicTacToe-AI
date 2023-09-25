package tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Field {
    public enum Symbol {
        EMPTY(" "), X("X"), O("O");

        private final String value;

        Symbol(String symbol) {
            this.value = symbol;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public static final int FIELD_SIZE = 9;
    private final List<Symbol> field = new ArrayList<>(FIELD_SIZE);
    private final AtomicInteger turnCounter = new AtomicInteger(0);
    private final int dimension = (int) Math.sqrt(FIELD_SIZE);



    public Field() {
    }

    public void initDefaultState(String initFieldState) {
        String[] input = initFieldState.split("");
        IntStream.range(0, input.length)
                .forEach(i -> field.add(applySymbol(input[i])));

    }

    private Symbol applySymbol(String symbol) {

        if (symbol.equals("_")) return Symbol.EMPTY;

        turnCounter.getAndIncrement();
        if (symbol.equals("X")) return Symbol.X;
        if (symbol.equals("O")) return Symbol.O;

        throw new IllegalArgumentException();
    }

    private Symbol nextSymbol() {
        return turnCounter.getAndIncrement() % 2 == 0
                ? Symbol.X
                : Symbol.O;
    }

    public void applyNextTurn(String inputCoordinate) throws WrongCoordinateException, AlreadyOccupiedException {
        int fieldIndex = transformCoordinate(inputCoordinate);
        field.set(fieldIndex, nextSymbol());
    }

    public int transformCoordinate(String inputCoordinate) throws NumberFormatException, WrongCoordinateException, AlreadyOccupiedException {

        String[] input = inputCoordinate.split(" ");

        int x;
        int y;
        int fieldIndex;
        y = Integer.parseInt(input[0]) - 1;
        x = Integer.parseInt(input[1]) - 1;
        fieldIndex = x + y * dimension;

        if (x < 0 || y < 0 || x >= dimension || y >= dimension) {
            throw new WrongCoordinateException();
        }

        if (field.get(fieldIndex) != Symbol.EMPTY) {
            throw new AlreadyOccupiedException();
        }

        return fieldIndex;
    }


    // TODO: replace with streams
    public void print() {
        System.out.println("-".repeat(9));
        IntStream.range(0, field.size())
                .forEach(i -> {
                    if (i % dimension == 0) System.out.print("| ");
                    System.out.print(field.get(i) + " ");
                    if (i % dimension == dimension - 1) System.out.println("|");
                });

        System.out.println("-".repeat(9));
    }

    public int turnCount() {
        return turnCounter.get();
    }

    public Symbol get(int index) {
        return field.get(index);
    }

    public int getDimension() {
        return dimension;
    }

    public static class WrongCoordinateException extends Exception {
    }

    public static class AlreadyOccupiedException extends Exception {
    }
}
