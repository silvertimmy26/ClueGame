package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {

	
	
	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
	}

	@Override
	public void updateHand(Card card) {
		//Add card to our hand as well as to our seen
		Set<Card> tempHand = this.getHand();
		tempHand.add(card);
		this.setHand(tempHand);
		this.addToSeen(card);
	}
	
	public Solution createSuggestion(Room currentRoom) {
		//Get copies of the deck to use
		Set<Card> tempDeck=new HashSet<Card>();
		tempDeck=this.getTheDeck();
		
		Set<Card> tempDeck2=new HashSet<Card>();
		tempDeck2.addAll(tempDeck);
		//Make a new solution and get version of our seen card
		Solution ourSuggestion=new Solution();
		ArrayList<Card> ourSeen=new ArrayList<Card>();
		ourSeen=this.getSeenCards();
		
		//Go through all cards in the deck to find our room card and to remove the ones that we've seen
		for(Card c:tempDeck) {
			if(c.getCardName().equals(currentRoom.getName())){
				ourSuggestion.setRoom(c);
			}

			for(Card s:ourSeen) {
				if(c.equals(s)) {
					tempDeck2.remove(c);
				}
			}
		}
		
		//Set our person and weapons cards based off of what is left
		for(Card c:tempDeck2) {
			if(c.getType()==CardType.PERSON) {
				ourSuggestion.setPerson(c);
			}
			else if(c.getType()==CardType.WEAPON) {
				ourSuggestion.setWeapon(c);
			}
		}
		
		return ourSuggestion;
	}
	
	public BoardCell selectTarget(Set<BoardCell> targets) {
		ArrayList<BoardCell> tempList=new ArrayList<BoardCell>();
		
		//go through all our board cells and find if it is a room
		for(BoardCell b: targets) {
			if(b.getIsRoom()) {
				boolean notSeen=true;
				//Checks if the room has been seen before
				for(Card c: this.getSeenCards()) {
					if(c.getCardName().equals(b.getRoomName())) {
						notSeen=false;
					}
				}
				//If the room hasn't been seen, go with that
				if(notSeen) {
					return b;
				}
			}
			tempList.add(b);
		}
		//choose a random target from the ones remaining
		Random rand= new Random();
		int randomNum=rand.nextInt(1000);
		randomNum=randomNum % targets.size();
		
		return tempList.get(randomNum);
	}

}
