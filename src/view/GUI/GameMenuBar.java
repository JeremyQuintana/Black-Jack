package view.GUI;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.AddPlayerButton;
import controller.RemovePlayerButton;
import model.interfaces.GameEngine;
import view.model.ViewModel;

//menu bar which holds global functionalities between each player
public class GameMenuBar extends JMenuBar{
	
	final ViewModel viewModel;
	
	public GameMenuBar(ViewModel viewModel) {
		this.viewModel = viewModel;
		createComponents();	//create everything inside the panel
	}
	
	//responsible for creating everything in the panel
	private void createComponents() {
		//creates buttons
		JMenu menu = new JMenu("Menu");	//creates dropdown
		JMenuItem addPlayer = new JMenuItem("Add Player");
		JMenuItem removePlayer = new JMenuItem("Remove Player");
		
		//add components to bar
		add(menu);	//add menu to bar
		//add buttons in a dropdown under the menu button
		menu.add(addPlayer);
		menu.add(removePlayer);
		
		//adds action listeners to both buttons
		addPlayer.addActionListener(new AddPlayerButton(viewModel));
		removePlayer.addActionListener(new RemovePlayerButton(viewModel));
	}

}
