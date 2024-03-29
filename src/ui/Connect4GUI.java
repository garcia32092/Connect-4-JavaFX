package ui;

import java.util.*;
import core.Connect4Logic;
import core.Connect4ComputerPlayer;
import core.Piece;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.*;
import javafx.scene.control.*;

/**
 * 
 * 2023SpringB-X-SER216-15142 / ASU Online
 * Assignment: Project: Deliverable 4
 * @author Randy Elias Garcia
 * Description: A JavaFX application that allows users to play a Connect 4 game via a graphical user interface.
 * 
 * @version 1.0.0
 * 
 */
public class Connect4GUI extends Application {
	
	private Connect4Logic game = new Connect4Logic();
	private Pane windowLayout;
	private Scene gameScene;
    private Pane gameLayout;
    private Pane pieceLayout;
    private StackPane endGamePane;
    private String currentGameMode = "1 Player", playerOneColor = "red", playerTwoColor = "yellow";
	private Connect4ComputerPlayer computer = new Connect4ComputerPlayer(playerTwoColor);
    private boolean computerPlayer = true;
    private int currentPlayer = 1;
    private static final int TILE_SIZE = 70;
    private static final int windowWidth = 900, windowHeight = 900;
    
    /**
     * The main method that launches the application.
     *
     * @param args an array of command-line arguments for the application.
     */    
    public static void  main(String[] args) {
		launch(args);
		
	}
    
    /**
     * Default constructor for the Connect4GUI class.
     */
    public Connect4GUI() {
    	
    }

