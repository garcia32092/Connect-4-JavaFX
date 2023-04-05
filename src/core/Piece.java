package core;

import javafx.scene.shape.Circle;

/**
 * 
 * 2023SpringB-X-SER216-15142 / ASU Online
 * Assignment: Project: Deliverable 2
 * @author Randy Elias Garcia
 * Description: A class representing a game piece in a Connect 4 game.
 * 
 * @version 1.0.0
 * 
 */

public class Piece extends Circle {
	
	/**
     * The color of the game piece.
     */
    private String color;
    
    private static final int TILE_SIZE = 70;
	
	Piece() {
		super(TILE_SIZE / 2 + 5);
		
		setCenterX(TILE_SIZE / 2);
        setCenterY(TILE_SIZE / 2);
	}
    
    /**
     * Gets the color of the game piece.
     * @return The color of the game piece.
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color of the game piece.
     * @param color The color to set for the game piece to.
     */
    public void setColor(String color) {
        this.color = color;
    }
    
}
