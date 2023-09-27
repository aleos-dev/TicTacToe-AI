package tictactoe;

import java.util.Random;

public class PlayerAI implements Player {
    private static final String AI_TURN_ANNOUNCE = "Making move level \"%s\"";

    private final Field field;
    private final Random randomGenerator = new Random();

    public PlayerAI(Field field) {
        this.field = field;
    }


    public int makeMove() {
        return makeRandomMove();
    }

    @Override
    public void announceTurn() {
        System.out.printf((AI_TURN_ANNOUNCE) + "%n", GameController.Mode.EASY.toString().toLowerCase());
    }


    private int makeRandomMove() {
        int[] moves = field.getPossibleMoves();
        int randomIndex = randomGenerator.nextInt(moves.length);
        return moves[randomIndex];
    }

}
