package view.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.naming.spi.DirectoryManager;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.interfaces.GameEngine;
import model.interfaces.PlayingCard;
import view.model.ViewModel;

//creation of the Player Game Panel
public class GamePlayerBar extends JPanel{
	
	//Panels dedicated for organisation which only purpose is to organise the contents in the center row by row
	private JPanel topPanel;
	private JPanel cardPanel;
	private JPanel bottomPanel;
	
	//Actual labels that need to be displayed
	private JLabel name;
	private JLabel score;
	private JLabel bet;

	private final ViewModel viewModel;
	
//	private HashMap<String, ArrayList<PlayingCard>> playerCards = new HashMap<String, ArrayList<PlayingCard>>();
	private HashMap<String, ArrayList<BufferedImage>> playerCards = new HashMap<String, ArrayList<BufferedImage>>();
	private HashMap<String, Integer> playerScores = new HashMap<String, Integer>();
	
	public GamePlayerBar(ViewModel viewModel) {
		this.viewModel = viewModel;
		setLayout(new BorderLayout());	//set layout of all components in a row by row format
		setBackground(Color.WHITE);	//set color of panel to white
		
		createComponents();	//create everything inside panel
		
		//adds an anonymous listener to listen for panel resizing to redraw cards to appropriate size
		//erases cards then redraws them to correct size
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				cardPanel.removeAll();
				String id = viewModel.getSelectedID();	//gets the id of the currently selected player to show correct cards
				if(id != null) {	//if statement to prevent error when game starts with no players
					for(int i = 0; i < playerCards.get(id).size(); i++) {	//goes through the array cards of currently selected player
						createImg(playerCards.get(id).get(i));	//prints the card
					}
				}
				//repaints the panel
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
		name = new JLabel();
		score = new JLabel("Score:");
		bet = new JLabel("Bet:");
		
		//setOpaque prevents the organising panels from being coloured the default colour and hence adopting the container colour
		topPanel.setOpaque(false);
		cardPanel.setOpaque(false);
		bottomPanel.setOpaque(false);
		
		//Setting the alignment of all organisation panels and contents 
		bottomPanel.setLayout(new GridLayout(0,1));
		name.setHorizontalAlignment(JTextField.CENTER);
		score.setHorizontalAlignment(JTextField.CENTER);
		bet.setHorizontalAlignment(JTextField.CENTER);
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
		bottomPanel.add(bet);
	}
	
	//method to update the visual components
	public void update() {
		//if statement to see if player is selected
		if(viewModel.getSelectedID() != null) {
			//if a player is selected it displays their information
			name.setText(viewModel.getGameEngine().getPlayer(viewModel.getSelectedID()).getPlayerName());
			score.setText("Score: " + playerScores.get(viewModel.getSelectedID()));
			bet.setText("Bet: " + viewModel.getGameEngine().getPlayer(viewModel.getSelectedID()).getBet());
		}
		else {
			//else it displays nothing
			name.setText("");
			score.setText("Score:");
			bet.setText("Bet:");
		}
		
		//removes all cards in the panel and redraws it
		cardPanel.removeAll();
		cardPanel.revalidate();
		cardPanel.repaint();
		
		if(playerCards.size() != 0) {	//check that there are players to read cards from
			//for every card stored in playerCards for the specific player it draws the image
			for(int i = 0; i < playerCards.get(viewModel.getSelectedID()).size(); i++) {
				createImg(playerCards.get(viewModel.getSelectedID()).get(i));
			}
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
	public void setCard(String id, PlayingCard card) {
		String fileName = "Images" + File.separator + card.getSuit() + card.getValue() + ".png";	//creates a string in the format to get the appropriate card image
		
		BufferedImage img;
		try {
			img = ImageIO.read(new File(fileName));	//reads the image file for the card
			playerCards.get(id).add(img);	//stores the buffered image to the playercards array to be drawn later
		}
		catch(Exception E) {
			System.out.println("ERROR - COULD NOT FIND IMAGE");
		}
	}
	
	//set the score of the given player in the hashmap
	public void setScore(String id, int score) {
		playerScores.put(id, score);
	}
	
	//getter methods to get maps
	public HashMap<String, Integer> getScores() {
		return playerScores;
	}
	
	public HashMap<String, ArrayList<BufferedImage>> getCards(){
		return playerCards;
	}
	
	//clearing methods to prepare for new round
	//removes all points of players
	public void clearPoints() {
		Map<String, Integer> pointsMap = playerScores; 
		for(Entry<String, Integer> points : pointsMap.entrySet()) {
			points.setValue(0);
		}
	}
	
	//removes all cards of all players
	public void clearPlayerCards() {
		Map<String, ArrayList<BufferedImage>> cardMap = playerCards; 
		for(Entry<String, ArrayList<BufferedImage>> arrayCards : cardMap.entrySet()) {
			arrayCards.setValue(new ArrayList<BufferedImage>());
		}
	}
}
