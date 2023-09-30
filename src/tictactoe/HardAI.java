package tictactoe;

public class HardAI implements Player {

    private final Field field;
    private final GameUI gameUI;
    private Field.Sign maxPlayerSign;
    private Field.Sign minPlayerSign;

    public HardAI(Field field, GameUI gameUI) {
        this.field = field;
        this.gameUI = gameUI;
    }

    @Override
    public void announceTurn() {
        String announce = String.format(GameUI.AI_TURN_ANNOUNCE, "hard");
        gameUI.printMessage(announce);

    }

    @Override
    public int makeMove() {
        maxPlayerSign = field.getCurrentPlayerSign();
        minPlayerSign = field.getNextPlayerSign();

        try {

            int bestMove = -1;
            int bestScore = Integer.MIN_VALUE;

            int[] possibleMoves = field.getPossibleMoves();
            int moveCount = possibleMoves.length;

            for (int i = 0; i < moveCount; i++) {

                Field modifiedField = field.cloneField();
                modifiedField.placeSign(possibleMoves[i]);

                int score = minimax(modifiedField, moveCount - 1, false);

                if (bestScore < score) {
                    bestScore = score;
                    bestMove = possibleMoves[i];
                }
            }

            return bestMove;

        } catch (Field.AlreadyOccupiedException e) {
            throw new RuntimeException(e);
        }
    }

    private int minimax(Field field, int depth, boolean isMaximizingPlayer) throws Field.AlreadyOccupiedException {
        int score = evaluateBoard(field);
        if (score != 0) {
            return score - depth;
        }
        if (depth == 0) {
            return 0;
        }

        int[] possibleMoves = field.getPossibleMoves();
        int moveCount = possibleMoves.length;

        int bestScore = isMaximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int possibleMove : possibleMoves) {

            Field modifiedField = field.cloneField();
            modifiedField.placeSign(possibleMove);

            int currentScore = minimax(modifiedField, depth - 1, !isMaximizingPlayer);

            if (isMaximizingPlayer) {
                bestScore = Math.max(currentScore, bestScore);
            } else {
                bestScore = Math.min(currentScore, bestScore);
            }
        }

        return bestScore;
    }

    private int evaluateBoard(Field field) {
        if (WinChecker.isPlayerWin(field, maxPlayerSign)) {
            return 10;
        }
        if (WinChecker.isPlayerWin(field, minPlayerSign)) {
            return -10;
        }
        return 0;
    }
}
