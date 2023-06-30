package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Collection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.interfaces.Player;

/**
 *  Summary View that shows players details and score
 */
public class SummaryView extends JPanel{
	
	private JPanel insidePanel;
	
	public SummaryView() {
		setBorder(BorderFactory.createLineBorder(Color.black));
		initializeLayout();
	}
	
	private void initializeLayout() {
		insidePanel = new JPanel();
		
		insidePanel.setAlignmentX(CENTER_ALIGNMENT);
		
		BoxLayout l = new BoxLayout(insidePanel, BoxLayout.Y_AXIS);
		insidePanel.setLayout(l);
		
		add(insidePanel);
		
		// Summary header
		JLabel summaryText = new JLabel("Players Summary");
		Font f = new Font("Helvetica", Font.BOLD, 24);
		summaryText.setFont(f);
		summaryText.setForeground(Color.red);
		summaryText.setAlignmentX(CENTER_ALIGNMENT);
		
		insidePanel.add(summaryText);
		insidePanel.add(Box.createRigidArea(new Dimension(0, 25)));
	}
	
	public void insertPlayers(Collection<Player> players) {
		reInitialize();

		for (Player player: players) {
			JPanel sPanel = new JPanel();
			sPanel.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			
			JLabel name = new JLabel("Player: " + player.getPlayerName());
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 2;
			sPanel.add(name, c);

			JLabel balance = new JLabel("Balance: " + String.valueOf(player.getPoints()));
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 1;
			c.weightx = 0.5;
			sPanel.add(balance, c);
			
			JLabel bet = new JLabel("Bet: " + String.valueOf(player.getBet()));
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 1;
			c.gridy = 1;
			c.weightx = 0.5;
			c.gridwidth = 1;
			sPanel.add(bet, c);
			
			JLabel result = new JLabel("Last result: " + String.valueOf(player.getResult()));
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 2;
			c.weightx = 0.0;
			c.gridwidth = 2;
			sPanel.add(result, c);
	
			insidePanel.add(sPanel);
			insidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
		}
	}
	
	private void reInitialize() {
		removeAll();
		revalidate();
		repaint();
		
		initializeLayout();
	}

}