    /**
     * The start method sets up the initial menu scene and sets the title and logo of the window.
     * On the menu, the user is able to select the game mode they want to play.
     * The user is able to click the rules button to see the rules of the game.
     * The user is able to play the game by clicking on the play button.
     * The user is able to quit the program by clicking the quit button.
     * @param window The Stage object representing the main window.
     */
    public void start(Stage window) {
    	
    	// sets title of the stage
    	window.setTitle("Connect 4");
    	// sets logo for the stage window
		Image logo = new Image(getClass().getResourceAsStream("c4_logo.png"));
        window.getIcons().add(logo);
        
        // VBox pane for the main menu
        VBox menu = new VBox();
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(40);
        
        // scene for the main menu
        Scene menuScene = new Scene(menu, windowWidth, windowHeight);
        // styler for main menu scene
        menuScene.getStylesheets().add("/ui/MainStyler.css");

        // title image within the main menu
        ImageView title = new ImageView("/ui/c4_title.png");
        title.setFitWidth(550);
        title.setPreserveRatio(true);
        title.setSmooth(true);
        title.setCache(true);
        
        // text instructs user to select game mode
        Text mode = new Text("Choose a game mode.\n"
        		+ "Current game mode: " + currentGameMode);
        mode.setTextAlignment(TextAlignment.CENTER);
        
        // button for single player option
        Button singlePlayer = new Button("1 Player");
        singlePlayer.setPrefSize(175, 50);
        // if pressed, user can play against computer player
        singlePlayer.setOnAction(e -> {
        	computerPlayer = true;
        	currentGameMode = "1 Player";
        	mode.setText("Choose a game mode.\nCurrent game mode: " + currentGameMode);
        });
        
        // button for two player option
        Button twoPlayer = new Button("2 Player");
        twoPlayer.setPrefSize(175, 50);
        // if pressed, user can play against other human player
        twoPlayer.setOnAction(e -> {
        	computerPlayer = false;
        	currentGameMode = "2 Players";
        	mode.setText("Choose a game mode.\nCurrent game mode: " + currentGameMode);
        });
        
        // HBox for game mode buttons
        HBox gameModes = new HBox();
        gameModes.setAlignment(Pos.CENTER);
        gameModes.setSpacing(20);
        
        // button for playing the game
        Button play = new Button("Play");
        play.setPrefSize(200, 50);
        // if pressed, will generate panes for game board, game pieces, endgame screen, 
        // and allow player to play the game by placing pieces in the game board
        play.setOnAction(e -> {
        	// creating the necessary pane objects
        	windowLayout = new Pane();
            gameScene = new Scene(windowLayout, windowWidth, windowHeight);
        	gameLayout = new Pane();
            pieceLayout = new Pane();
            endGamePane = new StackPane();
            // keeps end game screen centered
            endGamePane.layoutXProperty().bind(gameScene.widthProperty().divide(2).subtract(endGamePane.widthProperty().divide(2)));
            endGamePane.layoutYProperty().bind(gameScene.heightProperty().subtract(endGamePane.heightProperty()));

            // generates game board shape
            Shape board = createGameBoard();
            
            // keeps game board and pieces centered in the window
            gameLayout.layoutXProperty().bind(gameScene.widthProperty().divide(2).subtract(gameLayout.widthProperty().divide(2)));
            gameLayout.layoutYProperty().bind(gameScene.heightProperty().subtract(gameLayout.heightProperty()).subtract(75));
            
            // adds necessary pane/objects to game layout
            gameLayout.getChildren().add(pieceLayout);
            gameLayout.getChildren().add(board);
            gameLayout.getChildren().addAll(highlightColumns());
            
            // text instructs user on how to make a move
            Label moveInstruct = new Label("Click a column to make a move.");
            moveInstruct.layoutXProperty().bind(gameLayout.widthProperty().divide(2).subtract(moveInstruct.widthProperty().divide(2)));
            moveInstruct.setTranslateY(650);
            moveInstruct.setId("make-move-label");
            
            // button to return to main menu
            Button mainMenu = new Button("Main Menu");
            mainMenu.setPrefSize(200, 40);
            mainMenu.setTranslateY(-175);
            // if pressed, returns to main menu and reset game
            mainMenu.setOnAction(m -> {
            	game.setUpGame();
            	gameLayout.getChildren().clear();
            	windowLayout.getChildren().clear();
        		window.setScene(menuScene);
                window.show();
        	});
            
            // adds text and button to game layout
            gameLayout.getChildren().addAll(moveInstruct, mainMenu);
            
            // adds game layout to window and resets scene
            windowLayout.getChildren().add(gameLayout);
            gameScene.getStylesheets().add("/ui/MainStyler.css");
            window.setScene(gameScene);
            window.show();
        });
        
        // button for rules scene
        Button rules = new Button("Rules");
        rules.setPrefSize(200, 50);
        // if pressed, will take user to rules of the game
        rules.setOnAction(e -> {
        	// vbox to display text and button
        	VBox rulesPane = new VBox();
        	Scene ruleScene = new Scene(rulesPane, windowWidth, windowHeight);
        	rulesPane.setAlignment(Pos.CENTER);
        	
        	// button to return to the main menu
        	Button back = new Button("Main Menu");
        	back.setPrefSize(150, 40);
        	// if pressed, returns user to main menu
        	back.setOnAction(b -> {
        		window.setScene(menuScene);
                window.show();
        	});
        	
        	// text displaying rules
        	Text rulesText = new Text("Basic rules of Connect4:\n\n"
        			+ "1. The game is played by two players who take turns dropping colored discs into an open slot on the board.\n\n"
        			+ "2. Players must drop their discs into the lowest available slot in a chosen column. Once a disc is dropped into a column, it will fall to the lowest available space in that column.\n\n"
        			+ "3. The game is won when one player connects four of their colored discs in a row, either horizontally, vertically, or diagonally.\n\n"
        			+ "4. If all the slots on the board are filled and neither player has connected four discs in a row, the game is considered a draw.\n\n"
        			+ "5. Players can only connect their own colored discs.\n\n");
        	rulesText.setTextAlignment(TextAlignment.CENTER);
        	rulesText.setWrappingWidth(ruleScene.getWidth() - 150);
        	
        	rulesPane.getChildren().addAll(rulesText, back);
        	ruleScene.getStylesheets().add("/ui/MainStyler.css");
        	window.setScene(ruleScene);
        	window.show();
        });
        
        // button to quit program
        Button quit = new Button("Quit");
        quit.setPrefSize(200, 50);
        // if pressed, ends program
        quit.setOnAction(e -> System.exit(1));

        // adds elements of menu scene to the stage and shows scene
        gameModes.getChildren().addAll(singlePlayer, twoPlayer);
        menu.getChildren().addAll(title, mode, gameModes, play, rules, quit);
        window.setScene(menuScene);
        window.show();
        
    }
    
