package model;

import model.interfaces.*;

public class SimplePlayer implements Player {
	
	private String name;
	private String id;
	private int points;
	
	private int bet;
	private int result;
	
	//instantiates all needed variables to create a player
	public SimplePlayer(String id, String name, int points) {
		this.id = id;
		this.name = name;
		this.points = points;
	}
	
	//returns player name
	public String getPlayerName() {
		return name;
	}
	
	//sets player name
	public void setPlayerName(String name) {
		this.name = name;
	}
	
	//returns players points
	public int getPoints() {
		return points;
	}
	
	//sets players points
	public void setPoints(int points) {
		this.points = points;
	}
	
	//returns players id
	public String getPlayerId() {
		return id;
	}
	
	//sets the players bet if valid
	public boolean placeBet(int bet) {
		//checks if the bet will not reduce points to below 0 if the bet is lost
		//if it the bet is invalid returns false
		if ((bet >= 0) && ((points - bet) >= 0)) {
			this.bet = bet;
			return true;
		}
		else {
			return false;
		}
	}
	
	//returns the players bet
	public int getBet() {
		return bet;
	}
	
	//sets the players bet to 0
	public void resetBet() {
		bet = 0;
	}
	
	//returns the result of the players entire hand
	public int getResult() {
		return result;
	}
	
	//sets the result of the players hand
	//called after all the cards have been dealt to the player
	public void setResult(int result) {
		this.result = result;
	}
	
	//returns the players details
	public String toString() {
		return "id=" + id + " ,name=" + name + " ,points=" + points;
	}
}

