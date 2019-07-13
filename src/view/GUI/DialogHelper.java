package view.GUI;

import javax.swing.JOptionPane;

//dialog class to display a dialog box
public class DialogHelper extends JOptionPane{
	//asks for and returns an input with passed in question
	public String askInput(String Question) {
		return showInputDialog(this, Question);
	}
	
	//displays an error
	public void displayError(String Error) {
		showMessageDialog(this, Error);
	}
}
