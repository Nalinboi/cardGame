package swen221.cards.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a trick being played. This includes the cards that have been
 * played so far, as well as what the suit of trumps is for this trick.
 * 
 * @author David J. Pearce
 * 
 */
public class Trick implements Serializable{ //MUST EXTEND SERIALIZABLE AS WELL
	private Card[] cards = new Card[4];
	private Player.Direction lead;
	private Card.Suit trumps;
	
	/**
	 * Contruct a new trick with a given lead player and suit of trumps.
	 * 
	 * @param lead
	 *            --- lead player for this trick.
	 * @param trumps
	 *            --- maybe null if no trumps.
	 */
	public Trick(Player.Direction lead, Card.Suit trumps) {
		this.lead = lead;
		this.trumps = trumps;
	}
	
	/**
	 * Determine who the lead player for this trick is.
	 * 
	 * @return
	 */
	public Player.Direction getLeadPlayer() {
		return lead;
	}
	
	/**
	 * Determine which suit are trumps for this trick, or null if there are no
	 * trumps.
	 * 
	 * @return
	 */
	public Card.Suit getTrumps() {
		return trumps;
	}
	
	/**
	 * Get the list of cards played so far in the order they were played.
	 * 
	 * @return
	 */
	public List<Card> getCardsPlayed() {
		ArrayList<Card> cs = new ArrayList<Card>();
		for(int i=0;i!=4;++i) {
			if(cards[i] != null) {
				cs.add(cards[i]);
			} else {
				break;
			}
		}
		return cs;
	}
	
	/**
	 * Get the card played by a given player, or null if that player has yet to
	 * play.
	 * 
	 * @param p --- player
	 * @return
	 */
	public Card getCardPlayed(Player.Direction p) {		
		Player.Direction player = lead;
		for(int i=0;i!=4;++i) {
			if(player.equals(p)) {
				return cards[i];
			}
			player = player.next();
		}
		// deadcode
		return null;
	}
	
	/**
	 * Determine the next player to play in this trick.
	 * 
	 * @return
	 */
	public Player.Direction getNextToPlay() {
		Player.Direction dir = lead;
		for(int i=0;i!=4;++i) {
			if(cards[i] == null) {
				return dir;
			}
			dir = dir.next();			
		}
		return null;
	}
	
	/**
	 * Determine the winning player for this trick. This requires looking to see
	 * which player led the highest card that followed suit; or, was a trump.
	 * 
	 * @return
	 */
	public Player.Direction getWinner() {
		Player.Direction player = lead;
		Player.Direction winningPlayer = null;
		Card winningCard = cards[0];
		for (int i = 0; i != 4; ++i) {
			if (cards[i].suit() == winningCard.suit()
					&& cards[i].compareTo(winningCard) >= 0) {
				winningPlayer = player;
				winningCard = cards[i];
			} else if (trumps != null && cards[i].suit() == trumps
					&& winningCard.suit() != trumps) {
				// in this case, the winning card is a trump
				winningPlayer = player;
				winningCard = cards[i];
			}
			player = player.next();
		}
		return winningPlayer;
	}
	
	/**
	 * Player attempts to play a card. This method checks that the given player
	 * is entitled to play, and that the played card follows suit. If either of
	 * these are not true, it throws an IllegalMove exception.
	 */
	public void play(Player p, Card c) throws IllegalMove {
		// FIXME: we need to checks here for attempts to make illegal moves.

		if(!p.getHand().contains(c)) { //if the players hand does not contain this card
			throw new IllegalMove("We can't play a card thats not in our hand!");
		}
		if(p.getDirection() != getNextToPlay()) { //if the players direction does not equal the direction of the next ot play
			throw new IllegalMove("We can't play out of turn!");
		}

		//we must play a card of the same suit
		//if we do not have a a card of the same suit you may play a trump
		//if we do not have a trump you can play any card

		

//		System.out.println("My guy = " + c.suit());
//		System.out.println("What we should = " + cards[0]);

		//if this is not the first card to be played AND
		if((cards[0] != null) && (c.suit() != getCardPlayed(getLeadPlayer()).suit())) { //if the suit played is not the suit of the card of the leader
			//if the player does not have a card with that suit in their hand
			for(Card thisCard : p.getHand()) {
				if(thisCard.suit() == getCardPlayed(getLeadPlayer()).suit()) {
					throw new IllegalMove("You have a card of the same suit to play!");
				} // the card has the suit it needs to play
			}
		}
		


		
		// Finally, play the card.
		for (int i = 0; i != 4; ++i) {
			if (cards[i] == null) {
				cards[i] = c;
				p.getHand().remove(c);
				break;
			}
		}
	}
}
