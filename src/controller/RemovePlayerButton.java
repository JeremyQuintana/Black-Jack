package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.interfaces.GameEngine;
import view.GUI.DialogHelper;
import view.GUI.GameFrame;
import view.model.ViewModel;

//listener class run when remove player button is clicked
public class RemovePlayerButton implements ActionListener{

	final ViewModel viewModel;	//reference variable for viewmodel
	
	public RemovePlayerButton(ViewModel viewModel) {
		this.viewModel = viewModel;
	}
	
	public void actionPerformed(ActionEvent e) {
		viewModel.removePlayer();	//calls removeplayer method in viewmodel
	}
}