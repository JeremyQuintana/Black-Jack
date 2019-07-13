package view.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JToolBar;
import controller.BetButton;
import controller.DealButton;
import controller.SwitchPlayerButton;
import model.interfaces.Player;
import view.model.ViewModel;

//toolbar panel which changed player to player
public class GameToolBar extends JToolBar{
	
	final ViewModel viewModel;
	
	//create the interactive buttons
	JButton bet;
	JButton deal;
	JComboBox<Player> playerSelect;
	
	public GameToolBar(ViewModel viewModel) {
		this.viewModel = viewModel;
		setBackground(Color.LIGHT_GRAY);	//panel color becomes light gray
		setPreferredSize(new Dimension(getWidth(), 40));	//ensure width is always the full width of window with a height of 40
		
		createComponents();	//create everything in panel
	}
	
	//responsible for creating panel components
	private void createComponents() {
		//create the interactive buttons
		bet = new JButton("Bet");
		deal = new JButton("Deal");
		playerSelect = new JComboBox<Player>();
		playerSelect.setRenderer(new myRenderer());
		
		//set size of buttons
		bet.setPreferredSize(new Dimension(150, 30));
		deal.setPreferredSize(new Dimension(150, 30));
		
		//add the buttons to the toolbar
		add(bet);
		addSeparator();
		add(deal);
		addSeparator();
		add(playerSelect);
		
		//add action listeners to each button
		bet.addActionListener(new BetButton(viewModel));	
		deal.addActionListener(new DealButton(viewModel));
		playerSelect.addActionListener(new SwitchPlayerButton(viewModel));
	}
	
	//updates the visuals of this panel
	public void update() {
		addMissing();
		removeMissing();
	}
	
	//adds any missing player to the combobox
	private void addMissing() {
		//goes through each player in the model
		for(Player user : viewModel.getGameEngine().getAllPlayers()) {
			boolean found = false;	//helper variable to see if the player is in the combobox
			//goes through each item in the combobox
			for(int i = 0; i < playerSelect.getItemCount(); i++) {
				if(playerSelect.getItemAt(i).equals(user)) found = true;	//if the player in the combobox matches that to the compared model player found becomes true
			}
			if(found == false) playerSelect.addItem(user);	//if found is false then it adds the player from the model to the combobox
		}
	}
	
	//removes any player from the combobox not in the model
	private void removeMissing() {
		//goes through each player in the combobox
		for(int i = 0; i < playerSelect.getItemCount(); i++) {
			boolean found = false;	//helper variable to see if the player is in the combobox
			//goes through each player in the model
			for(Player user : viewModel.getGameEngine().getAllPlayers()){
				if(playerSelect.getItemAt(i).equals(user)) found = true;	//if the player in the combobox matches that to the compared model player found becomes true
			}
			if(found == false) playerSelect.removeItemAt(i);	//if the player is not found in the model then the player is removed from the combobox
		}
	}    
	
	//new class to render what is displayed in the combobox as a player is passed in
	//makes it so the combobox only displays name instead of the toString of the player class
	class myRenderer extends DefaultListCellRenderer{
		
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        	super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        	if(value instanceof Player) {
	        	Player player = (Player) value;
	        	setText(player.getPlayerName());
        	}
            return this;
        }

    }

}
