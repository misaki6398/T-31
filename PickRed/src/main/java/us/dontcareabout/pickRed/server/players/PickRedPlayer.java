package us.dontcareabout.pickRed.server.players;

import java.util.ArrayList;
import java.util.List;

import us.dontcareabout.pickRed.server.models.Card;

public class PickRedPlayer extends Player{
	
	private List<Card> eatedCard = new ArrayList<Card>();
	
	public List<Card> getEatedCard() {
		return eatedCard;
	}
	
	public void addEatedCards(List<Card> cards) {
		eatedCard.addAll(cards);
	}

}
