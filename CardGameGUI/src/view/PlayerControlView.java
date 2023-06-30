package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import controllers.BetButtonActionController;
import controllers.ComboBoxController;
import controllers.DealButtonActionController;
import model.interfaces.GameEngine;
import model.interfaces.Player;

/**
 * Player Control View for controlling player action like bet, deal and switchign players
 */
public class PlayerControlView extends JPanel {

	private JComboBox<String> choosePlayerComboBox;
	JButton dealBtn;
	JButton betBtn;

	public PlayerControlView(GameEngine gameEngine, MainView mainView, StatusBarView statusBarView, SummaryView summaryView) {
		setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		dealBtn = new JButton("DEAL");
		betBtn = new JButton("BET");
		
		// Add deal button
		dealBtn.setEnabled(false);
		dealBtn.addActionListener(new DealButtonActionController(gameEngine, 100, dealBtn, betBtn, statusBarView));
		add(dealBtn);
		
		// Add bet button
		betBtn.addActionListener(new BetButtonActionController(gameEngine, dealBtn, betBtn, statusBarView, summaryView));
		add(betBtn);
	
		// Add choose player combobox
		choosePlayerComboBox = new JComboBox<String>();
		choosePlayerComboBox.addActionListener(new ComboBoxController(gameEngine, mainView, dealBtn, betBtn));
		choosePlayerComboBox.setPreferredSize(new Dimension(200, 20));
		add(choosePlayerComboBox);
	}

	public void addPlayers(List<Player> players) {
		choosePlayerComboBox.removeAllItems();
		choosePlayerComboBox.addItem("house");
		
		for (Player player: players) {
			choosePlayerComboBox.addItem(player.getPlayerName());
		}
		
		int currentIndex = players.size();
		choosePlayerComboBox.setSelectedIndex(currentIndex);
	}

	public String getCurrentPlayerId() {
		return (String) choosePlayerComboBox.getSelectedItem();
	}
	
	public void switchToHouse() {
		choosePlayerComboBox.setSelectedIndex(0);
	}
}
