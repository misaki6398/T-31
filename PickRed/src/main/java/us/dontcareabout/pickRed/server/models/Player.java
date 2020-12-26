package us.dontcareabout.pickRed.server.models;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private List<Card> cardOnhand = new ArrayList<Card>();
	
	public void addCard(Card card) {
		cardOnhand.add(card);
	}
	
	public List<Card> getCardOnhand() {
		return this.cardOnhand;
	}
}
