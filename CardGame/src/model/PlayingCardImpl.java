package model;

import model.interfaces.PlayingCard;

public class PlayingCardImpl implements PlayingCard {
    private Suit suit;
    private Value value;

    public PlayingCardImpl (Suit s, Value v) {
        this.suit = s;
        this.value = v;
    }

    @Override
    public Suit getSuit() {
        return suit;
    }

    @Override
    public Value getValue() {
        return value;
    }

    @Override
    public int getScore() {
        switch (value) {
            case ACE: return 11;
            case JACK:
            case QUEEN:
            case KING:
            case TEN: return 10;
            case NINE: return 9;
            case EIGHT: return 8;
        }
        return 0;
    }

    @Override
    public boolean equals(PlayingCard card) {
        return suit == card.getSuit() && value == card.getValue();
    }

    @Override
    public String toString() {
        return String.format("Suit: %s, Value: %s, Score: %d", this.suit, this.value, getScore());
    }
}
