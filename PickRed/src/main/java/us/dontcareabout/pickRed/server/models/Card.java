package us.dontcareabout.pickRed.server.models;

import us.dontcareabout.pickRed.server.enums.Suits;

public class Card {

	private int deck;

	private Suits suit;

	public Card(int deck, Suits suit) {
		this.deck = deck;
		this.suit = suit;
	}	

	public int getDeck() {
		return this.deck;
	}
	
	/** 
	 * TODO: 沒做鬼牌
	 */
	public void setDeck(int deck) throws Exception {
		if (deck < 1 || deck > 13) {
			throw new Exception("number out of range");
		}
		this.deck = deck;
	}

	public Suits getSuit() {
		return this.suit;
	}

	public void setSuit(Suits suit) {
		this.suit = suit;
	}

}
