package core;

public class Connect4Logic {
    
    public final int rows = 6;
    public final int columns = 7;
    public Piece[][] board;
    int[][] grid;

    public Connect4Logic() {
        board = new Piece[rows][columns];
        setUpGame();
    }

    public void setUpGame() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                board[row][col] = null;
            }
        }
    }

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
            System.out.println("Peice cannot be added here.");
            return false;
        }
    }
    
    public boolean checkForWin(int lastMoveCol, String winColor) {
        boolean winner = false;

        for (int row = 0; row < rows; row++) {
            if (board[row][lastMoveCol] != null) {

                // check downwards
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

                // check horizontal
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

                // Check for diagonal win from top left to bottom right
                winCondition = 0;
                for (int winStreak = 0; row + winStreak < rows && lastMoveCol + winStreak < columns; winStreak++) {
                    if (board[row + winStreak][lastMoveCol + winStreak] != null && board[row + winStreak][lastMoveCol + winStreak].getColor().equals(winColor)) {
                        winCondition++;
                        // System.out.println("Win condition # for " + winColor + " checking diagonal(topleft-bottomright) is: " + winCondition);
                        if (winCondition == 4) {
                            winner = true;
                        }
                    }
                    else {
                        winCondition = 0;
                    }
                }

                // Check for diagonal win from top right to bottom left
                winCondition = 0;
                for (int winStreak = 0; row + winStreak < rows && lastMoveCol - winStreak >= 0; winStreak++) {
                    if (board[row + winStreak][lastMoveCol - winStreak] != null && board[row + winStreak][lastMoveCol - winStreak].getColor().equals(winColor)) {
                        winCondition++;
                        // System.out.println("Win condition # for " + winColor + " checking diagonal(topright-bottomleft) is: " + winCondition);
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
