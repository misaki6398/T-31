package us.dontcareabout.pickRed.server;

import us.dontcareabout.pickRed.server.models.PlayCard;
import us.dontcareabout.pickRed.server.models.PlayCards;

public class Main {
	public static void main(String[] args) {
		
		PlayCards playCards = new PlayCards();
		
		for (PlayCard card : playCards.cards) {
			System.out.println(card.getSuit() + ":" + card.getDeck());
		}
		
		System.out.println("=============shuffle==============");
		
		playCards.shuffle();
		
		for (PlayCard card : playCards.cards) {
			System.out.println(card.getSuit() + ":" + card.getDeck());
		}
		
		System.out.println(playCards.cards.size());
	}
}
