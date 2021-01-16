package us.dontcareabout.pickRed.server.players;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import us.dontcareabout.pickRed.server.enums.Suits;
import us.dontcareabout.pickRed.server.models.Card;

public class PickRedPlayer extends Player {

	private List<Card> pickedCards = new ArrayList<Card>();
	
	private int score = 0;	
	
	public int getScore() {
		return this.score;
	}

	public List<Card> getpickedCards() {
		return pickedCards;
	}

	public void addPickedCards(List<Card> cards) {
		pickedCards.addAll(cards);
	}

	public void addPickedCard(Card card) {
		pickedCards.add(card);
	}

	/**
	 * 就是計分~
	 * 
	 * @param playerNum 玩家人數，依照人數修改計算分數規則
	 * @throws Exception 
	 */
	public void computeScore(int playerNum, List<Card> pickedTwoCard) throws Exception {
		if(pickedTwoCard.size() != 2) {
			throw new Exception("Cards Count Error");
		}		
		
		switch (playerNum) {
		case 2:			  
			this.score = this.score + this.twoPlayerRule(pickedTwoCard);
			break;
		case 3:
			this.score = this.score + this.threePlayerRule(pickedTwoCard);
			break;
		case 4:
			this.score = this.score + this.fourPlayerRule(pickedTwoCard);
			break;
		default:
			throw new Exception("Player Number Error");
		}		
	}
	
	private int twoPlayerRule(List<Card> pickedTwoCard) {		
		// 找出紅卡
		List<Card> redCards = this.getRedCards(pickedTwoCard);		
		return this.commonRule(redCards);		
	}
	
	private int threePlayerRule(List<Card> pickedTwoCard) {
		int score = 0;
		
		// 黑桃Ａ、梅花Ａ分別為四十分、三十分。（黑桃Ａ和梅花Ａ必須和紅色９配才有算分，和黑色９配則不計分。）
		List<Card> blackA = pickedTwoCard.stream()
				.filter(c -> (c.getSuit() == Suits.club || c.getSuit() == Suits.spade) && c.getDeck() == 1)
				.collect(Collectors.toList());
		if(blackA.size() == 1) {
			List<Card> red9 = pickedTwoCard.stream()
					.filter(c -> (c.getSuit() == Suits.heart || c.getSuit() == Suits.diamond) && c.getDeck() == 9)
					.collect(Collectors.toList());
			if(red9.size() == 1) {
				if(blackA.get(0).getSuit() == Suits.club) {
					score = score + 30;
				}
				if(blackA.get(0).getSuit() == Suits.spade) {
					score = score + 40;
				}
			}
		}
		
		List<Card> redCards = this.getRedCards(pickedTwoCard);		
		score = score + this.commonRule(redCards);				
		
		return score;		
	}
	
	private int fourPlayerRule(List<Card> pickedTwoCard) {
		int score = 0;
		
		// 黑桃Ａ、梅花Ａ分別為三十分、四十分、
		List<Card> blackA = pickedTwoCard.stream()
				.filter(c -> (c.getSuit() == Suits.club || c.getSuit() == Suits.spade) && c.getDeck() == 1)
				.collect(Collectors.toList());
		if(blackA.size() == 1) {
			if(blackA.get(0).getSuit() ==  Suits.club) {
				score = score + 40;
			}
			if(blackA.get(0).getSuit() ==  Suits.spade) {
				score = score + 30;
			}			
		}
		
		// 雙紅5
		List<Card> double5 = pickedTwoCard.stream()
				.filter(c -> (c.getSuit() == Suits.heart || c.getSuit() == Suits.diamond) && c.getDeck() == 5)
				.collect(Collectors.toList());
		if(double5.size() == 2) {
			return 70;
		}
		
		List<Card> redCards = this.getRedCards(pickedTwoCard);		
		score = score + this.commonRule(redCards);				
		
		return score;		
	}
	
	
	private int commonRule(List<Card> redCards) {
		int score = 0;
		for(Card card : redCards) {
			if(card.getDeck() == 1) {
				score = score + 20;
			} else if (card.getDeck() >= 9) {
				score = score + 10;
			} else {
				score = score + card.getDeck();
			}
		} 
		return score;
	}
	
	private List<Card> getRedCards(List<Card> cards) {
		List<Card> redCards = cards.stream()
				.filter(c -> c.getSuit() == Suits.heart || c.getSuit() == Suits.diamond)
				.collect(Collectors.toList());
		return redCards;
	}

}
