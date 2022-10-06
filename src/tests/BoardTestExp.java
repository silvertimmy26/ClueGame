package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

class BoardTestExp {

	TestBoard board;
	
	@BeforeEach
	public void setUp() {
		board = new TestBoard();
	}
	
	@Test
	public void testAdjacency() {
		// top left corner cell adjacency
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1,  0)));
		Assert.assertTrue(testList.contains(board.getCell(0,  1)));
		Assert.assertEquals(2, testList.size());
		// bottom right corner cell adjacency
		cell = board.getCell(3, 3);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(3,  2)));
		Assert.assertTrue(testList.contains(board.getCell(2,  3)));
		Assert.assertEquals(2, testList.size());
		// right edge cell adjacency
		cell = board.getCell(1, 3);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(0,  3)));
		Assert.assertTrue(testList.contains(board.getCell(1,  2)));
		Assert.assertTrue(testList.contains(board.getCell(2,  3)));
		Assert.assertEquals(3, testList.size());
		// bottom left corner cell adjacency
		cell = board.getCell(3, 0);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2,  0)));
		Assert.assertTrue(testList.contains(board.getCell(3,  1)));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	public void testTargetsNormal() {
		// From (0,0), roll a 3, make sure 6 cells are available
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell,  3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6,  targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	@Test
	public void testTargetsMixed() {
		board.getCell(0,  2).setOccupiedBoolean(true);		// Other player occupies (0, 2)
		board.getCell(1, 2).setRoom(true);					// (1,2) is a room entrance	
		TestBoardCell cell = board.getCell(0,  3);			// Player is at (0, 3)
				board.calcTargets(cell,  3);				// Player rolls a 3, check that 3 spots are available to move to
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
	}

	@Test
	public void testTargetsNormal2() {
		// From (0,0), roll a 2, make sure 3 cells are available
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell,  2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3,  targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
	}
	
	@Test
	public void testTargetsNormal3() {
		// From (0,3), roll a 2, make sure 3 cells are available
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell,  2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3,  targets.size());
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
	}
	
	@Test
	public void testTargetsNormal4() {
		// From (2,1), roll a 2, make sure 6 cells are available
		TestBoardCell cell = board.getCell(2, 1);
		board.calcTargets(cell,  2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6,  targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(3, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
	}
	
}
