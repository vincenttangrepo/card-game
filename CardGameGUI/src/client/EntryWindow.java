package client;
import java.awt.BorderLayout;
import java.net.NoRouteToHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import model.GameEngineImpl;
import model.PlayingCardImpl;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import model.interfaces.PlayingCard.Suit;
import model.interfaces.PlayingCard.Value;
import view.SingleCardView;
import view.interfaces.GameEngineCallback;
import view.GameEngineCallbackGUI;
import view.GameEngineCallbackImpl;
import view.MenuView;
import view.PlayerControlView;


/**
 * EntryWindow is an entry point that initializes GameEngine along with GUI callback
 *
 */
public class EntryWindow {
	public static void main(String args[]) {
	    GameEngine engine = new GameEngineImpl();
	    GameEngineCallbackGUI cbGUI = new GameEngineCallbackGUI(engine);
	    Logger.getLogger("java.awt").setLevel(Level.OFF);
	    Logger.getLogger("sun.awt").setLevel(Level.OFF);
	    Logger.getLogger("javax.swing").setLevel(Level.OFF);
	    engine.addGameEngineCallback(cbGUI);
	    engine.addGameEngineCallback(new GameEngineCallbackImpl());
	}	
}
