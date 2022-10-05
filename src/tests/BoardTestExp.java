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
		// top left corner
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1,  0)));
		Assert.assertTrue(testList.contains(board.getCell(0,  1)));
		Assert.assertEquals(2, testList.size());
		// bottom right corner
		cell = board.getCell(3, 3);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(3,  2)));
		Assert.assertTrue(testList.contains(board.getCell(2,  3)));
		Assert.assertEquals(2, testList.size());
		// right edge
		cell = board.getCell(1, 3);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(0,  3)));
		Assert.assertTrue(testList.contains(board.getCell(1,  2)));
		Assert.assertTrue(testList.contains(board.getCell(2,  3)));
		Assert.assertEquals(3, testList.size());
		// bottom left corner
		cell = board.getCell(3, 0);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2,  0)));
		Assert.assertTrue(testList.contains(board.getCell(3,  1)));
		Assert.assertEquals(2, testList.size());
	}
	
	

}
