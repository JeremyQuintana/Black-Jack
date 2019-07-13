package view;

import javax.swing.SwingUtilities;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.GUI.GameFrame;
import view.interfaces.GameEngineCallback;

public class GameEngineCallbackGUI implements GameEngineCallback {
	
	final private GameEngine gameEngine;
	final private GameFrame gameFrame;
	
	public GameEngineCallbackGUI(GameEngine gameEngine){
		this.gameEngine = gameEngine;
		this.gameFrame = new GameFrame(gameEngine);
	}
	
	//called when a card is drawn for the player
	public void nextCard(Player player, PlayingCard card, GameEngine engine) {
		//updates status bar
		gameFrame.getStatusBar().update("Dealing to " + player.getPlayerName());
		//updates the scores
		gameFrame.getMainBar().getPlayerBar().setScore(player.getPlayerId(), gameFrame.getMainBar().getPlayerBar().getScores().get(player.getPlayerId()) + card.getScore());
		//draws the card drawn
		drawPlayerCard(player, card);
	}

	//called when player draws busting card
	public void bustCard(Player player, PlayingCard card, GameEngine engine) {
		//draws the bust card
		drawPlayerCard(player, card);
	}

	//called after player finishes their round
	public void result(Player player, int result, GameEngine engine) {
		//updates status bar
		gameFrame.getStatusBar().update("Player " + player.getPlayerName() + " has bust with score " + gameFrame.getMainBar().getPlayerBar().getScores().get(player.getPlayerId()));
		//attempts to deal to house and will only succeed if all players have been dealt
		gameFrame.getViewModel().dealHouse();
	}

	//called when the hosue draws a card
	public void nextHouseCard(PlayingCard card, GameEngine engine) {
		//updates the status bar
		gameFrame.getStatusBar().update("Dealing to the House");
		//updates house score
		gameFrame.getMainBar().getHouseBar().setHouseScore(gameFrame.getMainBar().getHouseBar().getHouseScore() + card.getScore());
		//draws card drawn
		drawHouseCard(card);
	}

	//called when house draws busting card
	public void houseBustCard(PlayingCard card, GameEngine engine) {
		//draws the busting card
		drawHouseCard(card);
	}

	//called when house finishes being dealt
	public void houseResult(int result, GameEngine engine) {
		//updates stauts panel
		gameFrame.getStatusBar().update("House has bust with score " + gameFrame.getMainBar().getHouseBar().getHouseScore());
		
		//clears all values such as stored cards and scores to prepare the next round
		gameFrame.getMainBar().getHouseBar().clearHouseCards();
		gameFrame.getMainBar().getPlayerBar().clearPlayerCards();
		gameFrame.getMainBar().getPlayerBar().clearPoints();
		gameFrame.getMainBar().getHouseBar().setHouseScore(0);
		gameFrame.getViewModel().clearDealt();
		
		//updates all relevant bars
		gameFrame.getMainBar().getHouseBar().update();
		gameFrame.getMainBar().getPlayerBar().update();
		gameFrame.getSummaryBar().update();
	}
	
	//draws card in the house panel on a new thread
	private void drawHouseCard(PlayingCard card) {
		gameFrame.getMainBar().getHouseBar().setCard(card);	//stores the card for later use
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				gameFrame.getMainBar().getHouseBar().update();	//redraws whole house panel
			}
		});
	}
	
	//draws card in the player panel on a new thread
	private void drawPlayerCard(Player player, PlayingCard card) {
		gameFrame.getMainBar().getPlayerBar().setCard(player.getPlayerId(), card);	//stores the card for later use
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				gameFrame.getMainBar().getPlayerBar().update();	//redraws while player panel
			}
		});
	}
	
}
