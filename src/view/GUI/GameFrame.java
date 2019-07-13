package view.GUI;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

import model.interfaces.GameEngine;
import view.model.ViewModel;

//creates and holds the GUI
public class GameFrame extends JFrame {
	
	//final instantiated reference variabels
	private final GameEngine gameEngine;
	private final ViewModel viewModel;
	
	//create each panel needed
	private GameMenuBar menuBar;
	private GameToolBar toolBar;
	private GameStatusBar statusBar;
	private GameSummaryBar summaryBar;
	private GameMainBar mainBar;
	
	public GameFrame(GameEngine gameEngine) {
		super("Black Jack");	//title of window
		this.gameEngine = gameEngine;
		this.viewModel = new ViewModel(this.gameEngine, this);
		setBounds(0, 0, 640, 480);	//set default window size
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//close program when you exit the window
		setLayout(new BorderLayout(2, 2));	//set the layout of components added to the frame with a 2 thick border
		setBackground(Color.BLACK);	//set color of frame to black
		
		createPanels();	//create component panels
		
		setVisible(true);	//make the frame appear
	}
	
	//responsible for crating and adding all component panels
	private void createPanels() {
		//create each panel needed
		menuBar = new GameMenuBar(viewModel);
		toolBar = new GameToolBar(viewModel);
		statusBar = new GameStatusBar();
		summaryBar = new GameSummaryBar(viewModel);
		mainBar = new GameMainBar(viewModel);
		
		//make it so the toolbar of type JToolBar cannot be moves
		toolBar.setFloatable(false);
		
		//add all the components to their respective areas in the layout
		setJMenuBar(menuBar);
		add(toolBar, BorderLayout.NORTH);
		add(statusBar, BorderLayout.SOUTH);
		add(summaryBar, BorderLayout.WEST);
		add(mainBar, BorderLayout.CENTER);
	}
	
	//getter methods for all panels
	public ViewModel getViewModel() {
		return viewModel;
	}
	
	public GameToolBar getToolBar() {
		return toolBar;
	}

	public GameStatusBar getStatusBar() {
		return statusBar;
	}

	public GameSummaryBar getSummaryBar() {
		return summaryBar;
	}

	public GameMainBar getMainBar() {
		return mainBar;
	}
}
