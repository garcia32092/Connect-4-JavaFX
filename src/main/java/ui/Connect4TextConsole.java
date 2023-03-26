/**
 * 2023SpringB-X-SER216-15142 / ASU Online
 * Assignment: Project: Deliverable 2
 * @author Randy Elias Garcia
 * Description: The Connect4TextConsole class represents the text-based console interface for the Connect 4 game.
 * 
 * @version 1.0.0
 */

package ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import core.Connect4Logic;

public class Connect4TextConsole {
    private Connect4Logic game;
    private int currentPlayer;

    /**
     * Constructs a new Connect4TextConsole instance and initializes the game with a new Connect4Logic object.
     */
    public Connect4TextConsole() {
        this.game = new Connect4Logic();
        this.currentPlayer = 1;
    }

    /**
     * Starts the Connect 4 game and prompts players to make their moves until the game is over.
     */
    public void start() {
        boolean gameInProgress = true;

        System.out.println("Begin Game. ");

        // Loop until game is over
        while (gameInProgress) {
            // Print the current state of the game board
            game.print();

            // Determine the color of the current player
            String color;
            if (currentPlayer == 1) {
                color = " x ";
            }
            else {
                color = " o ";
            }
    
            // Prompt the current player for their move
            System.out.println(getPlayerName(currentPlayer) + " - your turn. Choose a column number from 1-7.");

            // Get the desired column from the current player
            Scanner scanner = new Scanner(System.in);
            int desiredCol = -1;
            try {
                desiredCol = scanner.nextInt() - 1;
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer from 1-7.");
            }

            // Check if the current player's piece is added to the desired column
            boolean moveMade = game.addPiece(desiredCol, color);

            // Once a move has been successfully made, check if the game has been won or drawn, and end the game if necessary
            if (moveMade) {
                if (game.checkForWin(desiredCol, color)) {
                    game.print();
                    gameInProgress = false;
                    if (currentPlayer == 1) {
                        System.out.println("Player X Won the Game");
                    }
                    else {
                        System.out.println("Player O Won the Game");
                    }
                }
                if (!game.checkForWin(desiredCol, color) && game.checkForDraw()) {
                    game.print();
                    gameInProgress = false;
                    System.out.println("This game ends in a draw.");
                }
                // Switch to the next player's turn
                currentPlayer = getOpponent(currentPlayer);
            }

        }

    }

    /**
     * Returns the name of the player associated with the given player number.
     * 
     * @param player the player number (1 or 2)
     * @return the name of the player
     */
    private String getPlayerName(int player) {
        return currentPlayer == 1 ? "Player X" : "Player O";
    }
    
    /**
     * Returns the opponent of the given player.
     * 
     * @param player the player number (1 or 2)
     * @return the opponent's player number (2 or 1)
     */
    private int getOpponent(int player) {
        return currentPlayer == 1 ? 2 : 1;
    }

    /**
     * The main method to start the Connect 4 game.
     * 
     * @param args
     */
    public static void main(String[] args) {
        Connect4TextConsole gameConsole = new Connect4TextConsole();
        gameConsole.start();

    }
}