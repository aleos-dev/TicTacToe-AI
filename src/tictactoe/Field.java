package tictactoe;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Field {
    public enum CellState {
        EMPTY, X, O
    }

    public static final int FIELD_SIZE = 9;
    public static final int DIMENSION = (int) Math.sqrt(FIELD_SIZE);

    private final CellState[] board;
    private int turnCount;


    public Field() {
        board = new CellState[FIELD_SIZE];
        initializeBoard();
        turnCount = 0;
    }

    public Field cloneField() {
        Field clone = new Field();

        System.arraycopy(this.board, 0, clone.board, 0, FIELD_SIZE);
        clone.turnCount = this.turnCount;

        return clone;
    }

    public void markCell(int coordinateIndex) {
        if (isCellOccupied(coordinateIndex)) {
            throw new IllegalArgumentException();
        }

        board[coordinateIndex] = getCurrentPlayerSign();
        turnCount++;
    }

    public int[] getPossibleMoves() {
        return IntStream.range(0, FIELD_SIZE)
                .filter(index -> board[index] == CellState.EMPTY)
                .toArray();
    }

    public void printBoard() {
        String horizontalDivider = "-".repeat(9);
        System.out.println(horizontalDivider);

        IntStream.range(0, board.length)
                .forEach(i -> {
                    if (i % DIMENSION == 0) System.out.print("| ");
                    CellState value = board[i];
                    System.out.print(value == CellState.EMPTY ? "  " : value + " ");
                    if (i % DIMENSION == DIMENSION - 1) System.out.println("|");
                });

        System.out.println(horizontalDivider);
    }

    public boolean isCellOccupied(int index) {
        return board[index] != CellState.EMPTY;
    }


    public int turnCount() {
        return turnCount;
    }

    public void incrementTurnCount() {
        turnCount++;
    }

    public CellState getCellState(int index) {
        return board[index];
    }


    private void initializeBoard() {
        Arrays.fill(board, CellState.EMPTY);
    }

    public CellState getCurrentPlayerSign() {
        return turnCount % 2 == 0
                ? CellState.X
                : CellState.O;
    }

    public CellState getNextPlayerSign() {
        return turnCount % 2 != 0
                ? CellState.X
                : CellState.O;
    }
}
