package me.empty;

public record User(String id, GameUI gameUI) implements Player {

    @Override
    public int makeMove() {
        int coordinateIndex = -1;
        do {
            try {

                String userInput = gameUI.readUserInput();
                coordinateIndex = parseUserInputToCoordinateIndex(userInput);

            } catch (NumberFormatException e) {
                gameUI.printMessage(GameUI.ENTER_NUMBERS);
            } catch (WrongInputCoordinateException e) {
                gameUI.printMessage(GameUI.WRONG_COORDINATES);
            }
        } while (coordinateIndex == -1);

        return coordinateIndex;
    }

    @Override
    public void announceTurn() {
        gameUI.printMessage(GameUI.USER_TURN_ANNOUNCE);
    }

    public int parseUserInputToCoordinateIndex(String input) throws WrongInputCoordinateException {

        String[] coordinate = input.split(" ");
        int y = Integer.parseInt(coordinate[0]) - 1;
        int x = Integer.parseInt(coordinate[1]) - 1;
        checkEnteredCoordinate(x, y);

        return x + y * Field.DIMENSION;
    }

    private void checkEnteredCoordinate(int x, int y) throws WrongInputCoordinateException {
        if (x < 0 || x >= Field.DIMENSION || y < 0 || y >= Field.DIMENSION) {
            throw new WrongInputCoordinateException();
        }
    }

    public static class WrongInputCoordinateException extends Exception {
    }
}

