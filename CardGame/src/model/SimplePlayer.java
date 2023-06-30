package model;

import model.interfaces.Player;

public class SimplePlayer implements Player {
    private String name;
    private int points;
    private String playerID;
    private int bet;
    private int result;

    public SimplePlayer(String id, String playerName, int initialPoints) {
        this.playerID = id;
        this.name = playerName;
        this.points = initialPoints;
        this.bet = 0;
        this.result = 0;
    }

    @Override
    public String getPlayerName() {
        return this.name;
    }

    @Override
    public void setPlayerName(String playerName) {
        this.name = playerName;
    }

    @Override
    public int getPoints() {
        return this.points;
    }

    @Override
    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String getPlayerId() {
        return playerID;
    }

    @Override
    public boolean setBet(int bet) {
        if (bet > 0 && this.points >= bet) {
            this.bet = bet;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getBet() {
        return this.bet;
    }

    @Override
    public void resetBet() {
        this.bet = 0;
    }

    @Override
    public int getResult() {
        return this.result;
    }

    @Override
    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public boolean equals(Player player) {
        return compareTo(player) == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        SimplePlayer player = (SimplePlayer) o;
        return this.equals(player);
    }

    @Override
    public int compareTo(Player player) {
        return this.playerID.compareTo(player.getPlayerId());
    }

    @Override
    public String toString() {
        return String.format("Player: id=%s, name=%s, bet=%d, points=%d, RESULT .. %d", this.playerID, this.name, this.bet, this.points, this.result);
    }
}
