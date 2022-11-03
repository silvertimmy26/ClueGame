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
	
	public Solution createSuggestion(Room currentRoom) {
		Solution temp=new Solution();
		return temp;
	}
	
	public BoardCell selectTarget(Set<BoardCell> targets) {
		BoardCell temp=new BoardCell(1,1,'c',DoorDirection.DOWN);
		return temp;
	}

}
