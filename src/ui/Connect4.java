package ui;

import java.util.*;
import javafx.application.Application;

/**
 * 
 * 2023SpringB-X-SER216-15142 / ASU Online
 * Assignment: Project: Deliverable 4
 * @author Randy Elias Garcia
 * Description: Entry point for Connect4 program that allows user to choose which UI they want to use.
 * 
 * @version 1.0.0
 * 
 */

public class Connect4 {
	
	/**
     * The main method to start the Connect 4 game.
     * 
     * @param args an array of command-line arguments for the application.
     */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		char selection;
		do {
			System.out.println("Enter 'G' for JavaFX GUI or 'T' for text console UI:");
			selection = scan.next().charAt(0);
			if (selection == 'G' || selection == 'g') {
				Application.launch(Connect4GUI.class);
			}
			else if (selection == 'T' || selection == 't') {
				new Connect4TextConsole().start();
			}
			else
				System.out.println("Invalid Entry!\n");
		} while ((selection != 'g' && selection != 'G') && (selection != 't' && selection != 'T'));
		
	}

}
