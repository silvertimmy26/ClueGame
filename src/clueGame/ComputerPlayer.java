package clueGame;

import java.util.Set;

public class ComputerPlayer extends Player {

	
	
	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
	}

	@Override
	public void updateHand(Card card) {
		Set<Card> tempHand = this.getHand();
		tempHand.add(card);
		this.setHand(tempHand);
	}

}
