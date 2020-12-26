package us.dontcareabout.pickRed.server.games;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import us.dontcareabout.pickRed.server.enums.Suits;
import us.dontcareabout.pickRed.server.models.Card;

public class PlayCards {
	public List<Card> cards = new ArrayList<Card>();
	
	/** 
	 * 新的一組排序好的撲克牌
	 */
	public PlayCards() {		
		for (Suits suit : Suits.values()) {
			for (int deck = 1; deck <= 13; deck++) {
				Card card = new Card(deck, suit);
				cards.add(card);
			}
		}
	}

	
	/** 
	 * 就是洗牌
	 */
	public void shuffle() {		
		Random rand = new Random();
		List<Card> shuffledCards = new ArrayList<Card>();
		int cardSize = cards.size();
	    for (int i = 0; i < cardSize; i++) {
	        int randomIndex = rand.nextInt(cards.size());	        
	        Card card = cards.get(randomIndex);	        
	        cards.remove(randomIndex);	        
	        shuffledCards.add(card);	        	        
	    }	    
	    this.cards = shuffledCards;
	}
	
}
