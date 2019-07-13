package view.GUI;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import view.model.ViewModel;

//Responsible for creating and placing Player and House Game Panel in the correct area in the frame
public class GameMainBar extends JPanel{
	
	//creation of both Player and House Game panels
	private GamePlayerBar playerBar;
	private GameHouseBar houseBar;
	
	private final ViewModel viewModel;
	
	public GameMainBar(ViewModel viewModel) {
		this.viewModel = viewModel;
		setBackground(Color.BLACK);	//set color of panel to black
		setLayout(new GridLayout(2, 1, 0, 2));	//sets each of the 2 created panels one under the other with the same size
		
		createComponents();
	}
	
	//responsible for creating everything inside the panel
	private void createComponents() {
		//creation of both Player and House Game panels
		playerBar = new GamePlayerBar(viewModel);
		houseBar = new GameHouseBar(viewModel);
		
		//adds the panels to the organising panel to place it in the right place in the frame
		add(playerBar);
		add(houseBar);
	}
	
	//getter methods for child panels
	public GamePlayerBar getPlayerBar() {
		return playerBar;
	}

	public GameHouseBar getHouseBar() {
		return houseBar;
	}
	
	
}
