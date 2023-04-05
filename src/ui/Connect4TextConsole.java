package ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import core.Connect4ComputerPlayer;
import core.Connect4Logic;

/**
 * 2023SpringB-X-SER216-15142 / ASU Online
 * Assignment: Project: Deliverable 2
 * @author Randy Elias Garcia
 * Description: The Connect4TextConsole class represents the text-based console interface for the Connect 4 game.
 * 
 * @version 1.0.0
 */

public class Connect4TextConsole {
    private Connect4Logic game;
    private int currentPlayer;
    private Connect4ComputerPlayer computer;

    /**
     * Constructs a new Connect4TextConsole instance and initializes the game with a new Connect4Logic object and a new Connect4ComputerPlayer object.
     */
    public Connect4TextConsole() {
        this.game = new Connect4Logic();
        this.computer = new Connect4ComputerPlayer(" o ");
        this.currentPlayer = 1;
    }

    /**
     * Starts the Connect 4 game and prompts the player to decide whether to play against another player or the computer.
     * The game loop will then prompt players to make their moves until the game is over.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        char playingAgainst = 'z';

        // Do-while loop prompts user to choose between versus another player or computer
        do {
            System.out.println("Enter 'P' if you want to play against another player; enter 'C' to play against computer.");
            playingAgainst = scanner.next().charAt(0);
        } while ((playingAgainst != 'C' && playingAgainst != 'c') && (playingAgainst != 'p' && playingAgainst != 'P'));

        // "Begin game" print statement depends on 1 or 2 player game
        if (playingAgainst == 'C' || playingAgainst == 'c') {
            System.out.println("Begin game against computer.");
        }
        else {
            System.out.println("Begin 2 player game.");
        }
        
        // Boolean variable begins game loop
        boolean gameInProgress = true;

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
    
            // Prompt the current player for their move depending on 1 or 2 player game mode
            if ((playingAgainst == 'C' || playingAgainst == 'c') && currentPlayer == 1) {
                System.out.println("It is your turn. Choose a column number from 1-7.");
            }

            if (playingAgainst == 'P' || playingAgainst == 'p') {
                System.out.println(getPlayerName(currentPlayer) + " - your turn. Choose a column number from 1-7.");
            }
            
            // Initialize int variable for desired column
            int desiredCol = -1;

            // Make the computer's move if chosen game mode is 1 player and it is the computer's turn
            if ((playingAgainst == 'C' || playingAgainst == 'c') && currentPlayer == 2) {
                desiredCol = computer.makeMove(game.board);
                System.out.println("Computer player chose column: " + (desiredCol + 1));
            }
            // Get the desired column from the current player
            else {
                try {
                    desiredCol = scanner.nextInt() - 1;
                }
                catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter an integer from 1-7.");
                    scanner.next();
                }
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