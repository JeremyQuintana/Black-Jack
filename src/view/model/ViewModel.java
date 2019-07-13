package view.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GUI.DialogHelper;
import view.GUI.GameFrame;

//class that controls all functionality needed for the view that is not needed in the model
public class ViewModel {
	
	//instantiate required classes
	private final GameEngine gameEngine;
	private final GameFrame gameFrame;
	private final DialogHelper dialog = new DialogHelper();
	
	private HashMap<String, Boolean> playerDealt = new HashMap<String, Boolean>();	//map to search for who has bet or not
	
	private String selectedID;	//stores which id is currently using the player panel
	
	public ViewModel(GameEngine gameEngine, GameFrame gameFrame) {
		this.gameEngine = gameEngine;
		this.gameFrame = gameFrame;
	}
	
	//called when the add player button is clicked
	public void addPlayer() {
		//variables to store inputs from dialog
		String id = null;
		String name = null;
		int points = 0;
		
		//helper variables to assist in getting information from dialog
		boolean cancel = false;
		boolean validate = false;
		String tempValue;
		
		//series of if statements to distinguish whether user has pressed cancel
		//each if statement creates a new dialog box and asks for an input then validates it
		
		//asks for ID
		if(cancel == false) {
			tempValue = dialog.askInput("Enter ID");
			if (tempValue == null) {
				cancel = true;
				validate = true;
			}
			else {
				id = tempValue;
				validate = true;
			}
		}
		
		//asks for Name
		validate = false;
		if(cancel == false) {
			while(validate == false) {
				tempValue = dialog.askInput("Enter Name");
				if (tempValue == null) {
					cancel = true;
					validate = true;
				}
				else {
					try {
						if(tempValue.equals("")) throw new Exception();
						name = tempValue;
						validate = true;
					}
					catch(Exception E) {
						dialog.displayError("Name can not be Blank");
					}
				}
			}
		}
		
		//asks for points
		validate = false;
		if(cancel == false) {
			while(validate == false) {
				tempValue = dialog.askInput("Enter Points");
				if (tempValue == null) {
					cancel = true;
					validate = true;
				}
				else {
					try {
						points = Integer.parseInt(tempValue);
						validate = true;
					}
					catch(NumberFormatException E) {
						dialog.displayError("Must Enter Integer");
					}
				}
			}
		}
		
		//continues to add if user never pressed cancel
		if(cancel == false) {
			//creation of all needed fields for a new player
			playerDealt.put(id, false);
			gameFrame.getMainBar().getPlayerBar().getScores().put(id, 0);
			gameFrame.getMainBar().getPlayerBar().getCards().put(id, new ArrayList<BufferedImage>());
			gameEngine.addPlayer(new SimplePlayer(id, name, points));
			
			//update of all relevant panels
			gameFrame.getToolBar().update();
			gameFrame.getSummaryBar().update();
		}
	}
	
	//called when the rmeove player button is clicked
	public void removePlayer() {
		String id = null;	//variable to store input from dialog
		
		//helper variables to assist in getting information from dialog
		boolean cancel = false;
		boolean validate = false;
		String tempValue;
		
		
		//series of if statements to distinguish whether user has pressed cancel
		//each if statement creates a new dialog box and asks for an input then validates it
		//asks for id
		if(cancel == false) {
			while(validate == false) {
				tempValue = dialog.askInput("Enter ID");
				if (tempValue == null) {
					cancel = true;
					validate = true;
				}
				else {
					try {
						if(gameEngine.getPlayer(tempValue) == null) throw new Exception();
						id = tempValue;
						validate = true;
					}
					catch(Exception E) {
						dialog.displayError("ID not Found");
					}
				}
			}
		}
		
		//continues to add if user never pressed cancel
		if(cancel == false) {
			//removes all fields of that player in all areas mentions
			playerDealt.remove(id);
			gameFrame.getMainBar().getPlayerBar().getScores().remove(id);
			gameFrame.getMainBar().getPlayerBar().getCards().remove(id);
			gameEngine.removePlayer(gameEngine.getPlayer(id));
			
			//updates relevant panels
			gameFrame.getToolBar().update();
			gameFrame.getSummaryBar().update();
			
			dealHouse();	//calls deal house in case the player removed is the only player not dealt
		}
	}
	
