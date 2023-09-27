package tictactoe;

public record User(String id) implements Player {

    private static final String USER_TURN_ANNOUNCE = "Enter the coordinates: ";

    @Override
    public int makeMove() {
        while (true) {
            try {
                String userInput = Main.getScanner().nextLine();

                int coordinateIndex;
                coordinateIndex = parseUserInputToCoordinateIndex(userInput);
                return coordinateIndex;

            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
            } catch (WrongInputCoordinateException e) {
                System.out.println("Coordinates should be from 1 to 3!");
            }
        }
    }

    @Override
    public void announceTurn() {
        System.out.print(USER_TURN_ANNOUNCE);
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

