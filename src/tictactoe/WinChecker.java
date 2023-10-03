package tictactoe;

import java.util.stream.IntStream;

public class WinChecker {
    public static boolean isPlayerWin(Field field, Field.CellState cellState) {
        return IntStream.range(0, Field.DIMENSION)
                .anyMatch(i ->
                        isPlayerCloseRow(field, cellState, i)
                                || isPlayerCloseColumn(field, cellState, i)
                                || isPlayerCloseDiagonal(field, cellState)
                );
    }

    private static boolean isPlayerCloseRow(Field field, Field.CellState cellState, int row) {
        return IntStream.range(0, Field.DIMENSION)
                .allMatch(i -> field.getCellState(row * Field.DIMENSION + i) == cellState);
    }

    private static boolean isPlayerCloseColumn(Field field, Field.CellState cellState, int column) {
        return IntStream.range(0, Field.DIMENSION)
                .allMatch(i -> field.getCellState(i * Field.DIMENSION + column) == cellState);
    }

    private static boolean isPlayerCloseDiagonal(Field field, Field.CellState cellState) {
        boolean mainDiagonal = IntStream.range(0, Field.DIMENSION).allMatch(i -> field.getCellState(i * Field.DIMENSION + i) == cellState);
        boolean secondaryDiagonal = IntStream.range(0, Field.DIMENSION).allMatch(i -> field.getCellState((i + 1) * Field.DIMENSION - i - 1) == cellState);
        return mainDiagonal || secondaryDiagonal;
    }
}
