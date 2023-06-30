package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.StatusBarView;

/**
 * Action controller for deal button
 *
 */
public class DealButtonActionController implements ActionListener {

	private GameEngine gameEngine;
	private int delay;
	private JButton dealBtn;
	private JButton betBtn;
	private StatusBarView statusBarView;

	/**
	 * @param gameEngine
	 * @param delay
	 */
	public DealButtonActionController(GameEngine gameEngine, int delay, JButton dealBtn, JButton betBtn, StatusBarView statusBarView) {
		this.gameEngine = gameEngine;
		this.delay = delay;
		this.dealBtn = dealBtn;
		this.betBtn = betBtn;
		this.statusBarView = statusBarView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String playerId = e.getActionCommand();
		Player player = gameEngine.getPlayer(playerId);
		
		if (player != null && player.getBet() != 0) {
			
			betBtn.setEnabled(false);
			dealBtn.setEnabled(false);

			statusBarView.changeStatus(player.getPlayerName() + " is dealing...");
			
			new Thread() {
				@Override
				public void run() {
					gameEngine.dealPlayer(player, delay);
				}
			}.start();
		}
	}
}
