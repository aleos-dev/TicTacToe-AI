package tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Field {
    public enum Sign {
        EMPTY, X, O
    }

    public static final int FIELD_SIZE = 9;
    public static final int DIMENSION = (int) Math.sqrt(FIELD_SIZE);

    private final List<Sign> field;
    private final AtomicInteger turnCounter;


    public Field() {
        this.field = new ArrayList<>(FIELD_SIZE);
        this.turnCounter = new AtomicInteger(0);
        initField();
    }


    public void placeSign(int coordinateIndex) throws AlreadyOccupiedException {

        if (isOccupied(coordinateIndex)) {
            throw new AlreadyOccupiedException();
        }

        field.set(coordinateIndex, getCurrentPlayerSign());
    }

    public int[] getPossibleMoves() {
        int possibleMoveCount = FIELD_SIZE - turnCount();
        int[] possibleMoves = new int[possibleMoveCount];
        AtomicInteger index = new AtomicInteger(0);
        IntStream.range(0, FIELD_SIZE)
                .filter(i -> field.get(i) == Sign.EMPTY)
                .forEach(i -> possibleMoves[index.getAndIncrement()] = i);

        return possibleMoves;
    }

    public void printField() {
        String lineSeparator = "-".repeat(9);
        System.out.println(lineSeparator);

        IntStream.range(0, field.size())
                .forEach(i -> {
                    if (i % DIMENSION == 0) System.out.print("| ");
                    Sign value = field.get(i);
                    System.out.print(value == Sign.EMPTY ? "  " : value + " ");
                    if (i % DIMENSION == DIMENSION - 1) System.out.println("|");
                });

        System.out.println(lineSeparator);
    }

    public boolean isOccupied(int index) {
        return field.get(index) != Sign.EMPTY;
    }


    public int turnCount() {
        return turnCounter.get();
    }

    public Sign get(int index) {
        return field.get(index);
    }


    private void initField() {
        IntStream.range(0, FIELD_SIZE)
                .forEach(i -> field.add(Sign.EMPTY));
    }

    private Sign getCurrentPlayerSign() {
        return turnCounter.getAndIncrement() % 2 == 0
                ? Sign.X
                : Sign.O;
    }

    public static class AlreadyOccupiedException extends Exception {
    }
}
