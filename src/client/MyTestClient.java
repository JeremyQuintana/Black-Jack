package client;

import java.util.logging.Logger;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.GameEngineImpl;
import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import validate.Validator;
import view.GameEngineCallbackGUI;
import view.GameEngineCallbackImpl;


public class MyTestClient {
	
	private Scanner input = new Scanner(System.in);
	private static Logger logger = Logger.getLogger("assignment1");
	final GameEngine gameEngine = new GameEngineImpl();
	
	public void menu() {
		gameEngine.addGameEngineCallback(new GameEngineCallbackImpl());
		Validator.validate(false);
		
		String screen;	//declare screen variable to print details to the console

		while (true) {	//always loops after each function
			System.out.println("__________________________________________________________");	//a series of strings are created to add to screen to create a final display to the console
			screen = "*** Black Jack Menu ***\n";
			screen += String.format("%-25s %s\n","Add Player", "A");
			screen += String.format("%-25s %s\n","Remove Player", "B");
			screen += String.format("%-25s %s\n","Place Bet", "C");
			screen += String.format("%-25s %s\n","Start Game", "D");
			screen += String.format("%-25s %s\n","View Player Stats", "E");
			screen += String.format("%-25s %s\n","Exit Program", "X");
			screen += String.format("%-25s %s","Enter Selection:", "");
			System.out.print(screen);
			//switch statement which obtains the users input puts it all into lower case  and compares it to all values in the switch statement
			switch (input.nextLine().toLowerCase()) {
			case "a":
				addPlayer();
				break;
			case "b":
				removePlayer();
				break;
			case "c":
				placeBet();
				break;
			case "d":
				startGame();
				break;
			case "e":
				printPlayerStats();
				break;
			case "x":
				exitProgram();
				break;
			default:	//if the input matched none of the cases then
				System.out.println("\n__________________________________________________________");	//prints error to the console to select one of the values and repeats the loop
				System.out.println("\n\nInvalid input, please enter one of the options below\n");
			}
		}
	}
	
	//adds a player to the game
	public void addPlayer() {
		System.out.println("\n__________________________________________________________\n*** Add Player ***\n");
		String id;
		String name;
		int points;
		
		//prompts the user to enter an unused id
		System.out.print(String.format("%-25s %s","Enter ID:", ""));
		id = input.nextLine();

		//prompts use to enter name
		System.out.print(String.format("%-25s %s","Enter Name:", ""));
		name = input.nextLine();
		
		try {
			//prompts user to enter the starting points
			System.out.print(String.format("%-25s %s","Enter Points:", ""));
			points = input.nextInt();
			input.nextLine();
			
			//creates a new player and adds the player to the game engine
			Player player = new SimplePlayer(id, name, points);
			gameEngine.addPlayer(player);
		}
		
		//catches all possible errors or inputs
		catch(InputMismatchException E) {
			System.out.println("ERROR - Digits not entered");
		}
	}
	
	//removes a player of a specified id
	public void removePlayer() {
		System.out.println("\n__________________________________________________________\n*** Remove Player ***\n");
		String id;
		
		//prompts user to enter id of player to be removed
		System.out.print(String.format("%-25s %s","Enter ID:", ""));
		id = input.nextLine();
		
		//removes player or catches and displays an error that the player could not be found
		try {
			if(gameEngine.removePlayer(gameEngine.getPlayer(id)) == false) throw new Exception();
		}
		catch(Exception E){
			System.out.println("ERROR - Player not found");
		}
	}
	
	//places the bet for a specified player
	public void placeBet() {
		System.out.println("\n__________________________________________________________\n*** Place Bet ***\n");
		String id;
		int bet;
		Player player = null;
		
		//prompts user to enter id of the player who wants to make a bet
		System.out.print(String.format("%-25s %s","Enter ID:", ""));
		id = input.nextLine();
		
		try {
			//checks if the id of the player exists in the system
			if (gameEngine.getPlayer(id) == null) throw new Exception("ID not found in system");
			player = gameEngine.getPlayer(id);
			
			//prompts player to enter a bet
			System.out.print(String.format("%-25s %s","Enter Bet:", ""));
			bet = input.nextInt();
			input.nextLine();
			//checks if the bet is valid
			if(gameEngine.placeBet(player, bet) == false) throw new Exception("Insuffiecient Funds");

		}
		//catches all possible input errors 
		catch(InputMismatchException E) {
			System.out.println("ERROR - Digits not entered");
			input.nextLine();
		}
		catch(Exception E) {
			System.out.println("ERROR - " + E.getLocalizedMessage());
		}
	}
	
	//starts the round of black jack
	public void startGame() {
		System.out.println("\n__________________________________________________________\n*** Game Start ***\n");
		
		//deals cards to each player one at a time then lastly to the house
		for (Player player : gameEngine.getAllPlayers()) {
			gameEngine.dealPlayer(player, 1);
		}
		gameEngine.dealHouse(1);
	}
	
	//prints the id, name and points of all players
	public void printPlayerStats() {
		System.out.println("\n__________________________________________________________\n*** Player Status' ***\n");
		//loops through all players in the game engine and prints their current details
		for (Player player : gameEngine.getAllPlayers()) {
			System.out.println(String.format("%-15s%-20s%-20s", "ID: " + player.getPlayerId(), "Name: " + player.getPlayerName(), "Points: " + player.getPoints()));
		}
	}
	
	//exits program
	public void exitProgram() {
		System.exit(0);
	}
}
