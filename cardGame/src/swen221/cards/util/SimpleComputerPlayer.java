package swen221.cards.util;

import java.util.ArrayList;

import swen221.cards.core.Card;
import swen221.cards.core.Player;
import swen221.cards.core.Trick;

/**
 * Implements a simple computer player who plays the highest card available when
 * the trick can still be won, otherwise discards the lowest card available. In
 * the special case that the player must win the trick (i.e. this is the last
 * card in the trick), then the player conservatively plays the least card
 * needed to win.
 * 
 * @author David J. Pearce
 * 
 */
public class SimpleComputerPlayer extends AbstractComputerPlayer {

	public SimpleComputerPlayer(Player player) {
		super(player);
	}

	@Override
	public Card getNextCard(Trick trick) {		
		// TODO: you need to implement this!
		
		Player nextPlayer = this.player;
		Card bestC = null;
		Card worstC = null;
		
		//if it is the last player to play something...
		//then the player conservatively plays the least card needed to win.		
		
		if(trick.getCardsPlayed().size() == 3) { //if its the last card to play
			//for(trick.getWinner())
			Card currentBestC = null; //the current best from the trick
			for(Card c : trick.getCardsPlayed()) {
				if(currentBestC == null) {	currentBestC = c;} //if the currentBestC is null then set the first card to be the best one				
				else if(currentBestC.suit() == c.suit()) { //if the suits are equal then it just comes down to the rank
					if(c.rank().ordinal() > currentBestC.rank().ordinal()) { //if the rank is higher then the best one then set this card as the best one
						currentBestC = c; 
					}
				}				
				else if(currentBestC.suit() != trick.getTrumps() && c.suit() == trick.getTrumps()) { //if the current best one is not the best 
					currentBestC = c;
				}				
			}
			
			ArrayList<Card> beatIt = new ArrayList<Card>();
			for(Card c : nextPlayer.getHand()) {				
				if(currentBestC.suit() == c.suit()) { //if the suits are equal then it just comes down to the rank
					if(c.rank().ordinal() > currentBestC.rank().ordinal()) { //if the rank is higher then the best one then set this card as the best one
						beatIt.add(c); //return the first best  
					}
				}				
				else if(currentBestC.suit() != trick.getTrumps() && c.suit() == trick.getTrumps()) { //if the current best one is not the best 
					beatIt.add(c);
				}				
			}
			
			if(!beatIt.isEmpty()) { //if we have cards that beat it				
				for(Card c : beatIt) {
					//System.out.println(c.toString());
					if(worstC == null) { worstC = c;}
					if(worstC.suit() == c.suit()) { //if the suits are equal then it just comes down to the rank
						if(c.rank().ordinal() < worstC.rank().ordinal()) { //if the rank is less then the worse one then set this card as the worse one
							worstC = c; //the worst one is now this  
						}
					}				
					else if(worstC.suit() == trick.getTrumps() && c.suit() != trick.getTrumps()) { //if the worst is a trump and the current is not then 
						worstC = c; //the worst one is now this
					}	
				}
				return worstC;				
			}
			//we want to get the worst one from the cards that beat the trick
			
		}
		 
		if(trick.getLeadPlayer() == nextPlayer.direction) { //if its the first card being played
			if(!nextPlayer.getHand().matches(trick.getTrumps()).isEmpty()) { //if we have trump cards play the best from that 
				for(Card c : nextPlayer.getHand().matches(trick.getTrumps())) { //for each of the cards in your hand with the lead suit
					if(bestC == null) {	bestC = c;} //if the bestC is null then set the first card to be the best one
					else if(c.rank().ordinal() > bestC.rank().ordinal()) { //if the rank is higher then the best one then set this card as the best one
						bestC = c; //then play the highest suit from that
					}
				}
			}
			else {
				for(Card c : nextPlayer.getHand()) { //for each of the cards in your hand
					if(bestC == null) {	bestC = c;} //if the bestC is null then set the first card to be the best one					
					else if(c.rank().ordinal() > bestC.rank().ordinal()) { //if the rank is higher then the best one then set this card as the best one
						bestC = c; //then play the highest suit from that
					}
					else if(c.rank().ordinal() == bestC.rank().ordinal()) { //if the cards have the same rank check the one with the best suit
						if(c.suit().ordinal() > bestC.suit().ordinal()) { //if the suit is rated higher then play that
							bestC = c; 
						}						
					}
				}
			}
			//System.out.println(bestC.toString());
			return bestC; //we just return the best card the computer can play.
		}
		else if(!nextPlayer.getHand().matches(trick.getCardPlayed(trick.getLeadPlayer()).suit()).isEmpty()) { //if we have cards of the lead suit in our hand			
			for(Card c : nextPlayer.getHand().matches(trick.getCardPlayed(trick.getLeadPlayer()).suit())) { //for each of the cards in your hand with the lead suit
				if(bestC == null) {	bestC = c;} //if the bestC is null then set the first card to be the best one
				else if(c.rank().ordinal() > bestC.rank().ordinal()) { //if the rank is higher then the best one then set this card as the best one
					bestC = c; //then play the highest suit from that
				}
				
				if(worstC == null) { worstC = c;} //if the worstC is null then set the first card to be the worst one
				else if(c.rank().ordinal() < worstC.rank().ordinal()) { //if the rank is lower then the worst one then set this card as the worst one
					worstC = c; //then play the highest suit from that
				}
			}
		}
		else { //if we dont...			
			if(!nextPlayer.getHand().matches(trick.getTrumps()).isEmpty()) { //if there is something in the trump suit then play the highest suit from that.
				for(Card c : nextPlayer.getHand().matches(trick.getTrumps())) { //for the cards in your hand with trump suits
					if(bestC == null) {	bestC = c;} //if the bestC is null then set the first card to be the best one
					else if(c.rank().ordinal() > bestC.rank().ordinal()) { //if the rank is higher then the best one then set this card as the best one
						bestC = c; //then play the highest suit from that
					}
					
					if(worstC == null) { worstC = c;} //if the worstC is null then set the first card to be the worst one
					else if(c.rank().ordinal() < worstC.rank().ordinal()) { //if the rank is lower then the worst one then set this card as the worst one
						worstC = c; //then play the highest suit from that
					}
				}
			}
			else {				
				for(Card c : nextPlayer.getHand()) { //for your whole hand					
					if(bestC == null) {	bestC = c;} //if the bestC is null then set the first card to be the best one
					else if(c.rank().ordinal() > bestC.rank().ordinal()) { //if the rank is higher then the best one then set this card as the best one
						bestC = c; //then play the highest suit from that
					}
					
					if(worstC == null) { worstC = c;} //if the worstC is null then set the first card to be the worst one
					else if(c.rank().ordinal() < worstC.rank().ordinal()) { //if the rank is lower then the worst one then set this card as the worst one
						worstC = c; //then play the highest suit from that
					}
				}
			}			
			return worstC;
			//otherwise play the lowest ranking card you have
		}

		//if()
		for(Card c : trick.getCardsPlayed()) {
			if(bestC.suit() == trick.getTrumps()) { //if our best card is the best suit
				if(c.suit() == trick.getTrumps()) { //if the current card we looking at is a trump card also
					if(c.rank().ordinal() > bestC.rank().ordinal()) { //if this card is better than our best then just play the worst card
						return worstC;
					}
				}				
			}
			else { //if our best card is the lead suit and not a trump
				if(c.suit() == trick.getTrumps()) { //if the current card we looking at is a trump card
					return worstC; //then the trump card beats our best card.
				}
				else { //if their card is also a lead suit and not a trump
					if(c.rank().ordinal() > bestC.rank().ordinal()) { //if this card is better than our best then just play the worst card
						return worstC;
					}
				}
			}
		}
		
		return bestC; //if it goes through the check, then we really have a chance of winning this trick			
	}	
}
