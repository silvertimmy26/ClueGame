package experiment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	final static int COLS=4;
	final static int ROWS=4;
	
	public TestBoard() {
		
		//Initialize the board with cells
		grid= new TestBoardCell[ROWS][COLS];
		for(int i=0; i<ROWS; i++) {
			for(int j=0; j<COLS; j++) {
				TestBoardCell temp=new TestBoardCell(i,j);
				grid[i][j]=temp;
			}
		}
		
		//Go through every cell to identify its adjacent cells
		for(int i=0; i<ROWS; i++) {
			for(int j=0; j<COLS; j++) {
				
				TestBoardCell temp=grid[i][j];
				
				int row=temp.getRow();
				int col=temp.getCol();
				
				if((row-1)>=0) {
					temp.addAdjacency(this.getCell(row-1, col));
				}
				if((row+1)<ROWS) {
					temp.addAdjacency(this.getCell(row+1, col));
				}
				if((col-1)>=0) {
					temp.addAdjacency(this.getCell(row, col-1));
				}
				if((col+1)<COLS) {
					temp.addAdjacency(this.getCell(row, col+1));
				}
			}
		}
		
	}
	

	public void calcTargets(TestBoardCell startCell, int pathLength) {
		//Reset targets and visited
		targets=new HashSet<TestBoardCell>();
		visited=new HashSet<TestBoardCell>();
		//Call recursive formula
		calculateTargets(startCell, pathLength);
		//Remove the starting cell
		targets.remove(startCell);
		
	}
	
	public void calculateTargets(TestBoardCell startCell, int pathLength) {
		//Obtain adjacent cells
		Set<TestBoardCell> temp=startCell.getAdjList();
		
		
		for(TestBoardCell t:temp) {
			//If in visited or if occupied, ignore
			if(visited.contains(t)) {
				continue;
			}
			if(t.getOccupied()) {
				continue;
			}
			
			//Add cell to visited
			visited.add(t);
			
			//Go through looking if cell should actually be added, or if we call recursive again
			if(t.isRoom()) {
				targets.add(t);
			}
			
			else if(pathLength==1) {
				targets.add(t);
			}
			
			else {
				calculateTargets(t,pathLength-1);
			}
			
			//Remove cell from visited
			visited.remove(t);
		}
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
	
	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
	}
	
}
