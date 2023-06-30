package view;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

/**
 * Status bar view on the bottom of the frame to report current status of the game
 */
public class StatusBarView extends JPanel {
	
	JLabel statusTextLabel;
	
	public StatusBarView(int parentFrameWidth) {
		statusTextLabel = new JLabel("");
		initializeLayout(parentFrameWidth);
	}
	
	private void initializeLayout(int parentFrameWidth) {
		// border
		BevelBorder border = new BevelBorder(BevelBorder.LOWERED);
		setBorder(border);
		
		// suze for the status bar
		Dimension d = new Dimension(parentFrameWidth, 25);
		setPreferredSize(d);
		
		// setting a box layout
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		statusTextLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		
		add(statusTextLabel);
	}
	
	public void changeStatus(String status) {
		statusTextLabel.setText("Status: " + status);
	}
}
