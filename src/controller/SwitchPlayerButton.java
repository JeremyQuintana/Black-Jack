package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GUI.GameFrame;
import view.model.ViewModel;

//listener class run when item in combobox is clicked
public class SwitchPlayerButton implements ActionListener{
	
	final ViewModel viewModel;	//reference variable for viewmodel
	
	public SwitchPlayerButton(ViewModel viewModel) {
		this.viewModel = viewModel;
	}
	
	public void actionPerformed(ActionEvent e) {
		JComboBox<?> comboBox = (JComboBox<?>) e.getSource();	//gets the item of selected in the combobox
		Player selected = (Player)comboBox.getSelectedItem();	//turns the selected item into a type Player
		
		viewModel.switchPlayer(selected);	//calls the switchplayer method in viewmodel and passes in the player selected
	}
}