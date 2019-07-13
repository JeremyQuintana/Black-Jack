package view.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.interfaces.PlayingCard;
import view.model.ViewModel;

//creation of the House Game Panel
public class GameHouseBar extends JPanel{
	
	ArrayList<BufferedImage> houseCards = new ArrayList<BufferedImage>();
	private int houseScore = 0;
	
	//Panels dedicated for organisation which only purpose is to organise the contents in the center row by row
	private JPanel topPanel;
	private JPanel cardPanel;
	private JPanel bottomPanel;
	
	//Actual labels that need to be displayed
	private JLabel name;
	private JLabel score;
	
	private final ViewModel viewModel;
	
	public GameHouseBar(ViewModel viewModel) {
		this.viewModel = viewModel;
		setLayout(new BorderLayout());	//set layout of all components in a row by row format
		setBackground(Color.WHITE);	//set color of panel to white
		
		createComponents();	//create everything inside panel
		
		//adds an anonymous listener to listen for panel resizing to redraw cards to appropriate size
		//erases cards then redraws them to correct size
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				cardPanel.removeAll();
				for(int i = 0; i < houseCards.size(); i++) {
					createImg(houseCards.get(i));
				}
				cardPanel.revalidate();
				cardPanel.repaint();
			}
		});
	}
	
	//responsible for creating everything inside the panel
	private void createComponents() {
		//Panels dedicated for organisation which only purpose is to organise the contents in the center row by row
		topPanel = new JPanel();
		cardPanel = new JPanel();
		bottomPanel = new JPanel();
		
		cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.X_AXIS));	//set cardpanel layout to place cards in a row
		
		//Actual labels that need to be displayed
		name = new JLabel("House");
		score = new JLabel("Score:");
		
		//setOpaque prevents the organising panels from being coloured the default colour and hence adopting the container colour
		topPanel.setOpaque(false);
		cardPanel.setOpaque(false);
		bottomPanel.setOpaque(false);
		
		//Setting the alignment of all organisation panels and contents 
		name.setHorizontalAlignment(JTextField.CENTER);
		score.setHorizontalAlignment(JTextField.CENTER);
		topPanel.setAlignmentX(CENTER_ALIGNMENT);
		cardPanel.setAlignmentX(CENTER_ALIGNMENT);
		bottomPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		//adding to organisation panels
		add(topPanel, BorderLayout.NORTH);
		add(cardPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		
		//adding the contents to respective organisation panels
		topPanel.add(name);
		bottomPanel.add(score);
	}
	
	//method to update the visual components
	public void update() {
		//sets the text of score to appropriate house score
		score.setText("Score: " + houseScore);
		
		//removes all cards in the panel and redraws it
		cardPanel.removeAll();
		cardPanel.revalidate();
		cardPanel.repaint();
		//for every card stored in playerCards for the specific player it draws the image
		for(int i = 0; i < houseCards.size(); i++) {
			createImg(houseCards.get(i));
		}
	}
	
	//helper method to draw the card to the cardpanel
	private void createImg(BufferedImage img) {
		float calc = ((float)cardPanel.getHeight()/(float)img.getHeight())*img.getWidth();	//calculates the width according to scale with the changed height
		Image dimg = img.getScaledInstance((int) calc, cardPanel.getHeight(), Image.SCALE_SMOOTH);	//creates an image with correct dimensions
		ImageIcon imageIcon = new ImageIcon(dimg);
		JLabel panel = new JLabel(imageIcon);

		cardPanel.add(panel);	//adds the image to the cardpanel
	}
	
	//method called when adding a card to the player
	public void setCard(PlayingCard card) {
		String fileName = "Images" + File.separator + card.getSuit() + card.getValue() + ".png";
		
		BufferedImage img;
		try {
			img = ImageIO.read(new File(fileName));	//reads the image file for the card
			houseCards.add(img);	//stores the buffered image to the playercards array to be drawn later
		}
		catch(Exception E) {
			System.out.println("ERROR - COULD NOT FIND IMAGE");
		}
	}
	
	//clearing methods to prepare for new round
	//clears all cards stored in houseCard array
	public void clearHouseCards(){
		houseCards = new ArrayList<BufferedImage>();
	}
	
	//set the score of the house
	public void setHouseScore(int houseScore) {
		this.houseScore = houseScore;
	}
	
	//get score of the house
	public int getHouseScore() {
		return houseScore;
	}
}
