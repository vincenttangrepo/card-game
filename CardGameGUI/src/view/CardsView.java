package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.PlayingCardImpl;
import model.interfaces.PlayingCard;
import model.interfaces.PlayingCard.Suit;
import model.interfaces.PlayingCard.Value;

/**
 * represents panel of 5 cards with horizontal spacing
 *
 */
public class CardsView extends JPanel {

	private int cardCounter = 0;
	
	private JPanel insidePanel;
	
	public List<PlayingCard> playingCards = new ArrayList<PlayingCard>();
	
	public CardsView() {
		super();
		
		initializeLayout();
	}
	
	public void insertPlayingCard(PlayingCard card) {
		// redraw if 5 cards are already there
		if (playingCards.size() == 5) {
			reInitialize();
			playingCards.clear();
			cardCounter = 0;
			initializeLayout();
		}
		
		playingCards.add(card);
		if (getParent() != null) {
			addViewCard(card);
		}
	}
	
	public void redrawAll() {
		reInitialize();
		cardCounter = 0;
		initializeLayout();
		for (PlayingCard card: playingCards) {
			addViewCard(card);
		}
	}
	
	private void addViewCard(PlayingCard card) {
		int outerPadding = 25;
		int innerGapSum = 4*10;
		int width = (getParent().getWidth() / 5) - outerPadding - innerGapSum;
		SingleCardView cardView = new SingleCardView(card, width);
		
		// if there is already a card, add a horizontal gap as well
		if (cardCounter > 0) {
			Dimension d = new Dimension(10, 0);
			insidePanel.add(Box.createRigidArea(d));
			insidePanel.add(cardView);
		} else {	
			insidePanel.add(cardView);
		}
		
		// increase the counter
		cardCounter++;
	}
	
	private void initializeLayout() {
		GridBagLayout bg = new GridBagLayout();
		setLayout(bg);
		insidePanel = new JPanel();
		BoxLayout horizontalBox = new BoxLayout(insidePanel, BoxLayout.X_AXIS);
		insidePanel.setLayout(horizontalBox);
		insidePanel.add(Box.createHorizontalGlue());
		add(insidePanel);
	}
	
	private void reInitialize() {
		removeAll();
		revalidate();
		repaint();
	}

}
