package me.empty;

import java.util.OptionalInt;
import java.util.Random;

public class MediumAI implements Player {

    private final Field field;
    private final GameUI gameUI;
    private final Random randomGenerator = new Random();

    public MediumAI(Field field, GameUI gameUI) {
        this.field = field;
        this.gameUI = gameUI;
    }


    @Override
    public void announceTurn() {
        String announce = String.format(GameUI.AI_TURN_ANNOUNCE, "medium");
        gameUI.printMessage(announce);
    }

    public int makeMove() {
        return findOptimalMoveFor(true)
                .orElse(findOptimalMoveFor(false)
                        .orElse(makeRandomMove()));
    }

    /**
     * Finds the optimal move for a player on the board.
     *
     * @param isSelf Boolean flag indicating if the optimal move should be found for the AI (true)
     *               or for the opponent (false).
     * @return An OptionalInt containing the index of the optimal move if one is found;
     *         otherwise, returns an empty OptionalInt.
     */
    private OptionalInt findOptimalMoveFor(boolean isSelf) {


        for (int index = 0; index < Field.FIELD_SIZE; index++) {

            if (field.isCellOccupied(index)) {
                continue;
            }

            Field modifiedField = field.cloneField();


            Field.CellState playerCellState = determinePlayerSign(isSelf, modifiedField);

            makeMoveOnField(modifiedField, index);

            if (WinChecker.isPlayerWin(modifiedField, playerCellState)) {
                return OptionalInt.of(index);
            }
        }

        return OptionalInt.empty();
    }

    private Field.CellState determinePlayerSign(boolean isSelf, Field modifiedField) {
        Field.CellState playerCellState;
        if (isSelf) {
            playerCellState = field.getCurrentPlayerSign();
        } else {
            playerCellState = field.getNextPlayerSign();
            modifiedField.incrementTurnCount();
        }
        return playerCellState;
    }

    private void makeMoveOnField(Field modifiedField, int index) {
        modifiedField.markCell(index);
    }

    private int makeRandomMove() {
        int[] moves = field.getPossibleMoves();
        int randomIndex = randomGenerator.nextInt(moves.length);
        return moves[randomIndex];
    }
}
