package model;

import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.ArrayDeque;
import java.util.ArrayList;

import model.interfaces.*;
import view.interfaces.*;

public class GameEngineImpl implements GameEngine {
	
	private final int BUST_LEVEL = 21;
	
	private List<Player> players = new ArrayList<Player>();	//list of players playing
	private List<GameEngineCallback> callback = new ArrayList<GameEngineCallback>();	//list of all interfaces
	
	private Deque<PlayingCard> deque = getShuffledDeck();	//creates a new starting deck
	
	//deals to the specified player
	public void dealPlayer(Player player, int delay) {
		player.setResult(dealToBust(player, delay));	//sets the players result to their hands result just before they bust
		
		for(GameEngineCallback callback : callback) callback.result(player, player.getResult(), this);	//goes through all callbacks and displays the players result
	}
	
	//deals to the house
	//only called after all players have been dealt their cards and received results
	public void dealHouse(int delay) {
		int currentScore = dealToBust(null, delay);	//finds the house's result just before it busts
		
		settleBets(currentScore);
		
		for(GameEngineCallback callback : callback) callback.houseResult(currentScore, this);	//goes through all callbacks and prints the house's result
	}
	
	private void settleBets(int houseScore) {
		//goes through all players results
		for(Player player : players) {
			//checks if the players hand result is higher than the houses
			//if so then the player receives their bet to their points
			//if they lose then they lose the amount they bet
			if(player.getResult() > houseScore) {
				player.setPoints(player.getPoints() + (player.getBet()));
			}
			else if(player.getResult() < houseScore) {
				player.setPoints(player.getPoints() - player.getBet());
			}
			
			player.resetBet();	//resets the players bet to 0
		}
	}
	
	//refactoring method that returns the result of the hand just before bust
	//if dealing to house pass through null for the player
	private int dealToBust(Player player, int delay) {
		int currentScore = 0;
		PlayingCard currentCard;
		boolean busted = false;
		
		//do while loop that continues to deal until the hand exceeds the bust level
		do {
			if(deque.isEmpty()) deque = getShuffledDeck();	//checks if the deck is empty after each deal and restocks the deck if it is empty
			
			currentCard = deque.removeFirst();	//removes and stores the top of the deck's card
			
			//checks if the current card will exceed the hands amount by the bust level to call appropriate methods
			if(currentScore + currentCard.getScore() <= BUST_LEVEL) {
				//goes through all callbacks and logs the dealt card
				for(GameEngineCallback callback : callback) {
					//distinguishes whether to call the next house card or next card for a player
					if(player == null) callback.nextHouseCard(currentCard, this);
					else callback.nextCard(player, currentCard, this);
				}
				//adds to the card to the current score of the hand
				currentScore += currentCard.getScore();
			}
			else {
				//goes through all callbacks and logs the bust card
				for(GameEngineCallback callback : callback) { 
					//distinguishes whether t call the next house card or next card for a player
					if(player == null) callback.houseBustCard(currentCard, this);
					else callback.bustCard(player, currentCard, this);
				}
				busted = true;	//sets boolean value to true to exit the do while loop
			}
			
			placeDelay(delay);	//delay
		}while(busted == false);
		
		return currentScore;
	}
	
	//adds a player passed from client to the game engine
	public void addPlayer(Player player) {
		//if the id already exists in the collection it replaces the one in the collection with the new player
		if(getPlayer(player.getPlayerId()) == null) {
			players.add(player);
		}
		else players.set(players.indexOf(getPlayer(player.getPlayerId())), player);
	}
	
	//returns the player given their specified id
	public Player getPlayer(String id) {
		//checks through all players and checks their id
		//if it doesn't exist it returns null
		for(Player playerCompare : players) {
			if(playerCompare.getPlayerId().equals(id) == true) return playerCompare;
		}
		return null;
	}
	
	//removes a player from the game engine
	//returns null if the specified player doesn't exist in the game engine
	public boolean removePlayer(Player player) {
		if(players.remove(player) == true) return true;
		return false;
	}
	
	//adds a callback to the game engine's callback array
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		callback.add(gameEngineCallback);
	}
	
	//removes a callback from the game engine's callback array
	//if the callback doesn't exist then it returns false
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		if(callback.remove(gameEngineCallback) == true) return true;
		else return false;
	}
	
	//returns a collection of all the players stored in the game engine
	public Collection<Player> getAllPlayers(){
		Deque<Player> deque = new ArrayDeque<Player>(players);
		
		return deque;
	}
	
	//calls a specified players place bet method
	public boolean placeBet(Player player, int bet) {
		return player.placeBet(bet);
	}
	
	//creates a new shuffled deck
	public Deque<PlayingCard> getShuffledDeck(){
		//create a list to store all cards of a deck
		List<PlayingCard> deck = new LinkedList<PlayingCard>();
		//each possible card is then added to the list in sequential order
		for(PlayingCard.Suit suit : PlayingCard.Suit.values()) {
			for(PlayingCard.Value value : PlayingCard.Value.values()) {
				PlayingCard card = new PlayingCardImpl(suit, value);
				deck.add(card);
			}
		}
		//list is then shuffled
		Collections.shuffle(deck);
		//converted to type deque to be returned
		Deque<PlayingCard> deque = new ArrayDeque<PlayingCard>(deck);
		
		return deque; 
	}
	
	//refactor method to create a delay
	private void placeDelay(int delay) {
		try {
			TimeUnit.SECONDS.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