    /**
     * Returns a list of rectangles, which highlight the columns in the game board on mouse hover,
     * adds a game piece to the selected column on mouse click, plays an animation when a game piece
     * is added, checks for a win or draw, and switches to the next player's turn.
     * @return a list of rectangles, which highlight the columns in the game board on mouse hover
     */
    private List<Rectangle> highlightColumns() {
    	List<Rectangle> highlights = new ArrayList<>();
    	
    	// Iterates through each column in the game board and creates a new rectangle for each column
    	for (int col = 0; col < game.columns; col++) {
    		// Creates a new Rectangle object and sets its width and height
    		Rectangle rect = new Rectangle(TILE_SIZE, (game.rows + 3) * TILE_SIZE);
    		rect.setTranslateX(col * (TILE_SIZE + 26.5) + 26);
    		// Sets the fill color of the rectangle to transparent
    		rect.setFill(Color.TRANSPARENT);
    		
    		// Highlights the current column when the mouse enters the rectangle
    		rect.setOnMouseEntered(e -> {
    			rect.setStroke(Color.rgb(200, 200, 50, 1));
    			rect.setStrokeWidth(4);
    		});
    		rect.setOnMouseExited(e -> rect.setStroke(Color.TRANSPARENT));

			int addToCol = col;
			// Adds a game piece to the selected column on mouse click and plays an animation
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
                      
                      // Checks for a win and displays an end game message if a win is detected
    	              if (game.checkForWin(addToCol, getPlayerColor(currentPlayer))) {
    	            	  windowLayout.getChildren().add(endGamePane);
    	            	  Rectangle endScreen = new Rectangle(gameScene.getWidth(), gameScene.getHeight());
    	            	  endScreen.setFill(Color.rgb(0, 0, 0, 0.7));
    	            	  endGamePane.getChildren().add(endScreen);
    	            	  
    	            	  Label endGameMessage = new Label("Player " + currentPlayer + " won the game!");
    	            	  endGameMessage.setTranslateY(-350);
    	            	  endGameMessage.setId("end-game-label");
    	            	  endGamePane.getChildren().add(endGameMessage);
    	            	  
    	            	  Button playAgain = new Button("Play Again");
    	            	  playAgain.setPrefSize(150, 40);
    	            	  playAgain.setOnAction(end -> {
    	            		  pieceLayout.getChildren().clear();
    	            		  game.setUpGame();
    	            		  endGamePane.getChildren().clear();
    	            		  windowLayout.getChildren().remove(endGamePane);
    	            	  });
    	            	  
    	            	  endGamePane.getChildren().add(playAgain);
    	            	  // Displays the winning player in the console (helps with testing)
    	                    if (currentPlayer == 1) {
    	                        System.out.println("Player X Won the Game");
    	                    }
    	                    else {
    	                        System.out.println("Player O Won the Game");
    	                    }
    	                }
    	              	// Checks for a draw and displays an end game message if a draw is detected
    	                if (!game.checkForWin(addToCol, getPlayerColor(currentPlayer)) && game.checkForDraw()) {
    	                	windowLayout.getChildren().add(endGamePane);
      	            	  	Rectangle endScreen = new Rectangle(windowWidth, windowHeight);
      	            	  	endScreen.setFill(Color.rgb(0, 0, 0, 0.7));
      	            	  	endGamePane.getChildren().add(endScreen);
      	            	  	
      	            	  	Label endGameMessage = new Label("This game ends in a draw.");
      	            	  	endGameMessage.setTranslateY(-350);
      	            	  	endGameMessage.setId("end-game-label");
      	            	  	endGamePane.getChildren().add(endGameMessage);
      	            	  	
      	            	  	Button playAgain = new Button("Play Again");
      	            	  	playAgain.setPrefSize(150, 40);
      	            	  	playAgain.setOnAction(end -> {
      	            	  		pieceLayout.getChildren().clear();
      	            	  		game.setUpGame();
      	            	  		endGamePane.getChildren().clear();
      	            	  		windowLayout.getChildren().remove(endGamePane);
      	            	  	});
      	            	  	
      	            	  	endGamePane.getChildren().add(playAgain);
    	                    System.out.println("This game ends in a draw.");
    	                }
    	                // Switch to the next player's turn
    	                currentPlayer = getOpponent(currentPlayer);

    	                // checks for 1 player game mode and generates computer player's move if user selects 1 player game mode
        				if (computerPlayer && currentPlayer == 2) {
        					int computerCol = computer.makeMove(game.board);
        					if (game.addPiece(computerCol, getPlayerColor(currentPlayer))) {
        	    	              int computerRow = returnRow(computerCol);
        	    	              String computerColor = getPlayerColor(currentPlayer);
        	    	              game.board[computerRow][computerCol].setFill(Color.web(computerColor));
        	    	              game.board[computerRow][computerCol].setStroke(Color.BLACK);
        	    	              game.board[computerRow][computerCol].setStrokeWidth(2);
        	                      pieceLayout.getChildren().add(game.board[computerRow][computerCol]);
        	    	              
        	                      game.board[computerRow][computerCol].setTranslateX(computerCol * (TILE_SIZE + 26.5) + 24);
        	                      
        	                      TranslateTransition computerAnimation = new TranslateTransition(Duration.seconds(0.5), game.board[computerRow][computerCol]);
        	                      computerAnimation.setFromY(computerRow - 65);
        	                      computerAnimation.setToY(computerRow * (TILE_SIZE + 32) + 28);
        	                      computerAnimation.play();
        	                      
        	                      // Checks for a win and displays an end game message if a win is detected
        	    	              if (game.checkForWin(computerCol, getPlayerColor(currentPlayer))) {
        	    	            	  windowLayout.getChildren().add(endGamePane);
        	    	            	  Rectangle endScreen = new Rectangle(gameScene.getWidth(), gameScene.getHeight());
        	    	            	  endScreen.setFill(Color.rgb(0, 0, 0, 0.7));
        	    	            	  endGamePane.getChildren().add(endScreen);
        	    	            	  
        	    	            	  Label endGameMessage = new Label("Player " + currentPlayer + " won the game!");
        	    	            	  endGameMessage.setTranslateY(-350);
        	    	            	  endGameMessage.setId("end-game-label");
        	    	            	  endGamePane.getChildren().add(endGameMessage);
        	    	            	  
        	    	            	  Button playAgain = new Button("Play Again");
        	    	            	  playAgain.setPrefSize(150, 40);
        	    	            	  playAgain.setOnAction(end -> {
        	    	            		  pieceLayout.getChildren().clear();
        	    	            		  game.setUpGame();
        	    	            		  endGamePane.getChildren().clear();
        	    	            		  windowLayout.getChildren().remove(endGamePane);
        	    	            	  });
        	    	            	  
        	    	            	  endGamePane.getChildren().add(playAgain);
        	    	            	  	// Displays the winning player in the console (helps with testing)
        	    	                    if (currentPlayer == 1) {
        	    	                        System.out.println("Player X Won the Game");
        	    	                    }
        	    	                    else {
        	    	                        System.out.println("Player O Won the Game");
        	    	                    }
        	    	                }
        	    	              	// Checks for a draw and displays an end game message if a draw is detected
        	    	                if (!game.checkForWin(computerCol, getPlayerColor(currentPlayer)) && game.checkForDraw()) {
        	    	                	windowLayout.getChildren().add(endGamePane);
        	      	            	  	Rectangle endScreen = new Rectangle(windowWidth, windowHeight);
        	      	            	  	endScreen.setFill(Color.rgb(0, 0, 0, 0.7));
        	      	            	  	endGamePane.getChildren().add(endScreen);
        	      	            	  	
        	      	            	  	Label endGameMessage = new Label("This game ends in a draw.");
        	      	            	  	endGameMessage.setTranslateY(-350);
        	      	            	  	endGameMessage.setId("end-game-label");
        	      	            	  	endGamePane.getChildren().add(endGameMessage);
        	      	            	  	
        	      	            	  	Button playAgain = new Button("Play Again");
        	      	            	  	playAgain.setPrefSize(150, 40);
        	      	            	  	playAgain.setOnAction(end -> {
        	      	            	  		pieceLayout.getChildren().clear();
        	      	            	  		game.setUpGame();
        	      	            	  		endGamePane.getChildren().clear();
        	      	            	  		windowLayout.getChildren().remove(endGamePane);
        	      	            	  	});
        	      	            	  	
        	      	            	  	endGamePane.getChildren().add(playAgain);
        	    	                    System.out.println("This game ends in a draw.");
        	    	                }
        	    	                // Switch to the next player's turn
        	    	                currentPlayer = getOpponent(currentPlayer);
        					}
        				}
    	    		}
    		});
    		
    		highlights.add(rect);
    	}
    	
    	return highlights;
    }
    /**
     * Creates the game board shape with circles cut out to resemble a Connect 4 game board.
     * @return Shape, the shape of the Connect 4 game board
     */
    private Shape createGameBoard() {
    	// creates shape for game board
    	Shape board = new Rectangle((game.columns + 3) * TILE_SIZE, (game.rows + 3) * TILE_SIZE);
    	// creates circle for game piece slots
    	Circle circle = new Circle(TILE_SIZE / 2);
    	circle.setCenterX(TILE_SIZE / 2);
    	circle.setCenterY(TILE_SIZE / 2);
        
        for (int row = 0; row < game.rows; row++) {
          for (int col = 0; col < game.columns; col++) {
        	  circle.setTranslateX(col * (TILE_SIZE + 26.5) + 26);
        	  circle.setTranslateY(row * (TILE_SIZE + 32) + 26);
        	  
        	  // removes circle from rectangle game board
        	  board = Shape.subtract(board, circle);
          }
        }
        
        board.setFill(Color.BLUE);
        
        return board;
    }
    
    /**
     * Returns the name of the player associated with the given player number.
     * @param player the player number (1 or 2)
     * @return the name of the player
     */
    private String getPlayerName(int player) {
        return currentPlayer == 1 ? "Player X" : "Player O";
    }
    
    /**
     * Returns the color of the player associated with the given player number.
     * @param player the player number (1 or 2)
     * @return the color of the player
     */
    private String getPlayerColor(int player) {
        return currentPlayer == 1 ? playerOneColor : playerTwoColor;
    }
    
    /**
     * Returns the opponent of the given player.
     * @param player the player number (1 or 2)
     * @return the opponent's player number (2 or 1)
     */
    private int getOpponent(int player) {
        return currentPlayer == 1 ? 2 : 1;
    }
    
    /**
     * Receives a column integer and returns the row of the next available spot for a game piece on the board
     * @param col, column that player attempts to place a piece
     * @return the row where the game piece can be inserted
     */
    private int returnRow(int col) {
    	Piece[][] board = game.board;
    	int row = 0;
    	
    	while (board[row][col] == null && row <= game.rows - 1) {
    		row++;
    	}
    	
    	return row;
    	
    }

}
