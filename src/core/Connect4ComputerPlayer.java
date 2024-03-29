package core;

import java.util.Random;

/**
 * 2023SpringB-X-SER216-15142 / ASU Online
 * Assignment: Project: Deliverable 4
 * @author Randy Elias Garcia
 * Description: A class representing a computer player for a Connect 4 game.
 * 
 * @version 1.0.0
 */

public class Connect4ComputerPlayer {
    private final String color;

    /**
     * Constructs a new Connect4ComputerPlayer instance and initializes the color string variable.
     * @param color that the computer's color will be set to.
     */
    public Connect4ComputerPlayer(String color) {
        this.color = color;
    }

    /**
     * Gets the computer's color.
     * @return the color of the computer player's pieces.
     */
    public String getColor() {
        return color;
    }

    /**
     * Makes the next move for the computer player.
     * @param board the current state of the game board
     * @return the column where the computer player wants to place its next piece
     */
    public int makeMove(Piece[][] board) {
        Random random = new Random();
        int column;
        do {
            column = random.nextInt(board[0].length);
        } while (!isValidMove(board, column));
        return column;
    }

    /**
     * Determines if a move is valid.
     * @param board the current state of the game board
     * @param column the column where the piece would be added
     * @return true if the move is valid, false otherwise
     */
    private boolean isValidMove(Piece[][] board, int column) {
        if (board[0][column] != null) {
            return false;
        }
        return true;
    }
}