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
	private boolean isRoom=false;
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
		if(doorDirection != DoorDirection.NONE) {
			return true;
		}
		else {
			return false;
		}
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public boolean isRoomCenter() {
		return roomCenter;
	}

	public boolean isLabel() {
		return roomLabel;
	}

	public char getSecretPassage() {
		return secretPassage;
	}

	public void setRoomLabel(boolean roomLabel) {
		this.roomLabel = roomLabel;
	}

	public void setRoomCenter(boolean roomCenter) {
		this.roomCenter = roomCenter;
	}

	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}

	public void setIsRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}

	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}
	
	
	
	
}
