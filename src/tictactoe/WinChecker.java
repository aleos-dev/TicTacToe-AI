package tictactoe;

import java.util.stream.IntStream;

public class WinChecker {
    public static boolean isPlayerWin(Field field, Field.Sign sign) {
        return IntStream.range(0, Field.DIMENSION)
                .anyMatch(i ->
                        isPlayerCloseRow(field, sign, i)
                                || isPlayerCloseColumn(field, sign, i)
                                || isPlayerCloseDiagonal(field, sign)
                );
    }

    private static boolean isPlayerCloseRow(Field field, Field.Sign sign, int row) {
        return IntStream.range(0, Field.DIMENSION)
                .allMatch(i -> field.get(row * Field.DIMENSION + i) == sign);
    }

    private static boolean isPlayerCloseColumn(Field field, Field.Sign sign, int column) {
        return IntStream.range(0, Field.DIMENSION)
                .allMatch(i -> field.get(i * Field.DIMENSION + column) == sign);
    }

    private static boolean isPlayerCloseDiagonal(Field field, Field.Sign sign) {
        boolean mainDiagonal = IntStream.range(0, Field.DIMENSION).allMatch(i -> field.get(i * Field.DIMENSION + i) == sign);
        boolean secondaryDiagonal = IntStream.range(0, Field.DIMENSION).allMatch(i -> field.get((i + 1) * Field.DIMENSION - i - 1) == sign);
        return mainDiagonal || secondaryDiagonal;
    }
}
