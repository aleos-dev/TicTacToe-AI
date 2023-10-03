package tictactoe;

import java.util.List;
import java.util.Scanner;

public class GameUI {
    private final Scanner scanner;
    private Field field;

    public static final String PROMPT_FOR_COMMAND = "Input command: ";
    public static final String USER_TURN_ANNOUNCE = "Enter the coordinates: ";
    public static final String AI_TURN_ANNOUNCE = "Making move level \"%s\"" + "%n";
    public static final String ENTER_NUMBERS = "You should enter numbers!";
    public static final String WRONG_COORDINATES = "Coordinates should be from 1 to 3!";
    public static final String BAD_PARAMETERS = "Bad parameters!";
    public static final String CELL_OCCUPIED = "This cell is occupied! Choose another one!";
    public static final String DRAW = "Draw";
    public static final String WIN_MESSAGE = "%s wins";

    private final List<String> possibleCommands = List.of("start", "exit");
    private final List<String> possiblePlayers = List.of("user", "easy", "medium", "hard");

    public GameUI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void printMessage(String message) {
        System.out.print(message);
    }

    public boolean announceGameOver(String message, Field.CellState cellState) {
        message = String.format(message, cellState.toString());
        return announceGameOver(message);
    }

    public boolean announceGameOver(String message) {
        field.printBoard();
        System.out.println(message);
        return true;
    }

    public String readUserInput() {
        return scanner.nextLine();
    }

    public String readUserInput(String message) {
        System.out.print(message);
        return readUserInput().trim();
    }


    public void startMenu() {
        boolean isReceivedExitCommand = false;
        do {
            String userInput;
            String[] commands;
            boolean isCommandsValid;
            do {
                userInput = readUserInput(PROMPT_FOR_COMMAND);
                commands = userInput.split(" ");

                isCommandsValid = validateCommand(commands);
                if (!isCommandsValid) {
                    printMessage(BAD_PARAMETERS);
                }

            } while (!isCommandsValid);

            if (commands[0].equals("start")) {
                String playerOne = commands[1];
                String playerTwo = commands[2];
                field = new Field();
                GameController gc = new GameController(field, playerOne, playerTwo, this);

                gc.startGame();
            } else if (commands[0].equals("exit")) {
                isReceivedExitCommand = true;
            }
        } while (!isReceivedExitCommand);

    }

    private boolean validateCommand(String[] commands) {
        return (commands.length == 1 && "exit".equals(commands[0])) ||
                (commands.length == 3 &&
                        possibleCommands.contains(commands[0]) &&
                        possiblePlayers.contains(commands[1]) &&
                        possiblePlayers.contains(commands[2]));
    }
}
