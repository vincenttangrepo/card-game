package view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controllers.FileMenuPlayerController;
import model.interfaces.GameEngine;

/**
 * Menu view that has several options like add player, remove player, etc.
 *
 */
public class MenuView extends JMenuBar {
	JMenu fileMenuItem;
	JMenuItem addPlayerMenuItem;
	JMenuItem removePlayerMenuItem;
	
	public MenuView(GameEngine gameEngine, PlayerControlView playerControlView, MainView mainView, SummaryView summaryView) {
		// File
		fileMenuItem = new JMenu("File");
		addPlayerMenuItem = new JMenuItem("Add a player");
		removePlayerMenuItem = new JMenuItem("Remove a player");
		
		initialize(gameEngine, playerControlView, mainView, summaryView);
	}
	
	private void initialize(GameEngine gameEngine, PlayerControlView playerControlView, MainView mainView, SummaryView summaryView) {
		// add player
		addPlayerMenuItem.addActionListener(new FileMenuPlayerController(gameEngine, playerControlView, mainView, summaryView));
		addPlayerMenuItem.setActionCommand("add");
		fileMenuItem.add(addPlayerMenuItem);
		
		// remove player
		removePlayerMenuItem.addActionListener(new FileMenuPlayerController(gameEngine, playerControlView, mainView, summaryView));
		removePlayerMenuItem.setActionCommand("remove");
		fileMenuItem.add(removePlayerMenuItem);

		add(fileMenuItem);
	}
}
