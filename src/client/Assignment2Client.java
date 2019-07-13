package client;

import java.util.logging.Logger;

import model.GameEngineImpl;
import model.SimplePlayer;
import model.interfaces.GameEngine;
import view.GameEngineCallbackGUI;
import view.GameEngineCallbackImpl;

public class Assignment2Client {

	private static Logger logger = Logger.getLogger("assignment2");
	
	public static void main(String[] args) {
		final GameEngine gameEngine = new GameEngineImpl();
		gameEngine.addGameEngineCallback(new GameEngineCallbackImpl());
	    gameEngine.addGameEngineCallback(new GameEngineCallbackGUI(gameEngine));
	}

}
