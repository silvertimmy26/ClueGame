package clueGame;

import java.util.Set;

public class HumanPlayer extends Player {

	public HumanPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
	}
	
	@Override
	public void updateHand(Card card) {
		Set<Card> tempHand = this.getHand();
		tempHand.add(card);
		this.setHand(tempHand);
	}

}
