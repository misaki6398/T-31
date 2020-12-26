package us.dontcareabout.pickRed.server.games;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import us.dontcareabout.pickRed.server.models.Player;

public class PickRed extends PlayCards {

	private Map<Integer, Integer> dealCard = Map.of(
			2, 12, 
			3, 8, 
			4, 6
	);
	
	public List<Player> players = new ArrayList<Player>();
	
	public PickRed(int playerNum) throws Exception {
		Integer dealNum =  getDealNum(playerNum);
		
		for(int i=0;i<playerNum;i++) {
			Player player = new Player();
			players.add(player);
		}
		
		this.shuffle();
				
		for(int i=0;i<dealNum;i++) {
			for(Player player: players) {
				player.addCard(this.cards.get(0));
				this.cards.remove(0);
			}	
		}	
	}

	public Integer getDealNum(Integer players) throws Exception {
		if (players != 2 && players != 3 && players != 4) {
			throw new Exception("player number error");
		}
		return dealCard.get(players);
	}

	public void getTopFourCards() {
		
	}

}
