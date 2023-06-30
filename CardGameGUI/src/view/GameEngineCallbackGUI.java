package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controllers.FrameAdapter;
import model.PlayingCardImpl;
import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import model.interfaces.PlayingCard.Suit;
import model.interfaces.PlayingCard.Value;
import view.interfaces.GameEngineCallback;

/**
 * Entrypoint for UI and flow
 *
 */
public class GameEngineCallbackGUI implements GameEngineCallback {

	private JFrame gameFrame;
	private MenuView menuView;
	private PlayerControlView playerControlView;
	private StatusBarView statusBarView;
	private SummaryView summaryView;
	private MainView mainView;

	public GameEngineCallbackGUI(GameEngine gameEngine) {
		// Initialize game frame
		gameFrame = new JFrame("Game");
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setSize(800, 600);
		
		// Summary view
		summaryView = new SummaryView();
		
		// main view
		mainView = new MainView(summaryView, gameFrame);

		// Status bar view
		statusBarView = new StatusBarView(gameFrame.getWidth());
		statusBarView.changeStatus("Ready");
		
		// Player controls view
		playerControlView = new PlayerControlView(gameEngine, mainView, statusBarView, summaryView);

		// menu view
		menuView = new MenuView(gameEngine, playerControlView, mainView, summaryView);
		gameFrame.setJMenuBar(menuView);

		gameFrame.getContentPane().add(BorderLayout.NORTH, playerControlView);
		gameFrame.getContentPane().add(BorderLayout.CENTER, mainView);
		gameFrame.getContentPane().add(BorderLayout.SOUTH, statusBarView);
		gameFrame.setVisible(true);

	}

	private void insertUICard(String playerId, PlayingCard card) {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		CardsView cv = mainView.getCurrentMainView(playerId);
		cv.insertPlayingCard(card);
		mainView.redraw();
	}

	@Override
	public void nextCard(Player player, PlayingCard card, GameEngine engine) {
		insertUICard(player.getPlayerId(), card);
	}

	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) {
		insertUICard(player.getPlayerId(), card);
		statusBarView.changeStatus(player.getPlayerName() + " busted!");

	}

	@Override
	public void result(Player player, int result, GameEngine engine) {
		Collection<Player> players =  engine.getAllPlayers();
		
		summaryView.insertPlayers(players);
		mainView.redraw();

		
		boolean houseTurn = true;
		
		for (Player p : players) {
			if (p.getResult() == 0) {
				houseTurn = false;
			}
		}
		
		if (houseTurn) {
			JOptionPane.showMessageDialog(null, "House is going to deal now.");
			playerControlView.switchToHouse();
			engine.dealHouse(100);
		}

		statusBarView.changeStatus(player.getPlayerName() + " got a result " + result);

	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) {
		insertUICard("house", card);
	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) {
		insertUICard("house", card);
	}

	@Override
	public void houseResult(int result, GameEngine engine) {
		List<Player> players = (List<Player>) engine.getAllPlayers();

		Player winnerPlayer = players.get(0);
		
		for (int i = 1; i < players.size(); i++) {
			Player p = players.get(i);
			if (winnerPlayer.getResult() < p.getResult()) {
				winnerPlayer = p;
			}	
		}
		
		mainView.redraw();
		
		if (winnerPlayer.getResult() > result) {
			String message = winnerPlayer.getPlayerName() + " has won, Thanks for your time.";
			statusBarView.changeStatus(message);
			JOptionPane.showMessageDialog(null, message);
		} else {
			JOptionPane.showMessageDialog(null, "House has won. Thanks for your time.");
		}

		int option = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Quit", JOptionPane.YES_NO_OPTION);
		
		if (option == 0) {
			for (Player p: players) {
				p.resetBet();
				p.setResult(0);
			}
			summaryView.insertPlayers(players);
			engine.reshuffle();
		} else {
			// exit
			System.exit(0);
		}
	}

}
