package clueGame;

import java.util.Map;
import java.util.Set;

public class HumanPlayer extends Player {

	public HumanPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
	}
	
	@Override
	public void updateHand(Card card) {
		//Update our hand with the card
		Set<Card> tempHand = this.getHand();
		tempHand.add(card);
		this.addToSeen(card);
		this.setHand(tempHand);
	}

	@Override
	public Solution turnHandling(Set<BoardCell> targets, Map<Character, Room> roomMap) {
		return null;
		// do nothing
	}

	public Solution createSuggestion(Room currentRoom) {
		return null;
	}
	
	
	
}
