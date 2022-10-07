package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage;
	// private Boolean isRoom=false;
	// private Boolean isOccupied=false;
	private Set<BoardCell> adjList = new HashSet<BoardCell>();
	
	
	
	public BoardCell(int row, int col, char initial, DoorDirection doorDirection) {
		super();
		this.row = row;
		this.col = col;
		this.initial = initial;
		this.doorDirection = doorDirection;
	}

	public void addAdj(BoardCell adj) {
		return;
	}
	
	public char getInitial() {
		return initial;
	}

	public boolean isDoorway() {
		return false;
	}

	public DoorDirection getDoorDirection() {
		return null;
	}

	public boolean isRoomCenter() {
		return false;
	}

	public boolean isLabel() {
		return false;
	}

	public char getSecretPassage() {
		return 0;
	}
	
	
	
}
