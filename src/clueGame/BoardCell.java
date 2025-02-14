package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BoardCell {
	
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection = DoorDirection.NONE;
	private boolean roomLabel;
	private boolean roomCenter;
	private Character secretPassage = ' ';
	private boolean isRoom=false;
	private boolean isOccupied=false;
	private Set<BoardCell> adjList = new HashSet<BoardCell>();
	private String roomName;
	private boolean target = false;
	
	public BoardCell() {
		
	}
	
	public BoardCell(int row, int col, char initial, DoorDirection doorDirection) {
		super();
		this.row = row;
		this.col = col;
		this.initial = initial;
		this.doorDirection = doorDirection;
	}

	public void addAdj(BoardCell adj) {
		adjList.add(adj);
	}

	public void draw(Graphics g, int cellWidth, int cellHeight, int locationX, int locationY, Map<Character, Room> roomMap, int currentPlayer) {
		// method to draw each board cell
		
		Color color;
		
		//If unused cell then just draw it black with no borders
		char cellInitial = this.getInitial();
		if (cellInitial == 'X') {
			color = Color.BLACK;
			g.setColor(color);
			g.fillRect(locationX, locationY, cellWidth, cellHeight);
			
			//If room, set to gray and no borders (unless its a target)
		} else if (this.isRoom || (cellInitial != 'W' && cellInitial != 'X')) {
			BoardCell roomCenterCell = roomMap.get(cellInitial).getCenterCell();
			if (roomCenterCell.getIsTarget() && currentPlayer == 0) {
				color = Color.CYAN;
				//target=false;
			} else {
				color = Color.GRAY;
			}
			g.setColor(color);
			g.fillRect(locationX, locationY, cellWidth, cellHeight);
			
			//every other space make it yellow with a border
		} else {
			if (target && currentPlayer == 0) {
				color = Color.CYAN;
				//target=false;
			} else {
				color = Color.YELLOW;
			}
			g.setColor(color);
			g.fillRect(locationX, locationY, cellWidth, cellHeight);
			g.setColor(Color.black);
			g.drawRect(locationX, locationY, cellWidth, cellHeight);
		}
		// if doorway, draw an arrow showing direction of enter
		if (this.getIsDoorway()) {
			switch(this.getDoorDirection()) {
				case LEFT:
					g.drawString("<", (int)(locationX + (cellWidth * 0.5)), (int)(locationY + (cellHeight * 0.5)));
					break;
				case RIGHT:
					g.drawString(">", (int)(locationX + (cellWidth * 0.5)), (int)(locationY + (cellHeight * 0.5)));
					break;
				case UP:
					g.drawString("^", (int)(locationX + (cellWidth * 0.5)), (int)(locationY + (cellHeight * 0.5)));
					break;
				case DOWN:
					g.drawString("v", (int)(locationX + (cellWidth * 0.5)), (int)(locationY + (cellHeight * 0.5)));
					break;
			}
		}
	}
	
	public char getInitial() {
		return initial;
	}

	public boolean getIsDoorway() {
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

	public boolean getIsRoomCenter() {
		return roomCenter;
	}

	public boolean getIsLabel() {
		return roomLabel;
	}

	public char getSecretPassage() {
		return secretPassage;
	}
	
	public boolean getIsOccupied() {
		return isOccupied;
	}

	public boolean getIsRoom() {
		return isRoom;
	}
	
	public Set<BoardCell> getAdjList() {
		return adjList;
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
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
	
	public void setOccupied(boolean occupied) {
		isOccupied = occupied;
	}

	public void setInitial(char initial) {
		this.initial = initial;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public void setTarget(boolean target) {
		this.target = target;
	}

	public boolean getIsTarget() {
		return target;
	}
	
	
	
}
