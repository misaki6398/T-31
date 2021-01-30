package us.dontcareabout.pickRed.server.games;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import us.dontcareabout.pickRed.server.models.Card;
import us.dontcareabout.pickRed.server.players.PickRedPlayer;
import us.dontcareabout.pickRed.server.players.Player;

public class PickRed extends PlayCards {

	private List<PickRedPlayer> players = new ArrayList<PickRedPlayer>();

	private List<Card> cardsInTheSea = new ArrayList<Card>();

	private int playerNum;

	/**
	 * 撿紅點
	 * 
	 * @param playerNum 遊玩者數量 2~4 人
	 */
	public PickRed(int playerNum) throws Exception {

		this.playerNum = playerNum;

		int dealNum = getDealNum(playerNum);

		for (int i = 0; i < playerNum; i++) {
			PickRedPlayer player = new PickRedPlayer();
			players.add(player);
		}

		this.shuffle();

		for (int i = 0; i < dealNum; i++) {
			for (Player player : players) {
				player.addCardOnHand(this.getCards().get(0));
				this.getCards().remove(0);
			}
		}
	}

	/**
	 * 依照遊玩者決定初始手牌數量
	 * 
	 * @param playerNum 遊玩者數量 2~4 人
	 */
	public int getDealNum(int players) throws Exception {
		if (players < 2 && players > 4) {
			throw new Exception("player number error");
		}

		Map<Integer, Integer> dealCard = Map.of(2, 12, 3, 8, 4, 6);

		return dealCard.get(players);
	}

	public void takeFourCardIntoSea() {
		for (int i = 0; i < 4; i++) {
			getCardsInTheSea().add(this.takeCardOnTop());
		}
	}

	/**
	 * 撿紅點，會回傳匹配卡牌，若無匹配卡牌則回傳 null
	 * 
	 * @param card 打的手牌
	 */
	public List<Card> pickCard(Card card) {
		List<Card> pairedCard = null;

		if (card.getDeck() >= 10) {
			pairedCard = this.getCardsInTheSea().stream().filter(c -> c.getDeck() == card.getDeck())
					.collect(Collectors.toList());
		} else {
			pairedCard = this.getCardsInTheSea().stream().filter(c -> c.getDeck() == 10 - card.getDeck())
					.collect(Collectors.toList());
		}

		if (pairedCard.size() == 0) {
			return null;
		}

		return pairedCard;
	}

	public List<PickRedPlayer> getPlayers() {
		return this.players;
	}

	public void putCardInTheSea(Card card) {
		this.getCardsInTheSea().add(card);
	}
	
	public int getPlayerNum() {
		return playerNum;
	}

	public List<Card> getCardsInTheSea() {
		return cardsInTheSea;
	}

}
