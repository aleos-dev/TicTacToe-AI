# Tic-Tac-Toe in Java

## Overview

This repository contains a Java-based Tic-Tac-Toe game. It features a text-based user interface, and supports both player vs player and player vs AI game modes. The AI has different levels of difficulty: easy, medium, and hard.

## Features

- Text-based User Interface
- Player vs Player gameplay
- Player vs AI gameplay
- Different levels of AI difficulty: Easy, Medium, Hard
- Code separated into classes

## Code Structure

- `Main.java`: The entry point of the program.
- `GameController.java`: Manages the game logic.
- `Field.java`: Represents the game board.
- `GameUI.java`: Handles user interface tasks.
- `Player.java`: Abstract class representing a player.
- `User.java`, `EasyAI.java`, `MediumAI.java`, `HardAI.java`: Classes extending Player, implementing different types of players.
- `WinChecker.java`: Utility class for checking win conditions.
```

## Example
Input command: > start hard user
Making move level "hard"
---------
|       |
| X     |
|       |
---------
Enter the coordinates: > 2 2
---------
|       |
| X O   |
|       |
---------
Making move level "hard"
---------
|   X   |
| X O   |
|       |
---------
Enter the coordinates: > 3 2
---------
|   X   |
| X O   |
|   O   |
---------
Making move level "hard"
---------
| X X   |
| X O   |
|   O   |
---------
Enter the coordinates: > 3 1
---------
| X X   |
| X O   |
| O O   |
---------
Making move level "hard"
---------
| X X X |
| X O   |
| O O   |
---------
X wins

Input command: > exit
```