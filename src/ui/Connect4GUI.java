package ui;

import java.util.*;

import core.Connect4Logic;
import core.Piece;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.shape.*;
import javafx.scene.control.*;

public class Connect4GUI extends Application {
	
	private Connect4Logic game;
    private GridPane layout;
    private Pane pieceLayout;
    private String playerOneColor = "red", playerTwoColor = "yellow";
    private Button[][] boardButtons;
    private boolean computerPlayer;
    private Label message;
    private int currentPlayer;
    private static final int TILE_SIZE = 70;
    
    public static void  main(String[] args) {
		launch(args);
		
	}

    @Override
    public void start(Stage primaryStage) {
    	
    	primaryStage.setTitle("Connect 4");
		Image logo = new Image(getClass().getResourceAsStream("c4_logo.png"));
        primaryStage.getIcons().add(logo);
        
        game = new Connect4Logic();
        layout = new GridPane();
        pieceLayout = new Pane();
        currentPlayer = 1;
        message = new Label("Click a column to make a move.");

        Shape board = createGameBoard();

        layout.add(message, 0, 1);
        
        layout.getChildren().add(pieceLayout);
        
        layout.getChildren().add(board);
        layout.getChildren().addAll(highlightColumns());
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
    private List<Rectangle> highlightColumns() {
    	List<Rectangle> highlights = new ArrayList<>();
    	
    	for (int col = 0; col < game.columns; col++) {
    		Rectangle rect = new Rectangle(TILE_SIZE, (game.rows + 3) * TILE_SIZE);
    		rect.setTranslateX(col * (TILE_SIZE + 26.5) + 26);
    		rect.setFill(Color.TRANSPARENT);
    		
    		rect.setOnMouseEntered(e -> {
    			rect.setStroke(Color.rgb(200, 200, 50, 1));
    			rect.setStrokeWidth(4);
    		});
    		rect.setOnMouseExited(e -> rect.setStroke(Color.TRANSPARENT));

			int addToCol = col;
    		rect.setOnMouseClicked(e -> {
    			if (game.addPiece(addToCol, getPlayerColor(currentPlayer))) {
    	              int row = returnRow(addToCol);
    	              String color = getPlayerColor(currentPlayer);
    	              game.board[row][addToCol].setFill(Color.web(color));
    	              game.board[row][addToCol].setStroke(Color.BLACK);
    	              game.board[row][addToCol].setStrokeWidth(2);
                      pieceLayout.getChildren().add(game.board[row][addToCol]);
    	              
                      game.board[row][addToCol].setTranslateX(addToCol * (TILE_SIZE + 26.5) + 24);
                      
                      TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5), game.board[row][addToCol]);
                      animation.setFromY(row - 65);
                      animation.setToY(row * (TILE_SIZE + 32) + 28);
                      animation.play();
                      
    	              if (game.checkForWin(addToCol, getPlayerColor(currentPlayer))) {
    	                    if (currentPlayer == 1) {
    	                        System.out.println("Player X Won the Game");
    	                    }
    	                    else {
    	                        System.out.println("Player O Won the Game");
    	                    }
    	                }
    	                if (!game.checkForWin(addToCol, getPlayerColor(currentPlayer)) && game.checkForDraw()) {
    	                    System.out.println("This game ends in a draw.");
    	                }
    	                // Switch to the next player's turn
    	                currentPlayer = getOpponent(currentPlayer);
    	    		}
    		});
    		
    		highlights.add(rect);
    	}
    	
    	return highlights;
    }
    
    private Shape createGameBoard() {
    	Shape board = new Rectangle((game.columns + 3) * TILE_SIZE, (game.rows + 3) * TILE_SIZE);
    	Circle circle = new Circle(TILE_SIZE / 2);
    	circle.setCenterX(TILE_SIZE / 2);
    	circle.setCenterY(TILE_SIZE / 2);
        
        for (int row = 0; row < game.rows; row++) {
          for (int col = 0; col < game.columns; col++) {
        	  circle.setTranslateX(col * (TILE_SIZE + 26.5) + 26);
        	  circle.setTranslateY(row * (TILE_SIZE + 32) + 26);
        	  
        	  board = Shape.subtract(board, circle);
          }
        }
        
        board.setFill(Color.BLUE);
        
        return board;
    }
    
    private String getPlayerName(int player) {
        return currentPlayer == 1 ? "Player X" : "Player O";
    }
    
    private String getPlayerColor(int player) {
        return currentPlayer == 1 ? playerOneColor : playerTwoColor;
    }
    
    private int getOpponent(int player) {
        return currentPlayer == 1 ? 2 : 1;
    }

    /**
     * Updates the GUI board to match the current state of the game logic board.
     */
//    private void updateBoard() {
//        Piece[][] board = game.board;
//        
//        for (int row = 0; row < game.rows; row++) {
//            for (int col = 0; col < game.columns; col++) {
//                if (board[row][col] == null) {
//                	continue;
//                }
//                else {
//                    Circle gamePiece = new Circle(TILE_SIZE / 2 + 5, Color.web(board[row][col].getColor()));
//                    gamePiece.setCenterX(TILE_SIZE / 2);
//                    gamePiece.setCenterY(TILE_SIZE / 2);
//                    gamePiece.setTranslateX(col * (TILE_SIZE + 26.5) + 24);
//                    gamePiece.setTranslateY(row * (TILE_SIZE + 32) + 28);
//                    pieceLayout.getChildren().add(gamePiece);
//                    
//                    TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5), gamePiece);
//                    animation.setToY(row * (TILE_SIZE + 32) + 28);
//                    animation.play();
//                    
//                }
//            }
//        }
//    }
    
    private int returnRow(int col) {
    	Piece[][] board = game.board;
    	int row = 0;
    	
    	while (board[row][col] == null && row <= game.rows - 1) {
    		row++;
    	}
    	
    	return row;
    	
    }
    
//    private static class gamePiece extends Circle {
//    	public gamePiece() {
//    		super(TILE_SIZE / 2);
//    	}
//    }

}
