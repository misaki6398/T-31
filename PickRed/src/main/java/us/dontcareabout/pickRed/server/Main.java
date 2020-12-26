package us.dontcareabout.pickRed.server;

import us.dontcareabout.pickRed.server.games.PickRed;
import us.dontcareabout.pickRed.server.models.Card;
import us.dontcareabout.pickRed.server.models.Player;

public class Main {
	public static void main(String[] args) throws Exception {
		
		PickRed pickRed = new PickRed(3);
		
		int cardCount = 0;
		
		for (Player player : pickRed.players) {
			System.out.println("==== Player ====");
			for (Card card : player.getCardOnhand()) {
				System.out.println(card.getSuit() + ":" + card.getDeck());
				cardCount++;
			}
		}
		
		System.out.println("==== Card on desktop ====");
		for (Card card : pickRed.cards) {
			System.out.println(card.getSuit() + ":" + card.getDeck());
			cardCount++;
		}
		
		System.out.println(cardCount);
		
//		System.out.println("=============shuffle==============");
//		
//		playCards.shuffle();
//		
//		for (PlayCard card : playCards.cards) {
//			System.out.println(card.getSuit() + ":" + card.getDeck());
//		}
//		
//		System.out.println(playCards.cards.size());
	}
}
