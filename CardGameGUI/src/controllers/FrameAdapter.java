package controllers;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import view.CardsView;

/**
 * Component Adapter for frame resize so we can scale cards
 *
 */
public class FrameAdapter extends ComponentAdapter {
	
	CardsView cardsView;
	
	public FrameAdapter(CardsView cardsView) {
		this.cardsView = cardsView;
	}
	
	@Override
	public void componentResized(ComponentEvent componentEvent) {
		cardsView.redrawAll();
	}
}
