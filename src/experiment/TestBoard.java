package experiment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	
	private ArrayList<TestBoardCell> board;
	private Set<TestBoardCell> targets = new HashSet<TestBoardCell>();
	
	public TestBoard() {
		board = new ArrayList<TestBoardCell>();
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		return;
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
	
	public TestBoardCell getCell(int row, int col) {
		TestBoardCell testCell = new TestBoardCell(row, col);
		return testCell;
	}
	
}
