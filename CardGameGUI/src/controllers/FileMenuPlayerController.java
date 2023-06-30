package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import validate.PlayerImpl;
import view.MainView;
import view.SummaryView;
import view.PlayerControlView;

/**
 * Action controller Controller for file menu player controller that includes adding and removing players
 *
 */
public class FileMenuPlayerController implements ActionListener {

	private GameEngine gameEngine;
	private PlayerControlView playerControlView;
	private MainView mainView;
	private SummaryView summaryView;


	public FileMenuPlayerController(GameEngine gameEngine, PlayerControlView playerControlView, MainView mainView, SummaryView summaryView) {
		this.gameEngine = gameEngine;
		this.playerControlView = playerControlView;
		this.mainView = mainView;
		this.summaryView = summaryView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch (action) {
			case "add": {
				Player player = getPlayername();
				if (player != null) {
					gameEngine.addPlayer(player);
					mainView.insertPlayer(player);
				}
				break;
			}
			
			case "remove": {
				String playerId = JOptionPane.showInputDialog("Enter Player ID you want to remove", 0); 
				if (playerId != null) {
					Player player = gameEngine.getPlayer(playerId);
					if (player != null) {
						gameEngine.removePlayer(player);
						mainView.removePlayer(player);
					} else {
						JOptionPane.showMessageDialog(null, "Player does not exist.");
					}	
				}
				break;
			}	
		}
	
		List<Player> players = (List<Player>) gameEngine.getAllPlayers();
		playerControlView.addPlayers(players);
		summaryView.insertPlayers(players);
	}

	private Player getPlayername() {
		Player player = null;
		JTextField nameTextField = new JTextField();
		JTextField pointsTextField = new JTextField();
		Object[] message = { 
			"Player name:", nameTextField, 
			"Points:", pointsTextField 
		};
		
		do {
			int option = JOptionPane.showConfirmDialog(null, message, "Enter player details.", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.CANCEL_OPTION) {
				return null;
			}
			
			String name = nameTextField.getText();
			String point = pointsTextField.getText();
			
			
			
			if (name != null && point != null) {
				try {
					int pointi = Integer.parseInt(point);
					
					if (gameEngine.getPlayer(name) != null) {
						JOptionPane.showMessageDialog(null, "The given name already exists");
					} else if (pointi <= 0) {
						JOptionPane.showMessageDialog(null, "The given point must be a positive number");
					} else {
						return new SimplePlayer(name, name, pointi);
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "The given point must be a positive number");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Name must be unique and points must be a positive number");
			}	
		} while (true);
	}
}
