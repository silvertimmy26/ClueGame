package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	
	private int row;
	private int col;
	private Set<TestBoardCell> AdjList = new HashSet<TestBoardCell>();
	
	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}

	public Set<TestBoardCell> getAdjList() {
		return AdjList;
	}

	public void addAdjacency(TestBoardCell cell) {
		return;
	}
	
	public void setRoom(boolean partOfRoom) {
		return;
	}
	
	public boolean isRoom() {
		return false;
	}
	
	public void setOccupiedBoolean(boolean isOccupied) {
		return;
	}
	
	public boolean getOccupied() {
		return false;
	}
	
}
