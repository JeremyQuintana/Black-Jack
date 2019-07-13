package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.interfaces.GameEngine;
import view.model.ViewModel;

//listener class that runs when add player button is clicked
public class AddPlayerButton implements ActionListener{

	final ViewModel viewModel;	//reference variable to the viewmodel
	
	public AddPlayerButton(ViewModel viewModel) {
		this.viewModel = viewModel;
	}
	
	public void actionPerformed(ActionEvent e) {
		viewModel.addPlayer();	//runs addplayer method in viewmodel
	}
}
