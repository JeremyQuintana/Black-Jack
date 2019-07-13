package view.GUI;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.interfaces.Player;
import view.model.ViewModel;

public class GameSummaryBar extends JScrollPane{
			
	private final ViewModel viewModel;
	
	private JTable table;	//visual view of the contents of the data
	private DefaultTableModel data = new DefaultTableModel();	//contains the contents of how the table should be formatted
	
	public GameSummaryBar(ViewModel viewModel) {
		this.viewModel = viewModel;
		setPreferredSize(new Dimension(198, getHeight()));	//maintain size to be the width of the table
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);	//ensure the scrollbar is always visible to remove awkward space left when the table is small
		
		createComponents();	//creates the table
	}
	
	//responsible for creating everything inside the class
	private void createComponents() {
		//set the columns headings of the table
		data.addColumn("ID:");
		data.addColumn("Name:");
		data.addColumn("Points:");
		
		table = new JTable(data);	//create a the table with the header data
		
		//set the size of each column
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		//make it so that the table cannot be altered by user
		table.setFocusable(false);
		table.setRowSelectionAllowed(false);
		
		//gets this class to focus on the table
		getViewport().add(table);
	}
	
	//method responsible for getting data from the model and updating the view of this panel
	public void update() {
		addMissing();
		removeMissing();
		updatePoints();
	}
	
	//adds any missing player
	private void addMissing() {
		//searches through all players in the model
		for(Player user : viewModel.getGameEngine().getAllPlayers()) {
			//helper variables to see if the player needs to be added of updated
			boolean found = false;
			boolean update = false;
			int row = 0;
			
			//goes through all data in the table
			for(int i = 0; i < data.getRowCount(); i++) {
				//if data in the table equals the comparing player then it found the player
				if(data.getValueAt(i, 0).equals(user.getPlayerId())) {
					found = true;
					//check if the player of the name changed to see if the player needs to be updated
					if(data.getValueAt(i, 1).equals(user.getPlayerName()) == false) {
						update = true;
						row = i;
					}
				}
			}
			//if the player name did change then it removes the row and re adds it
			if(update == true) {
				data.removeRow(row);
				data.addRow(new Object[] {user.getPlayerId(), user.getPlayerName(), user.getPoints()});
			}
			//if a player in the model was not found in the table then it adds the player
			if(found == false) data.addRow(new Object[] {user.getPlayerId(), user.getPlayerName(), user.getPoints()});
		}
	}
	
	//removes any player in the table that is not found in the modal
	private void removeMissing() {
		//searches through each row in the tbale
		for(int i = 0; i < data.getRowCount(); i++) {
			boolean found = false;	//helper variable to see if the player is found in the model
			//searches through each player in the model
			for(Player user : viewModel.getGameEngine().getAllPlayers()){
				//if the player is found in the model then it sets found to true
				if(data.getValueAt(i, 0).equals(user.getPlayerId())) found = true;
			}
			if(found == false) data.removeRow(i);	//if a player is not found in the model then the row is removed
		}
	}
	
	//changes the values of each players points according to the model
	private void updatePoints() {
		//goes through each row and gets the id
		for(int i = 0; i < data.getRowCount(); i++) {
			String id = (String) data.getValueAt(i, 0);
			int updatePoints = viewModel.getGameEngine().getPlayer(id).getPoints();	//gets the points of the player from the modal
			data.setValueAt(updatePoints, i, 2);	//sets the data at the points column to the points obtained in the model
		}
	}
}
