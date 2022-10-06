package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	
	private int row;
	private int col;
	private Boolean isRoom=false;
	private Boolean isOccupied=false;
	private Set<TestBoardCell> adjList = new HashSet<TestBoardCell>();
	
	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}

	public Set<TestBoardCell> getAdjList() {
		return adjList;
	}

	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}
	
	public void setRoom(boolean partOfRoom) {
		isRoom=partOfRoom;
	}
	
	public boolean isRoom() {
		return isRoom;
	}
	
	public void setOccupiedBoolean(boolean isOccupied) {
		this.isOccupied=isOccupied;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	
	
}
