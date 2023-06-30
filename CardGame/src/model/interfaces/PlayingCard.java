package model.interfaces;

/**
 * <pre>Assignment interface representing a PlayingCard
 * (setting values is handled by the implementing class constructor(s))
 * 
 * </pre>
 */
public interface PlayingCard
{
   public enum Suit
   {
      HEARTS, SPADES, CLUBS, DIAMONDS;

      @Override
      public String toString() {
         switch (this) {
            case CLUBS: return "Clubs";
            case HEARTS: return "Hearts";
            case SPADES: return "Spades";
            case DIAMONDS: return "Diamonds";
            default: return null;
         }
      }
   }

   public enum Value
   {
      EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;

      @Override
      public String toString() {
         switch (this) {
            case EIGHT: return "Eight";
            case NINE: return "Nine";
            case TEN: return "Ten";
            case JACK: return "Jack";
            case QUEEN: return "Queen";
            case KING: return "King";
            case ACE: return "Ace";
            default: return null;
         }
      }
   }

   /**
    * 28 cards as specified above
    */
   public static final int DECK_SIZE = Suit.values().length * Value.values().length;

   /**
    * @return the suit of this card based on {@link Suit}
    */
   public Suit getSuit();

   /**
    * @return the face value of this card based on {@link Value}
    */
   public Value getValue();

   /**
    * @return the score value of this card (Ace=11, J, Q, K=10, All others int of face value)
    */
   public int getScore();

   /**
   * <pre>@return a human readable String that lists the values of this PlayingCard
   *         instance (see OutputTrace.pdf)
   *         
   *         e.g. "Suit: Clubs, Value: Five, Score: 5" for Five of Clubs
   *         
   *  NOTE: Case MUST match as above</pre>
   */
   @Override
   public abstract String toString();

   /**
    * @param card - another card to compare with
    * @return true - if the face value and suit is equal
    */
   public abstract boolean equals(PlayingCard card);

   /**
    * <pre><b>NOTE:</b> this is the java.lang.Object @Override
    * 
    * the implementation should cast and call through to the type checked method above</pre>
    * 
    * @param card - another card to compare with
    * @return true - if the face value and suit is equal
    */
   @Override
   public abstract boolean equals(Object card);

   /**
    * @return if equals() is true then generated hashCode should also be equal
    */
   @Override
   public abstract int hashCode();
}