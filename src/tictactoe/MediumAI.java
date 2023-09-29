package tictactoe;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.Random;

public class MediumAI implements Player {

    private static final int NO_MOVE_FOUND = -1;

    private final Field field;
    private final GameUI gameUI;
    private final Random randomGenerator = new Random();

    public MediumAI(Field field, GameUI gameUI) {
        this.field = field;
        this.gameUI = gameUI;
    }


    public int makeMove() {
        return decideNextMove();
    }

    @Override
    public void announceTurn() {
        String announce = String.format(GameUI.AI_TURN_ANNOUNCE, "medium");
        gameUI.printMessage(announce);
    }

    private int makeRandomMove() {
        int[] moves = field.getPossibleMoves();
        int randomIndex = randomGenerator.nextInt(moves.length);
        return moves[randomIndex];
    }

    private int decideNextMove() {
        int nextMove = findOptimalMove(true);
        if (nextMove == NO_MOVE_FOUND) {
            nextMove = findOptimalMove(false);
        }
        return nextMove == NO_MOVE_FOUND ? makeRandomMove() : nextMove;
    }

    private int findOptimalMove(boolean forWinning) {
        int[] possibleMoves = field.getPossibleMoves();

        OptionalInt optimalMove = Arrays.stream(possibleMoves)
                .filter(moveIndex -> {
                    try {
                        Field tempField = field.cloneField();
                        if (!forWinning) {
                            tempField.incrementTurnCount();
                        }
                        Field.Sign sign = tempField.getCurrentPlayerSign();
                        tempField.placeSign(moveIndex);
                        return WinChecker.isPlayerWin(tempField, sign);
                    } catch (Field.AlreadyOccupiedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .findFirst();

        return optimalMove.orElse(NO_MOVE_FOUND);
    }
}
