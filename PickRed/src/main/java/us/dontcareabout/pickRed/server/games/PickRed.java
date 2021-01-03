package us.dontcareabout.pickRed.server.games;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import us.dontcareabout.pickRed.server.models.Card;
import us.dontcareabout.pickRed.server.players.PickRedPlayer;
import us.dontcareabout.pickRed.server.players.Player;

public class PickRed extends PlayCards {	
	
	private List<PickRedPlayer> players = new ArrayList<PickRedPlayer>();
	
	private List<Card> cardsInTheSea = new ArrayList<Card>();
	
	private int playerNum = 2;
	
	public PickRed(int playerNum) throws Exception {
		
		this.playerNum = playerNum;
		
		int dealNum =  getDealNum(playerNum);
		
		for(int i=0;i<playerNum;i++) {
			PickRedPlayer player = new PickRedPlayer();
			players.add(player);
		}
		
		this.shuffle();
				
		for(int i=0;i<dealNum;i++) {
			for(Player player: players) {
				player.addCardOnHand(this.cards.get(0));
				this.cards.remove(0);
			}	
		}	
	}
	
	public void start() {	
		
		PickRed pickRed;
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			pickRed = new PickRed(this.playerNum);
			PickRedPlayer player;
			int nowPlayer = 0;			
			pickRed.takeFourCardIntoSea();			
			int putCardNum;
			
			while (pickRed.cardsInTheSea.size() > 0 || pickRed.cards.size() > 0) {
				player = pickRed.getPlayers().get(nowPlayer);
				System.out.println("==== Cards in the sea ====");
				pickRed.showCardInTheSea();
				System.out.println("==== Player " + nowPlayer +" ====");
				
				
				List<Card> eatedCard = null;
				
				if(player.getCardOnhand().size() > 0) {
					player.showCardOnhand();					
					System.out.println("==== Which card you want put ====");
					try {
			            putCardNum = Integer.parseInt(bufferReader.readLine());
			        } catch(NumberFormatException nfe) {
			            System.err.println("Not a Number!");
			            continue;
			        }
					
					Card putCard = player.takeOneCard(putCardNum-1);
						
					eatedCard = pickRed.eatCard(putCard);
					
					if(eatedCard == null)
					{
						pickRed.cardsInTheSea.add(putCard);	
						System.out.println("Put a card " + putCard.getSuit() + ":" + putCard.getDeck());
					} else {
						player.addEatedCards(eatedCard);
						for (Card card : eatedCard) {
							System.out.println("Eat cards " + card.getSuit() + ":" + card.getDeck());
						}					
					}
				}				
				
				if(pickRed.cards.size() > 0) {
					Card topCard = pickRed.takeCardOnTop();
					System.out.println("Take a card " + topCard.getSuit() + ":" + topCard.getDeck());
					eatedCard = pickRed.eatCard(topCard);				
					if(eatedCard == null)
					{
						player.addCardOnHand(topCard);		
					} else {
						player.addEatedCards(eatedCard);
						for (Card card : eatedCard) {
							System.out.println("Eat cards " + card.getSuit() + ":" + card.getDeck());
						}	
					}
				}				
				
				nowPlayer++;
				if(nowPlayer == this.playerNum) nowPlayer = 0;					
			}	
			
			System.out.println("==== Game ended ====");			
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public int getDealNum(int players) throws Exception {
		if (players < 2 && players > 4) {
			throw new Exception("player number error");
		}
		
		Map<Integer, Integer> dealCard = Map.of(
				2, 12, 
				3, 8, 
				4, 6
		);
		
		return dealCard.get(players);
	}

	public void takeFourCardIntoSea() {
		for(int i=0;i<4;i++) {
			cardsInTheSea.add(this.takeCardOnTop());
		}		
	}
	
	public List<Card> eatCard (Card takedCard) {
		List<Card> pairedCard = null;
		int CardNo = 0;
		
		if(takedCard.getDeck() >= 10) {
			pairedCard = this.cardsInTheSea.stream()
				.filter(c-> c.getDeck() == takedCard.getDeck())
				.collect(Collectors.toList());
		} else {
			pairedCard = this.cardsInTheSea.stream()
			.filter(c-> c.getDeck() == 10 - takedCard.getDeck())
			.collect(Collectors.toList());
		}
		
		if(pairedCard.size() == 0) {			
			return null;
		}
		
		if(pairedCard.size() > 1) {
			CardNo = this.selectCards(pairedCard);			
		}
		
		cardsInTheSea.remove(pairedCard.get(CardNo));
		List<Card> eatedCard = new ArrayList<Card>();
		eatedCard.add(takedCard);
		eatedCard.add(pairedCard.get(CardNo));
		return eatedCard;
	}
	
	private int selectCards(List<Card> pairedCard) {
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		int cardCount = 1;
		int seletedCard = -1;
		for(Card card : pairedCard) {
			System.out.println("Which card you want to eat");
			System.out.println(cardCount + ":" + card.getSuit() + ":" + card.getDeck());
			cardCount++;
		}		
		
		while(seletedCard == -1) {
			try {
				seletedCard = Integer.parseInt(bufferReader.readLine());
	        } catch(IOException nfe) {
	            System.err.println("Not a Number!");	            
	        }
		}		
        
        return seletedCard-1;        	       
	}
	
	public void showCardInTheSea() {
		for (Card card : this.cardsInTheSea) {
			System.out.println(card.getSuit() + ":" + card.getDeck());			
		}
	}
	
	public List<PickRedPlayer> getPlayers() {
		return this.players;
	}
	
	public List<Card> getCardInTheSea(){
		return this.cardsInTheSea;
	}
	
	public void putCardInTheSea(Card card){
		this.cardsInTheSea.add(card);
	}

}
