package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.interfaces.PlayingCard;
import model.interfaces.PlayingCard.Suit;
import model.interfaces.PlayingCard.Value;

/**
 * This view draws a single card
 *
 */
public class SingleCardView extends JPanel {
	
	private PlayingCard card;
	private int width;
	private int height;
	
	// buffered images for spade, club, diamond, and heart
	private BufferedImage spadeImg = readBuffImg("/spade.png");
	private BufferedImage diamondImg = readBuffImg("/diamond.png");
	private BufferedImage clubImg = readBuffImg("/club.png");
	private BufferedImage heartImg = readBuffImg("/heart.png");
	
	public SingleCardView(PlayingCard playingCard, int width) {
		this.width = width;
		this.height = (int) (this.width * 1.5);
		
		this.card = playingCard;
		
		// setting up dimension of a card
		Dimension d = new Dimension(this.width, this.height);
		setPreferredSize(d);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Draw white rectangle
		g.setColor(Color.WHITE);
		int roundness = width / 4;
		g.fillRoundRect(0, 0, width, height, roundness, roundness);
		
		// color
		Color color = cardColor();
		g.setColor(color);
		
		// image in the center
		int imgWidth = (int) (0.35 * width);
		int imgX = (int) ((0.5 * width) - (imgWidth / 2));
		int imgY = (int) ((0.5 * height) - (imgWidth / 2));
		BufferedImage suit = suitImg();
		g.drawImage(suit, imgX, imgY, imgWidth, imgWidth, null);
		
		// number value of card
		Font font = new Font("Verdana", 1, width / 6);
		String value = cardValue();
		g.setFont(font);
		g.drawString(value, (int) (0.15 * width), (int) (0.17 * height));
		g.drawString(value, (int) (0.74 * width), (int) (0.87 * height));
	}
	
	
	private BufferedImage readBuffImg(String img) {
	    try {
	    	URL resource = this.getClass().getResource(img);
	        return ImageIO.read(resource);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	private Color cardColor() {
		switch(card.getSuit()) {
			case HEARTS:
			case DIAMONDS:
				return Color.RED;
				
			case SPADES:
			case CLUBS:
				return Color.BLACK;
		}
		return null;
	}
	
	private BufferedImage suitImg() {
		switch(card.getSuit()) {
			case CLUBS:
				return clubImg;
			case SPADES:
				return spadeImg;
			case DIAMONDS:
				return diamondImg;
			case HEARTS:
				return heartImg;
		}
		return null;
	}
	
	private String cardValue() {
		switch(card.getValue()) {
			case EIGHT: return "8";
			case NINE: return "9";
			case TEN: return "10";
			case JACK: return "J";
			case QUEEN: return "Q";
			case KING: return "K";
			case ACE: return "A";
		}
		return null;
	}
}
