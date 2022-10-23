package tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

class FileInitTest {

	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}
	
	@Test
	public void testRoomMap() {
		// make sure there are eleven rooms
		assertEquals(11, board.getRoomMapSize());
		// make sure all nine rooms exist on board
		assertEquals('J', board.getCell(0, 0).getInitial());
		assertEquals('H', board.getCell(14, 1).getInitial());
		assertEquals('S', board.getCell(17, 12).getInitial());
		assertEquals('G', board.getCell(22, 18).getInitial());
	}
	
	@Test
	public void testRowColumn() {
		// ensures 25 rows, 24 columns
		assertEquals(25, board.getNumRows());
		assertEquals(24, board.getNumColumns());
	}
	
	@Test
	public void testDoorways() {
		// checks all four directions + none
		assertEquals(DoorDirection.LEFT, board.getCell(2, 7).getDoorDirection());
		assertEquals(DoorDirection.UP, board.getCell(4, 5).getDoorDirection());
		assertEquals(DoorDirection.DOWN, board.getCell(18, 6).getDoorDirection());
		assertEquals(DoorDirection.RIGHT, board.getCell(19, 17).getDoorDirection());
		assertEquals(DoorDirection.NONE, board.getCell(7, 17).getDoorDirection());
		assertEquals(20, board.getDoorNum());
	}

	@Test
	public void testCellLabels() {
		// checks four different cell labels
		assertEquals('W', board.getCell(4, 4).getInitial());
		assertEquals('X', board.getCell(0, 8).getInitial());
		assertEquals('C', board.getCell(2, 22).getInitial());
		assertEquals('B', board.getCell(13, 16).getInitial());
	}
	
	@Test
	public void testRoomLabels() {
		// makes sure room labels are correct
		assertEquals("Jail", board.getRoom('J').getName());
		assertEquals("Bank", board.getRoom('B').getName());
		assertEquals("Saloon", board.getRoom('S').getName());
		assertEquals("Gunsmith", board.getRoom('G').getName());
	}
	
	@Test
	public void testRoomCenterCells() {
		// tests to make sure the center of rooms are correct
		BoardCell cell = board.getCell(4, 12);
		assertTrue(cell.getIsRoomCenter());
		cell = board.getCell(20, 12);
		assertTrue(cell.getIsRoomCenter());
	}
	
}
