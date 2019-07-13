package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.model.ViewModel;

//listner class run when deal button is clicked
public class DealButton implements ActionListener{
	
	private final ViewModel viewModel;	//reference varibale for viewmodel
	
	public DealButton(ViewModel viewModel) {
		this.viewModel = viewModel;
	}
	
	public void actionPerformed(ActionEvent e) {
		viewModel.dealPlayer();	//calls dealplayer method in viewmodel
	}
}