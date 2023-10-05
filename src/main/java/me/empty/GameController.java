package me.empty;

public class GameController {
    private final Field field;
    private final GameUI gameUI;
    private Player playerOne;
    private Player playerTwo;

    private Player currentPlayer;

    public GameController(Field field, String playerOne, String playerTwo, GameUI gameUI) {
        this.field = field;
        this.gameUI = gameUI;
        initPlayers(playerOne, playerTwo);
    }


    public void startGame() {
        while (!isGameOver()) {
            executeTurn();
        }
    }

    private void executeTurn() {
        currentPlayer = getCurrentPlayer();
        field.printBoard();
        currentPlayer.announceTurn();
        handleTurn();
    }

    private Player getCurrentPlayer() {
        return (field.turnCount() % 2 == 0) ? playerOne : playerTwo;
    }

    private void handleTurn() {
        boolean trySuccess;
        do {
            trySuccess = tryToMarkCell();
        } while (!trySuccess);
    }

    private boolean tryToMarkCell() {
        try {
            field.markCell(currentPlayer.makeMove());
            return true;
        } catch (IllegalArgumentException e) {
            gameUI.printMessage(GameUI.CELL_OCCUPIED);
            return false;
        }
    }

    public boolean isGameOver() {
        return checkGameOverConditions(GameUI.DRAW, GameUI.WIN_MESSAGE);
    }

    private boolean checkGameOverConditions(String drawMessage, String winMessage) {
        if (isPlayerWin(Field.CellState.O))
            return gameUI.announceGameOver(winMessage, Field.CellState.O);
        if (isPlayerWin(Field.CellState.X))
            return gameUI.announceGameOver(winMessage, Field.CellState.X);
        if (field.turnCount() == Field.FIELD_SIZE) return gameUI.announceGameOver(drawMessage);
        return false;
    }

    private boolean isPlayerWin(Field.CellState cellState) {
        return WinChecker.isPlayerWin(field, cellState);
    }

    private void initPlayers(String playerOne, String playerTwo) {
        this.playerOne = createPlayer(playerOne);
        this.playerTwo = createPlayer(playerTwo);
    }

    private Player createPlayer(String type) {
        return switch (type) {
            case "user" -> new User("user_Bob", gameUI);
            case "easy" -> new EasyAI(field, gameUI);
            case "medium" -> new MediumAI(field, gameUI);
            case "hard" -> new HardAI(field, gameUI);
            default -> throw new IllegalArgumentException("Invalid player type");
        };
    }
}
