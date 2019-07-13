package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.model.ViewModel;

//listner class run when the bet button is clocked
public class BetButton implements ActionListener{

	private final ViewModel viewModel;	//reference variable for viewmodel
	
	public BetButton(ViewModel viewModel) {
		this.viewModel = viewModel;
	}

	public void actionPerformed(ActionEvent e) {
		viewModel.betPlayer();	//runs betplayer method in viewmodel
	}
}
