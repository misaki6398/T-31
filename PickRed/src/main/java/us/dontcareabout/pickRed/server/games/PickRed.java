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
			cardsInTheSea.add(this.takeCardOnTop());
		}
	}

	/**
	 * 撿紅點，會回傳匹配卡牌，若無匹配卡牌則回傳 null
	 * 
	 * @param card 打入的手牌
	 */
	public List<Card> pickCard(Card card) {
		List<Card> pairedCard = null;

		if (card.getDeck() >= 10) {
			pairedCard = this.cardsInTheSea.stream().filter(c -> c.getDeck() == card.getDeck())
					.collect(Collectors.toList());
		} else {
			pairedCard = this.cardsInTheSea.stream().filter(c -> c.getDeck() == 10 - card.getDeck())
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

	public List<Card> getCardInTheSea() {
		return this.cardsInTheSea;
	}

	public void putCardInTheSea(Card card) {
		this.cardsInTheSea.add(card);
	}

	/**
	 * 下面都是 demo 用的
	 */
	public void start() {

		try {
			PickRedPlayer player;
			int nowPlayer = 0;
			this.takeFourCardIntoSea();
			int putCardNum;

			while ((this.getCardInTheSea().size() > 0 || this.getCards().size() > 0)
					|| this.getPlayers().stream().filter(c -> c.getCardOnhand().size() != 0).collect(Collectors.toList()).size() > 0) {
				
				player = this.getPlayers().get(nowPlayer);

				System.out.println("==== Cards in the sea ====");
				this.showCardInTheSea();
				System.out.format("==== Player %s score %s ====\n", nowPlayer + 1, player.getScore());

				List<Card> pairedCard = null;

				if (player.getCardOnhand().size() > 0) {
					player.showCardOnhand();
					System.out.println("==== Which card you want put ====");

					putCardNum = this.readInputCardNum();

					Card putCard = player.takeOneCard(putCardNum - 1);

					pairedCard = this.pickCard(putCard);

					if (pairedCard == null) {
						this.cardsInTheSea.add(putCard);
						System.out.println("Put a card " + putCard.getSuit() + ":" + putCard.getDeck());

					} else {
						int CardNo = 0;

						if (pairedCard.size() > 1) {
							CardNo = this.selectCards(pairedCard);							
						}
						
						List<Card> pickedCard = new ArrayList<Card>();
						pickedCard.add(putCard);
						pickedCard.add(pairedCard.get(CardNo));
						this.getCardInTheSea().remove(pairedCard.get(CardNo));
						player.computeScore(this.playerNum, pickedCard);
						player.addPickedCards(pickedCard);

						String s = "pick cards:";
						for (Card card : pickedCard) {
							s = s + " " + card.getSuit() + ":" + card.getDeck();							
						}
						System.out.println(s);
						System.out.format("==== Player %s score %s ====\n", nowPlayer + 1, player.getScore());
					}
				}

				if (this.getCards().size() > 0) {
					Card topCard = this.takeCardOnTop();
					System.out.println("Take a card " + topCard.getSuit() + ":" + topCard.getDeck());
					pairedCard = this.pickCard(topCard);
					if (pairedCard == null) {
						player.addCardOnHand(topCard);
					} else {
						int CardNo = 0;

						if (pairedCard.size() > 1) {
							CardNo = this.selectCards(pairedCard);							
						}
						List<Card> pickedCard = new ArrayList<Card>();
						pickedCard.add(topCard);
						pickedCard.add(pairedCard.get(CardNo));
						this.getCardInTheSea().remove(pairedCard.get(CardNo));
						player.computeScore(this.playerNum, pickedCard);
						player.addPickedCards(pairedCard);

						String s = "pick cards:";
						for (Card card : pickedCard) {
							s = s + " " + card.getSuit() + ":" + card.getDeck();							
						}
						System.out.println(s);
						System.out.format("==== Player %s score %s ====\n", nowPlayer + 1, player.getScore());
					}

				}

				nowPlayer++;
				if (nowPlayer == this.playerNum) {
					nowPlayer = 0;
				}					
				
				System.out.println("=====================================");
			}

			System.out.println("==== Game ended ====");		

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int nowPlayer = 0;
		for(PickRedPlayer player : this.getPlayers()) {
			System.out.format("==== Player %s score %s ====\n", nowPlayer + 1, player.getScore());
			nowPlayer++;
		}
	}

	private int selectCards(List<Card> pairedCard) {
		System.out.println("Which card you want to eat");
		this.printCards(pairedCard);
		return this.readInputCardNum() - 1;
	}

	private void showCardInTheSea() {
		for (Card card : this.getCardInTheSea()) {
			System.out.println(card.getSuit() + ":" + card.getDeck());
		}
	}

	private int readInputCardNum() {
		int inputCardNum = -1;
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		while (inputCardNum == -1) {
			try {
				inputCardNum = Integer.parseInt(bufferReader.readLine());
			} catch (IOException nfe) {
				System.err.println("Not a Number!");
			}
		}
		return inputCardNum;
	}

	private void printCards(List<Card> cards) {
		int cardCount = 1;
		for (Card card : cards) {
			System.out.println(cardCount + ":" + card.getSuit() + ":" + card.getDeck());
			cardCount++;
		}
	}

}
