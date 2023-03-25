package ui;

import java.util.Scanner;

import core.Connect4Logic;

public class Connect4TextConsole {
    private Connect4Logic game;
    private int currentPlayer;

    public Connect4TextConsole() {
        this.game = new Connect4Logic();
        this.currentPlayer = 1;
    }

    public void start() {
        boolean gameInProgress = true;

        System.out.println("Begin Game. ");

        while (gameInProgress) {
            game.print();
            String color;
            if (currentPlayer == 1) {
                color = " x ";
            }
            else {
                color = " o ";
            }
    
            System.out.println(getPlayerName(currentPlayer) + "-your turn. Choose a column number from 1-7.");

            Scanner scanner = new Scanner(System.in);
            int desiredCol = scanner.nextInt() - 1;

            boolean moveMade = game.addPiece(desiredCol, color);

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
                currentPlayer = getOpponent(currentPlayer);
            }

        }

    }

    private String getPlayerName(int player) {
        return currentPlayer == 1 ? "PlayerX" : "PlayerO";
    }
    
    private int getOpponent(int player) {
        return currentPlayer == 1 ? 2 : 1;
    }

    public static void main(String[] args) {
        Connect4TextConsole gameConsole = new Connect4TextConsole();
        gameConsole.start();

    }
}