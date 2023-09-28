package tictactoe;

public class GameController {
    private final Field field;
    private final GameUI gameUI;
    private Player playerOne;
    private Player playerTwo;

    private Player currentPlayer;

    enum Mode {
        NO_WIN, EASY, MEDIUM, HARD
    }


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
            trySuccess = tryPlaceSign();
        } while (!trySuccess);
    }

    private boolean tryPlaceSign() {
        try {
            field.placeSign(currentPlayer.makeMove());
            return true;
        } catch (Field.AlreadyOccupiedException e) {
            gameUI.printMessage(GameUI.CELL_OCCUPIED);
            return false;
        }
    }

    public boolean isGameOver() {
        return checkGameOverConditions(GameUI.DRAW, GameUI.WIN_MESSAGE);
    }

    private boolean checkGameOverConditions(String drawMessage, String winMessage) {
        if (isPlayerWin(Field.Sign.O)) return gameUI.announceGameOver(winMessage, Field.Sign.O);
        if (isPlayerWin(Field.Sign.X)) return gameUI.announceGameOver(winMessage, Field.Sign.X);
        if (field.turnCount() == Field.FIELD_SIZE) return gameUI.announceGameOver(drawMessage);
        return false;
    }

    private boolean isPlayerWin(Field.Sign sign) {
        return WinChecker.isPlayerWin(field, sign);
    }

    private void initPlayers(String playerOne, String playerTwo) {
        this.playerOne = createPlayer(playerOne);
        this.playerTwo = createPlayer(playerTwo);
    }

    private Player createPlayer(String type) {
        return switch (type) {
            case "user" -> new User("user_Bob", gameUI);
            case "easy" -> new PlayerAI(field, gameUI);
            default -> throw new IllegalArgumentException("Invalid player type");
        };
    }
}
