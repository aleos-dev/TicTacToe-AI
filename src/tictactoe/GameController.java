package tictactoe;

public class GameController {
    private final Field field;
    private final Player playerOne;
    private final Player playerTwo;

    private Player currentPlayer;

    enum Mode {
        NO_WIN, EASY, MEDIUM, HARD
    }

    private static final String CELL_OCCUPIED = "This cell is occupied! Choose another one!";
    private static final String DRAW = "Draw";
    private static final String WIN_MESSAGE = "%s wins";

    public GameController(Field field, Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.field = field;
    }

    public void startGame() {
        while (!isGameOver()) {
            currentPlayer = getCurrentPlayer();
            field.printField();
            executeTurn();
        }
    }


    private void executeTurn() {
        currentPlayer.announceTurn();
        handleTurn();
    }

    private Player getCurrentPlayer() {
        return field.turnCount() % 2 == 0 ? playerOne : playerTwo;
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
            System.out.println(CELL_OCCUPIED);
            return false;
        }
    }

    public boolean isGameOver() {
        return checkGameOverConditions(DRAW, WIN_MESSAGE);
    }

    private boolean checkGameOverConditions(String drawMessage, String winMessage) {
        if (isPlayerWin(Field.Sign.O)) return announceGameOver(Field.Sign.O, winMessage);
        if (isPlayerWin(Field.Sign.X)) return announceGameOver(Field.Sign.X, winMessage);
        if (field.turnCount() == Field.FIELD_SIZE) return announceGameOver(drawMessage);
        return false;
    }

    private boolean announceGameOver(Field.Sign sign, String message) {
        field.printField();
        System.out.printf(message, sign);
        return true;
    }

    private boolean announceGameOver(String message) {
        field.printField();
        System.out.println(message);
        return true;
    }

    private boolean isPlayerWin(Field.Sign sign) {
        return WinChecker.isPlayerWin(field, sign);
    }

}
