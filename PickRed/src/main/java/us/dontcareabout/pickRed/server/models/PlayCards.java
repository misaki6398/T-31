package us.dontcareabout.pickRed.server.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import us.dontcareabout.pickRed.server.enums.Suits;

public class PlayCards {
	public List<PlayCard> cards = new ArrayList<PlayCard>();

	public PlayCards() {
		// 新的排序好的撲克牌 沒有鬼牌
		for (Suits suit : Suits.values()) {
			for (int deck = 1; deck <= 13; deck++) {
				PlayCard card = new PlayCard(deck, suit);
				cards.add(card);
			}
		}
	}

	public void shuffle() {		
		Random rand = new Random();
		List<PlayCard> shuffledCards = new ArrayList<PlayCard>();
	    for (int i = 0; i < cards.size(); i++) {
	        int randomIndex = rand.nextInt(cards.size());
	        PlayCard card = cards.get(randomIndex);	        
	        shuffledCards.add(card);
	    }	    
	    this.cards = shuffledCards;
	}
	
}
