package us.dontcareabout.pickRed.server.players;

import java.util.ArrayList;
import java.util.List;

import us.dontcareabout.pickRed.server.models.Card;

public class Player {
	private List<Card> cardOnhand = new ArrayList<Card>();
	
	public void addCardOnHand(Card card) {
		cardOnhand.add(card);
	}
	
	public List<Card> getCardOnhand() {
		return this.cardOnhand;
	}
	
	public void showCardOnhand() {
		int cardNum = 1;
		for (Card card : this.cardOnhand) {			
			System.out.println(cardNum + ":" + card.getSuit() + ":" + card.getDeck());	
			cardNum++;
		}
	}
	
	public Card takeOneCard(int num) {
		if(num < 0 || num > cardOnhand.size()) {
			return null;
		}		
		Card card = this.cardOnhand.get(num);
		this.cardOnhand.remove(num);
		return card;
	}
}
