package tictactoe;

import java.util.Random;

public class PlayerAI implements Player {

    private final Field field;
    private final GameUI gameUI;
    private final Random randomGenerator = new Random();

    public PlayerAI(Field field, GameUI gameUI) {
        this.field = field;
        this.gameUI = gameUI;
    }

    public int makeMove() {
        return makeRandomMove();
    }

    @Override
    public void announceTurn() {
        String announce = String.format(GameUI.AI_TURN_ANNOUNCE, GameController.Mode.EASY.toString().toLowerCase());
        gameUI.printMessage(announce);
    }

    private int makeRandomMove() {
        int[] moves = field.getPossibleMoves();
        int randomIndex = randomGenerator.nextInt(moves.length);
        return moves[randomIndex];
    }

}
