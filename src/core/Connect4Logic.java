package core;

/**
 * 
 * 2023SpringB-X-SER216-15142 / ASU Online
 * Assignment: Project: Deliverable 2
 * @author Randy Elias Garcia
 * Description: A class representing the logic for the Connect 4 game.
 * 
 * @version 1.0.0
 * 
 */

public class Connect4Logic {
    
    /**
     * The number of rows on the Connect 4 board.
     */
    public final int rows = 6;

    /**
     * The number of columns on the Connect 4 board.
     */
    public final int columns = 7;

    /**
     * A 2D array of Piece objects representing the Connect 4 board.
     */
    public Piece[][] board;

    /**
     * Constructs a Connect4Logic object and initializes the board.
     */
    public Connect4Logic() {
        board = new Piece[rows][columns];
        setUpGame();
    }

    /**
     * Sets up the board by initializing all positions to null.
     */
    public void setUpGame() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                board[row][col] = null;
            }
        }
    }

    /**
     * Prints the current state of the Connect 4 board to the console with a divider underneath.
     */
    public void print() {
        for (int row = 0; row < rows; row++) {
            System.out.print("|");
            for (int col = 0; col < columns; col++) {
                if (board[row][col] == null) {
                    System.out.print("   ");
                }
                else {
                    System.out.print(board[row][col].getColor());
                }
                System.out.print("|");
            }
            System.out.println();
        }
        System.out.println("-----------------------------");
    }

    
    /**
     * Attempts to add a piece to the board at the specified column with the specified color.
     * @param addToCol the column to add the piece to
     * @param color the color of the piece to add
     * @return true if the piece was successfully added, false otherwise
     */
    public boolean addPiece(int addToCol, String color) {
        if (addToCol >= 0 && addToCol < columns) {
            if (board[0][addToCol] == null) {
                boolean pieceAdded = false;
                for (int row = rows - 1; row >= 0; row--) {
                    if (board[row][addToCol] == null) {
                        board[row][addToCol] = new Piece();
                        board[row][addToCol].setColor(color);
                        pieceAdded = true;
                        break;
                    }
                }
                return pieceAdded;
            }
            else {
                System.out.println("This column is full.");
                return false;
            }
        }
        else {
            System.out.println("Piece cannot be added here.");
            return false;
        }
    }

    /**
     * Checks the board for a win condition after a piece has been added at the specified column with the specified color.
     * @param lastMoveCol the column where the last piece was added
     * @param winColor the color of the winning pieces
     * @return true if a win condition has been met, false otherwise
     */
    public boolean checkForWin(int lastMoveCol, String winColor) {
        boolean winner = false;

        for (int row = 0; row < rows; row++) {
            if (board[row][lastMoveCol] != null) {

                // check downwards win streak
                int winCondition = 0;
                for (int winStreak = row; winStreak < rows; winStreak++) {
                    if (board[winStreak][lastMoveCol].getColor().equals(winColor)) {
                        winCondition++;
                        // System.out.println("Win condition # for " + winColor + " checking downwards is: " + winCondition);
                        if (winCondition == 4) {
                            winner = true;
                        }
                    }
                    else {
                        winCondition = 0;
                    }
                }

                // check horizontal win streak
                winCondition = 0;
                for (int winStreak = 0; winStreak < columns; winStreak++) {
                    if (board[row][winStreak] != null && board[row][winStreak].getColor().equals(winColor)) {
                        winCondition++;
                        // System.out.println("Win condition # for " + winColor + " checking horizontal is: " + winCondition);
                        if (winCondition == 4) {
                            winner = true;
                        }
                    }
                    else {
                        winCondition = 0;
                    }
                }

                // Check for right diagonal win streak
                winCondition = 0;
                for (int rowStreak = row - 3, colStreak = lastMoveCol + 3; rowStreak <= row + 3 && colStreak >= lastMoveCol - 3; rowStreak++, colStreak--) {
                    if (rowStreak < 0 || colStreak >= columns) {
                        continue;
                    }
                    if (rowStreak >= rows || colStreak < 0) {
                        break;
                    }

                    if (board[rowStreak][colStreak] != null && board[rowStreak][colStreak].getColor().equals(winColor)) {
                        winCondition++;
                        // System.out.println("Win condition # for " + winColor + " checking right diagonal is: " + winCondition);
                        if (winCondition == 4) {
                            winner = true;
                        }
                    }
                    else {
                        winCondition = 0;
                    }
                }

                // Check for left diagonal win streak
                winCondition = 0;
                for (int rowStreak = row - 3, colStreak = lastMoveCol - 3; rowStreak <= row + 3 && colStreak <= lastMoveCol + 3; rowStreak++, colStreak++) {
                    if (rowStreak < 0 || colStreak < 0) {
                        continue;
                    }
                    if (rowStreak >= rows || colStreak >= columns) {
                        break;
                    }

                    if (board[rowStreak][colStreak] != null && board[rowStreak][colStreak].getColor().equals(winColor)) {
                        winCondition++;
                        // System.out.println("Win condition # for " + winColor + " checking left diagonal is: " + winCondition);
                        if (winCondition == 4) {
                            winner = true;
                        }
                    }
                    else {
                        winCondition = 0;
                    }
                }

                break;
            }
        }

        return winner;
    }

    /**
     * Checks if the game board is in a draw state.
     *
     * @return true if the game is in a draw state, false otherwise.
     */
    public boolean checkForDraw() {
        int maxMoves = rows * columns;
        int totalMoves = 0;
    
        // Count total number of moves
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (board[row][col] != null) {
                    totalMoves++;
                }
            }
        }
    
        // Check for draw condition
        if (totalMoves == maxMoves) {
            return true;
        }
        else {
            return false;
        }
    }

}
