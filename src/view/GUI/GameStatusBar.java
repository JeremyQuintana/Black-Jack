package view.GUI;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

//Status bar to display game status
public class GameStatusBar extends JPanel{
	
	private JLabel status;
	
	public GameStatusBar() {
		setBackground(Color.LIGHT_GRAY);	//set color of panel to light gray
		setLayout(new FlowLayout(FlowLayout.LEFT));	//push the status detail to be left aligned
		
		createComponents();
	}
	
	//responsible for creating everything inside the panel
	public void createComponents(){
		status = new JLabel("Status bar:");
		add(status);	//add the text for the status bar
	}
	
	//method responsible for updating the message in the status bar
	public void update(String message) {
		status.setText("Status bar: " + message);
	}
}
