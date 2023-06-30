
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.StatusBarView;
import view.SummaryView;

/**
 * Action Controller for bet button
 *
 */
public class BetButtonActionController implements ActionListener {

	private GameEngine gameEngine;
	private JButton dealButton;
	private JButton betButton;
	private StatusBarView statusBar;
	private SummaryView summaryPanel;


	public BetButtonActionController(GameEngine gameEngine, JButton dealButton, JButton betButton, StatusBarView statusBar, SummaryView summaryPanel) {
		this.gameEngine = gameEngine;
		this.dealButton = dealButton;
		this.betButton = betButton;
		this.statusBar = statusBar;
		this.summaryPanel = summaryPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String playerId = e.getActionCommand();
		Player player = gameEngine.getPlayer(playerId);
		if (player != null) {
			int bet = betDialog(player);
			
			Collection<Player> allPlayers = gameEngine.getAllPlayers();
			summaryPanel.insertPlayers(allPlayers);
			statusBar.changeStatus(player.getPlayerName() + " bet amount - " + bet);
			dealButton.setEnabled(true);
		
		}
	}

	private int betDialog(Player player) {
		int bet = 0;
		
		boolean didBet = false;
		
		do {
			String betStr = JOptionPane.showInputDialog("Bet amount", 0);
			if (betStr != null) {
				try {
					bet = Integer.parseInt(betStr);
				} catch (NumberFormatException ex) {
					bet = 0;
				}
				didBet = gameEngine.placeBet(player, bet);
			}
				
			if (!didBet) {
				JOptionPane.showMessageDialog(null, "Invalid bet or insuccifient points. Try again.");
			}
			
		} while(!didBet);
		
		return bet;
	}

}
