package us.dontcareabout.pickRed.server;

import us.dontcareabout.pickRed.server.games.PickRed;
import us.dontcareabout.pickRed.server.models.Card;
import us.dontcareabout.pickRed.server.players.Player;

public class Main {
	public static void main(String[] args) throws Exception {
	
		int playerNum = 4;
		
		PickRed pickRed = new PickRed(playerNum);
		
		pickRed.start();

	}
}
