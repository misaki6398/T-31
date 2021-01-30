package us.dontcareabout.pickRed.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import us.dontcareabout.pickRed.server.games.PickRed;
import us.dontcareabout.pickRed.server.models.Card;
import us.dontcareabout.pickRed.server.players.PickRedPlayer;
import us.dontcareabout.pickRed.server.players.Player;

public class Main {
	public static void main(String[] args) throws Exception {

		System.out.println("Please input player number (2, 3 or 4)");

		int playerNum = readInputPlayerNum();

		PickRed pickRed = new PickRed(playerNum);

		try {
			PickRedPlayer player;
			pickRed.takeFourCardIntoSea();

			int nowPlayer = 0;
			int putCardNum;

			while ((pickRed.getCardsInTheSea().size() > 0 || pickRed.getCards().size() > 0) || pickRed.getPlayers()
					.stream().filter(c -> c.getCardOnhand().size() != 0).collect(Collectors.toList()).size() > 0) {

				player = pickRed.getPlayers().get(nowPlayer);

				System.out.println("========= Cards in the sea ==========");

				for (Card card : pickRed.getCardsInTheSea()) {
					System.out.println(card.getSuit() + ":" + card.getDeck());
				}

				System.out.format("========= Player %s score %s =========\n", nowPlayer + 1, player.getScore());

				List<Card> pairedCard = null;

				if (player.getCardOnhand().size() > 0) {
					player.showCardOnhand();

					System.out.println("====== Which card you want put ======");

					putCardNum = readInputCardNum(player.getCardOnhand());

					Card putCard = player.takeOneCard(putCardNum - 1);

					pairedCard = pickRed.pickCard(putCard);

					if (pairedCard == null) {
						pickRed.getCardsInTheSea().add(putCard);
						System.out.println("Put a card " + putCard.getSuit() + ":" + putCard.getDeck());

					} else {
						computeScoreAndDealCard(pickRed, player, pairedCard, putCard);
						System.out.format("******* Player %s score %s ********\n", nowPlayer + 1, player.getScore());
					}
				}

				if (pickRed.getCards().size() > 0) {
					Card topCard = pickRed.takeCardOnTop();

					System.out.println("Take a card " + topCard.getSuit() + ":" + topCard.getDeck());

					pairedCard = pickRed.pickCard(topCard);

					if (pairedCard == null) {
						player.addCardOnHand(topCard);
					} else {
						computeScoreAndDealCard(pickRed, player, pairedCard, topCard);
						System.out.format("******* Player %s score %s *******\n", nowPlayer + 1, player.getScore());
					}

				}

				nowPlayer++;
				if (nowPlayer == pickRed.getPlayerNum()) {
					nowPlayer = 0;
				}

				System.out.println("=====================================");
			}

			System.out.println("========= Game ended =========");

		} catch (Exception e) {
			e.printStackTrace();
		}

		int nowPlayer = 0;
		for (PickRedPlayer player : pickRed.getPlayers()) {
			System.out.format("======= Player %s score %s =======\n", nowPlayer + 1, player.getScore());
			nowPlayer++;
		}

	}

	public static void computeScoreAndDealCard(PickRed pickRed, PickRedPlayer player, List<Card> pairedCard, Card pickCard) throws Exception {
		int CardNo = 0;

		if (pairedCard.size() > 1) {
			CardNo = selectCards(pairedCard);
		}

		List<Card> pickedCard = new ArrayList<Card>();
		pickedCard.add(pickCard);
		pickedCard.add(pairedCard.get(CardNo));
		pickRed.getCardsInTheSea().remove(pairedCard.get(CardNo));
		player.computeScore(pickRed.getPlayerNum(), pickedCard);
		player.addPickedCards(pickedCard);

		String s = "pick cards:";
		for (Card card : pickedCard) {
			s = s + " " + card.getSuit() + ":" + card.getDeck();
		}
		System.out.println(s);
	}

	public static int selectCards(List<Card> pairedCard) {
		System.out.println("Which card you want to pick");
		printCards(pairedCard);
		int inputCardNum = readInputCardNum(pairedCard);
		return inputCardNum - 1;
	}

	public static int readInputCardNum(List<Card> inputCards) {
		int inputCardNum = -1;
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		while (inputCardNum == -1) {
			try {
				inputCardNum = Integer.parseInt(bufferReader.readLine());
			} catch (Exception nfe) {
				System.err.println("Not a Number!");
				continue;
			}
			if (inputCardNum > inputCards.size() || inputCardNum <= 0) {
				inputCardNum = -1;
				System.err.println("Number out of range");
			}
		}
		return inputCardNum;
	}

	public static int readInputPlayerNum() {
		int inputPlayerNum = -1;
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		while (inputPlayerNum == -1) {
			try {
				inputPlayerNum = Integer.parseInt(bufferReader.readLine());
			} catch (Exception nfe) {
				System.err.println("Not a Number!");
				continue;
			}
			if (inputPlayerNum != 2 && inputPlayerNum != 3 & inputPlayerNum != 4) {
				inputPlayerNum = -1;
				System.err.println("No this number rule");
			}
		}
		return inputPlayerNum;
	}

	public static void printCards(List<Card> cards) {
		int cardCount = 1;
		for (Card card : cards) {
			System.out.println(cardCount + ":" + card.getSuit() + ":" + card.getDeck());
			cardCount++;
		}
	}

}
