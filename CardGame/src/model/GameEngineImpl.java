package model;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

import java.util.*;

public class GameEngineImpl implements GameEngine {
    private Collection<Player> players;
    private Deque<PlayingCard> deck;
    private Collection<GameEngineCallback> callbacks;

    public GameEngineImpl() {
        this.players = new ArrayList<>();

        LinkedList<PlayingCard> temp = new LinkedList<>();
        for (PlayingCard.Value v : PlayingCard.Value.values()) {
            for (PlayingCard.Suit s : PlayingCard.Suit.values()) {
                temp.add(new PlayingCardImpl(s, v));
            }
        }
        Collections.shuffle(temp);
        this.deck = temp;

        this.callbacks = new ArrayList<>();
    }

    private boolean sleep(long delay) {
        try {
            Thread.sleep(delay);
            return false;
        } catch (InterruptedException e) {
            return true;
        }
    }

    @Override
    public void dealPlayer(Player player, int delay) throws IllegalArgumentException {
        if (delay < 0 || delay > 1000) {
            throw new IllegalArgumentException("Delay must be between 0 and 1000.");
        }

        int score = 0;
        PlayingCard card;
        while (true) {
            card = this.deck.removeFirst();
            if (score + card.getScore() <= GameEngine.BUST_LEVEL) {
                for (GameEngineCallback cb : callbacks) {
                    cb.nextCard(player, card, this);
                }
                score += card.getScore();
            } else {
                break;
            }

            // True if interrupted
            if (sleep(delay))
                return;
        }

        if (score != GameEngine.BUST_LEVEL) {
            for (GameEngineCallback cb : callbacks) {
                cb.bustCard(player, card, this);
            }
        }
        
        player.setResult(score);
        
        for (GameEngineCallback cb : callbacks) {
            cb.result(player, score, this);
        }
    }

    @Override
    public void dealHouse(int delay) throws IllegalArgumentException {
        if (delay < 0) {
            throw new IllegalArgumentException("Delay must be between 0 and 1000.");
        }

        int score = 0;
        PlayingCard card;
        while (true) {
            card = deck.removeFirst();
            if (score + card.getScore() <= GameEngine.BUST_LEVEL) {
                for (GameEngineCallback cb : callbacks) {
                    cb.nextHouseCard(card, this);
                }
                score += card.getScore();
            } else {
                break;
            }

            // True if interrupted
            if (sleep(delay))
                return;
        }

        if (score != GameEngine.BUST_LEVEL) {
            for (GameEngineCallback cb : callbacks) {
                cb.houseBustCard(card, this);
            }
        }

        for (Player p : players) {
            applyWinLoss(p, score);
        }

        for (GameEngineCallback cb : callbacks) {
            cb.houseResult(score, this);
        }

        for (Player p : players) {
            p.resetBet();
        }
    }

    @Override
    public void applyWinLoss(Player player, int houseResult) {
        if (player.getResult() > houseResult) {
            player.setPoints(player.getPoints() + player.getBet());
        } if (player.getResult() < houseResult) {
            player.setPoints(player.getPoints() - player.getBet());
        }
    }

    @Override
    public void addPlayer(Player player) {
        if (getPlayer(player.getPlayerId()) != null)
            removePlayer(player);

        players.add(player);
    }

    @Override
    public Player getPlayer(String id) {
        for (Player p : players) {
            if (p.getPlayerId().equals(id)){
                return p;
            }
        }
        return null;
    }

    @Override
    public boolean removePlayer(Player player) {
        return players.remove(player);
    }

    @Override
    public boolean placeBet(Player player, int bet) {
        return player.setBet(bet);
    }

    @Override
    public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
        this.callbacks.add(gameEngineCallback);
    }

    @Override
    public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
        return this.callbacks.remove(gameEngineCallback);
    }

    @Override
    public Collection<Player> getAllPlayers() {
        return this.players;
    }

    @Override
    public Deque<PlayingCard> getShuffledHalfDeck() {
        return this.deck;
    }
    
    @Override
    public void reshuffle() {
        LinkedList<PlayingCard> temp = new LinkedList<>();
        for (PlayingCard.Value v : PlayingCard.Value.values()) {
            for (PlayingCard.Suit s : PlayingCard.Suit.values()) {
                temp.add(new PlayingCardImpl(s, v));
            }
        }
        Collections.shuffle(temp);
        this.deck = temp;
    }
}
