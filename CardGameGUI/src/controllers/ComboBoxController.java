package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.MainView;

/**
 * Action Listener for combox box for selection of players
 *
 */
public class ComboBoxController implements ActionListener {

	private GameEngine gameEngine;
	private MainView mainView;
	private JButton dealBtn;
	private JButton betBtn;

	public ComboBoxController(GameEngine gameEngine, MainView mainView, JButton dealBtn, JButton betBtn) {
		this.gameEngine = gameEngine;
		this.mainView = mainView;
		this.dealBtn = dealBtn;
		this.betBtn = betBtn;
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		JComboBox<String> playersComboBox = (JComboBox<String>) event.getSource();
		
		String playerId = (String) playersComboBox.getSelectedItem();
		
		if (playerId != null) {
			mainView.switchPlayer(playerId);
			Player player = gameEngine.getPlayer(playerId);
			
			if (player != null) {
				// For flayer
				dealBtn.setActionCommand(playerId);
				betBtn.setActionCommand(playerId);

				int playerResult = player.getResult();
				if (playerResult != 0) {
					betBtn.setEnabled(false);
					dealBtn.setEnabled(false);	
				} else {
					betBtn.setEnabled(true);
					int playerBet = player.getBet();
					if (playerBet != 0) {
						dealBtn.setEnabled(true);
					} else {
						dealBtn.setEnabled(false);
					}
				}
			} else {
				// For house (non-player)
				betBtn.setEnabled(false);
				dealBtn.setEnabled(false);
			}
			
			// Redraw card view panel
			mainView.getCurrentMainView(playerId).redrawAll();

		}
	}
}