	//called when the bet button is pressed
	//uses selectedID variable to distinguish who the bet is for
	public void betPlayer() {
		int bet = 0;	//variable to store input from dialog
		boolean cancel = false;	//helper variable to find whether user canceled the bet or cant bet
		
		//series of checks to see if user is allowed to bet
		//checks if there is currently a player selected
		if(checkIfPlayerSelected() == false) {
			cancel = true;
			dialog.displayError("No Player Selected");
		}
		
		//checks if the player has already been dealt for the round
		if(checkIfPlayerDealt() == true) {
			cancel = true;
			dialog.displayError("Player has already been dealt for this round");
		}
		
		//creates dialog and asks input bet then validates the input
		//series of validation in order of whether a bet was submitted, bet is an integer, bet is a positive number, bet can be made
		if(cancel == false) {
			boolean validate = false;
			String tempValue;
			while(validate == false) {
				tempValue = dialog.askInput("Enter Bet");
				if (tempValue == null) {
					cancel = true;
					validate = true;
				}
				else {
					try {
						bet = Integer.parseInt(tempValue);
						if(bet < 1) throw new Exception();
						//if bet does not exceed the amount of points the player has then it passes
						if(gameEngine.placeBet(gameEngine.getPlayer(selectedID), bet) == true) {
							validate = true;
						}
						else {
							dialog.displayError("Not enough Points");
						}
					}
					catch(NumberFormatException E) {
						dialog.displayError("Must Enter Integer");
					}
					catch(Exception E) {
						dialog.displayError("Integer must be a positive number");
					}
				}
			}
		}
			
		//if all validation and cancels are passed then this can execute
		if(cancel == false) {
			//sets the bet then updates the player bar to show the bet
			gameEngine.getPlayer(selectedID).placeBet(bet);
			gameFrame.getMainBar().getPlayerBar().update();
		}
	}
	
	//called when the deal button is clicked
	public void dealPlayer() {
		boolean cancel = false;	//helper method to pass through method if deal is cancelled
		
		//series of checks to ensure user can press the deal
		//checks if a player is selected
		if(checkIfPlayerSelected() == false) {
			cancel = true;
			dialog.displayError("No Player Selected");
		}
		//checks if the player selected has bet
		if(checkIfPlayerBet() == false) {
			cancel = true;
			dialog.displayError("No bet Placed");
		}
		//checks if the player has already been dealt
		if(checkIfPlayerDealt() == true) {
			cancel = true;
			dialog.displayError("Player Already Dealt");
		}
		
		//if checks pass then ir deals
		if(cancel == false) {
			playerDealt.put(selectedID, true);	//records that the player has been dealt to for the round
			new Thread() {
				public void run() {
					gameEngine.dealPlayer(gameEngine.getPlayer(selectedID), 1);	//calls the deal method in the model for the currnet player selected
				}
			}.start();
		}
	}
	
	//button when a player is clicked in the combobox
	public void switchPlayer(Player selectedPlayer) {
		//check if a player has been selected by the box
		if(selectedPlayer != null) {
			//sets the selectedID to the id of the player selected and updates the player bar
			selectedID = selectedPlayer.getPlayerId();
			gameFrame.getMainBar().getPlayerBar().update();
		}
		else {
			//sets the selectedID to null and updates the player bar to nothing
			selectedID = null;
			gameFrame.getMainBar().getPlayerBar().update();
		}
	}
	
	//method that is called after any player has been dealt
	public void dealHouse() {
		//checks if all other players have been dealt to continue
		if(checkAllDealt() == true) {
			new Thread() {
				public void run() {
					gameEngine.dealHouse(1);;	//if all other players have been dealt then it calls the dealhouse method in the model
				}
			}.start();
		}
	}
	
	//helper methods for checks
	private boolean checkIfPlayerSelected() {
		if(selectedID == null) return false;
		else return true;
	}
	
	private boolean checkIfPlayerDealt() {
		if(playerDealt.get(selectedID) == true) return true;
		else return false;
	}
	
	private boolean checkIfPlayerBet() {
		if(gameEngine.getPlayer(selectedID).getBet() > 0) return true;
		else return false;
	}
	
	private boolean checkAllDealt() {
		if(playerDealt.size() != 0) {	//check to see the house isnt dealing because theres no players
			boolean allDealt = true;
			Map<String, Boolean> dealtMap = playerDealt; 
			for(Entry<String, Boolean> dealt : dealtMap.entrySet()) {
				if(dealt.getValue() == false) allDealt = false;
			}
			if(allDealt == true) return true;
			else return false;
		}
		return false;
	}
	
	//method to reset the round by making all in the playerDealt array false
	public void clearDealt() {
		Map<String, Boolean> dealtMap = playerDealt; 
		for(Entry<String, Boolean> dealt : dealtMap.entrySet()) {
			dealt.setValue(false);
		}
	}
	
	//getter methods
	public String getSelectedID() {
		return selectedID;
	}
	
	public GameEngine getGameEngine() {
		return gameEngine;
	}
}
