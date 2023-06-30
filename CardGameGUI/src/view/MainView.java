package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controllers.FrameAdapter;
import model.PlayingCardImpl;
import model.interfaces.Player;
import model.interfaces.PlayingCard.Suit;
import model.interfaces.PlayingCard.Value;

/**
 * A main view for cards view and summary view
 *
 */
public class MainView extends JPanel {
	
	JFrame frame;
	GridBagLayout gbLayout;
	GridBagConstraints c;
	SummaryView summaryPanel;
	Map<String, CardsView> mainViewHashMap = new HashMap();
	
	public MainView(SummaryView summaryPanel, JFrame frame) {
		this.summaryPanel = summaryPanel;
		this.frame = frame;
		initalizeLayout();
		
		// empty view Only when there is no card at first
		CardsView emptyView = new CardsView();
		addCardViewPanel(emptyView);
		
		CardsView cardsView = new CardsView();
		mainViewHashMap.put("house", cardsView);
	}
	
	private void initalizeLayout() {
		gbLayout = new GridBagLayout();
		c = new GridBagConstraints();

		c.weighty = 1;
		c.weightx = 0.32;
		
		c.gridheight = 1;
		c.gridwidth = 1;
		

		c.gridx = 1;
		c.gridy = 0;
		
		setLayout(gbLayout);
		gbLayout.setConstraints(summaryPanel, c);
		add(summaryPanel);
	}
	
	private void removeAllListeners() {
		for (ComponentListener l: frame.getComponentListeners()) {
			frame.removeComponentListener(l);
		}
	}
	
	private void resetUI() {
		removeAllListeners();
		removeAll();
		revalidate();
		repaint();
		initalizeLayout();
	}
	
	public void redraw() {
		revalidate();
		repaint();
	}

	public void insertPlayer(Player player) {
		// reset before inserting player because there can only be one player's MainView seen at a time.
		resetUI();
		CardsView cardsView = new CardsView();
		addCardViewPanel(cardsView);
		mainViewHashMap.put(player.getPlayerId(), cardsView);
	}
	
	private void addCardViewPanel(CardsView cardsView) {
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.75;
		c.gridx = 0;
		c.gridy = 0;
		
		// resize card on frame resize
		frame.addComponentListener(new FrameAdapter(cardsView));
		
		gbLayout.setConstraints(cardsView, c);
		add(cardsView);
	}
	
	
	public void switchPlayer(String playerId) {
		resetUI();
		CardsView card = mainViewHashMap.get(playerId);
		addCardViewPanel(card);
	}
	
	public void removePlayer(Player player) {
		resetUI();
		mainViewHashMap.remove(player.getPlayerId());
	}
	
	public CardsView getCurrentMainView(String currentPlayerId) {
		return mainViewHashMap.get(currentPlayerId);
	}
} 
