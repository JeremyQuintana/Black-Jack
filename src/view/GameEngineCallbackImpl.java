package view;

import java.util.logging.Level;
import java.util.logging.Logger;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

/**
 * 
 * Skeleton/Partial example implementation of GameEngineCallback showing Java logging behaviour
 * 
 * @author Caspar Ryan
 * @see view.interfaces.GameEngineCallback
 * 
 */
public class GameEngineCallbackImpl implements GameEngineCallback
{
   private final Logger logger = Logger.getLogger(this.getClass().getName());

   public GameEngineCallbackImpl()
   {
      // FINE shows dealing output, INFO only shows result
      logger.setLevel(Level.FINE);
   } 
   
   //logs the player's dealt card
   public void nextCard(Player player, PlayingCard card, GameEngine engine)
   {
      // intermediate results logged at Level.FINE
      logger.log(Level.FINE, String.format("%-1s .. %-1s", "Card Dealt to " + player.getPlayerName(), card.toString()));
      //System.out.println(String.format("%-20s%-30s%-15s", "Name: " + player.getPlayerName(), "Card: " + card.getValue() + " of " + card.getSuit(), "Score: " + card.getScore()));
   }
   
   //logs the player's card that busted
   public void bustCard(Player player, PlayingCard card, GameEngine engine) {
	   logger.log(Level.FINE, String.format("%-1s .. %-1s ... YOU BUSTED!", "Card Dealt to " + player.getPlayerName(), card.toString()));
	   //System.out.println(String.format("BUST\n%-20s%-30s%-15s", "Name: " + player.getPlayerName(), "Card: " + card.getValue() + " of " + card.getSuit(), "Score: " + card.getScore()));
   }
   
   //logs the player's results just before they bust
   public void result(Player player, int result, GameEngine engine)
   {
      // final results logged at Level.INFO
      logger.log(Level.INFO, String.format("%-1s, %-1s", player.getPlayerName(), "final result=" + result));
      //System.out.println(String.format("%-20s%-20s", "Name: " + player.getPlayerName(), "Final Result: " + result));
   }
   
   //logs the house's dealt card
   public void nextHouseCard(PlayingCard card, GameEngine engine) {
	   logger.log(Level.FINE, String.format("%-1s .. %-1s", "Card Dealt to House", card.toString()));
	   //System.out.println(String.format("%-20s%-30s%-15s", "Name: House", "Card: " + card.getValue() + " of " + card.getSuit(), "Score: " + card.getScore()));
   }
   
   //logs the house's dealt card that busted the hand
   public void houseBustCard(PlayingCard card, GameEngine engine) {
	   logger.log(Level.FINE, String.format("%-1s .. %-1s ... YOU BUSTED!", "Card DealHouse", card.toString()));
	   //System.out.println(String.format("BUST\n%-20s%-30s%-15s", "Name: House", "Card: " + card.getValue() + " of " + card.getSuit(), "Score: " + card.getScore()));
   }
   
   //logs the house's hand result before the hand was bust
   //logs all the players results
   public void houseResult(int result, GameEngine engine) {
	   logger.log(Level.INFO, String.format("%-1s, %-1s", "House", "final result=" + result));
       //System.out.println(String.format("%-20s%-20s", "Name: House", "Final Result: " + result));
	   
	   //creates a string of all the players results in a correct new line format
	   String playerResults = "Final Player Results";
	   for(Player players : engine.getAllPlayers()) playerResults += "\nPlayer: " + players;	//loop that constructs a string of all the players results in correct format
	   logger.log(Level.INFO, playerResults);	//logs the results of all players
   }
   
}
