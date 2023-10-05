package me.empty;


public class HardAI implements Player {

    private final Field field;
    private final GameUI gameUI;
    private Field.CellState maximizingPlayerCellState;
    private Field.CellState minimizingPlayerCellState;

    public HardAI(Field field, GameUI gameUI) {
        this.field = field;
        this.gameUI = gameUI;
    }

    @Override
    public void announceTurn() {
        String announce = String.format(GameUI.AI_TURN_ANNOUNCE, "hard");
        gameUI.printMessage(announce);

    }

    /**
     * Makes the best possible move for the AI, considering the current state of the game field.
     * <p>
     * This method utilizes the minimax algorithm with alpha-beta pruning to determine the best possible
     * move for the AI. The best move is calculated based on the evaluation of possible future game states.
     * </p>
     *
     * @return An integer representing the index on the game board where the AI should place its sign.
     */
    @Override
    public int makeMove() {
        maximizingPlayerCellState = field.getCurrentPlayerSign();
        minimizingPlayerCellState = field.getNextPlayerSign();

        int bestMove = -1;
        int bestScore = Integer.MIN_VALUE;

        int depth = Field.FIELD_SIZE - field.turnCount();
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        for (int index = 0; index < Field.FIELD_SIZE; index++) {
            if (field.isCellOccupied(index)) {
                continue;
            }

            Field modifiedField = cloneAndPlaceSign(field, index);

            int score = miniMaxAlgorithm(modifiedField, depth - 1, alpha, beta, false);
            if (bestScore < score) {
                bestScore = score;
                bestMove = index;
            }
        }

        return bestMove;
    }

    /**
     * Implementation of the minimax algorithm with alpha-beta pruning for game state evaluation.
     * <p>
     * This is a recursive function that simulates all possible game states to a certain depth to
     * evaluate the best possible outcome for the AI, taking into consideration both maximizing and
     * minimizing player turns.
     * </p>
     *
     * @param field              The current game field object.
     * @param depth              The depth of the game tree to explore.
     * @param alpha              The best score that the maximizing player can achieve.
     * @param beta               The best score that the minimizing player can achieve.
     * @param isMaximizingPlayer Boolean flag to indicate if it's the maximizing player's turn.
     * @return The evaluation score of the board after considering all possible game states up to the given depth.
     */
    private int miniMaxAlgorithm(Field field, int depth, int alpha, int beta, boolean isMaximizingPlayer) {
        int score = evaluateBoard(field);
        if (score != 0) {
            return score + depth;
        }
        if (isLeafReached(depth)) {
            return 0;
        }

        int bestScore = isMaximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int index = 0; index < Field.FIELD_SIZE; index++) {
            if (field.isCellOccupied(index)) {
                continue;
            }

            Field modifiedField = cloneAndPlaceSign(field, index);

            int currentScore = miniMaxAlgorithm(modifiedField, depth - 1, alpha, beta, !isMaximizingPlayer);

            if (isMaximizingPlayer) {
                bestScore = Math.max(bestScore, currentScore);
                alpha = Math.max(alpha, currentScore);
            } else {
                bestScore = Math.min(bestScore, currentScore);
                beta = Math.min(beta, currentScore);
            }

            if (isPruningNeeded(alpha, beta)) {
                break;
            }

        }

        return bestScore;
    }

    private boolean isPruningNeeded(int alpha, int beta) {
        return beta <= alpha;
    }

    private Field cloneAndPlaceSign(Field field, int index) {
        Field modifiedField = field.cloneField();
        modifiedField.markCell(index);

        return modifiedField;
    }

    private boolean isLeafReached(int depth) {
        return depth == 0;
    }

    private int evaluateBoard(Field field) {
        if (WinChecker.isPlayerWin(field, maximizingPlayerCellState)) {
            return 10;
        }
        if (WinChecker.isPlayerWin(field, minimizingPlayerCellState)) {
            return -10;
        }
        return 0;
    }
}
