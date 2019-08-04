package swen221.cards.core;

import java.io.Serializable;
import java.util.Objects;

public class Card implements Comparable<Card>, Serializable { //MUST EXTEND SERIALIZABLE AS WELL
	
	/**
	 * Represents a card suit.
	 * 
	 * @author David J. Pearce
	 *
	 */
	public enum Suit {
		HEARTS,
		CLUBS,
		DIAMONDS,
		SPADES;
	}
	
	/**
	 * Represents the different card "numbers".
	 * 
	 * @author David J. Pearce
	 *
	 */
	public enum Rank {
		TWO,
		THREE,
		FOUR,
		FIVE,
		SIX,
		SEVEN,
		EIGHT,
		NINE,
		TEN,
		JACK,
		QUEEN,
		KING,
		ACE;
	}
	
	// =======================================================
	// Card stuff
	// =======================================================
	
	private Suit suit; // HEARTS, CLUBS, DIAMONDS, SPADES
	private Rank rank; // 2 <= number <= 14 (ACE)
	
	/**
	 * Construct a card in the given suit, with a given number
	 * 
	 * @param suit
	 *            --- between 0 (HEARTS) and 3 (SPADES)
	 * @param number
	 *            --- between 2 and 14 (ACE)
	 */
	public Card(Suit suit, Rank number) {				
		this.suit = suit;
		this.rank = number;
	}

	/**
	 * Get the suit of this card, between 0 (HEARTS) and 3 (SPADES).
	 * 
	 * @return
	 */
	public Suit suit() {
		return suit;
	}

	/**
	 * Get the number of this card, between 2 and 14 (ACE).
	 * 
	 * @return
	 */
	public Rank rank() {
		return rank;
	}	
		
	private static String[] suits = { "Hearts","Clubs","Diamonds","Spades"};
	private static String[] ranks = { "2 of ", "3 of ", "4 of ",
			"5 of ", "6 of ", "7 of ", "8 of ", "9 of ", "10 of ", "Jack of ",
			"Queen of ", "King of ", "Ace of " };
	
	public String toString() {
		return ranks[rank.ordinal()] + suits[suit.ordinal()];		
	}

	
//	public int hashCode() {
//		return this.rank();
//	}
//	
	
	public boolean equals(Card o) {
		if(((this.rank) == (o.rank)) && ((this.suit) == (o.suit))) {
			return true;
		}
		return false;
	}
	
	public int hashCode() {
		return Objects.hash(suit, rank);
	}
	
	@Override
	public int compareTo(Card o) {
		// TODO: you need to implement this!
		
		//Rank.values();
		//return 0;
//		if((this.equals(null)) && (o.equals(null))) {return 0;} //if they both equal null return 0
//		else if(this.equals(null)) {return -1;} //if one equals null return the other
//		else if(o.equals(null)) {return 1;}
		
		if(this.equals(o)) {
			return 0;
		}

		if(this.suit.ordinal() > o.suit.ordinal()) {	return 1; }
		else if(this.suit.ordinal() < o.suit.ordinal()) { return -1;	}
		else { //if their suits are equal
			if(this.rank.ordinal() > o.rank.ordinal()) { return 1; } 
			else if(this.rank.ordinal() < o.rank.ordinal()) { return -1; }
			else { return 0; } //if they are both the same 
		}
	}
}
