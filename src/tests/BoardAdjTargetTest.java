package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

public class BoardAdjTargetTest {


	private static Board board;
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout306.csv", "ClueSetup306.txt");
		// Initialize will load config files 
		board.initialize();
	}
	
	
	@Test
	public void testWalkways() {
		//Test normal movement in multiple walkway spots
		Set<BoardCell> tempList=board.getAdjList(12, 7);
		assertEquals(4,tempList.size());
		assertTrue(tempList.contains(board.getCell(11, 7)));
		assertTrue(tempList.contains(board.getCell(12, 8)));
		assertTrue(tempList.contains(board.getCell(13, 7)));
		assertTrue(tempList.contains(board.getCell(12, 6)));
		
		tempList=board.getAdjList(8, 15);
		assertEquals(4,tempList.size());
		assertTrue(tempList.contains(board.getCell(8, 14)));
		assertTrue(tempList.contains(board.getCell(7, 15)));
		assertTrue(tempList.contains(board.getCell(8, 16)));
		assertTrue(tempList.contains(board.getCell(9, 15)));
	}
	
	@Test
	public void testInsideRoom() {
		//Test cell in room to make sure it has no adjacent cells
		Set<BoardCell> tempList=board.getAdjList(8, 1);
		assertEquals(0,tempList.size());
	}
	
	@Test
	public void testEdgeOfBoard() {
		//Test edge of the board movement
		Set<BoardCell> tempList=board.getAdjList(24, 14);
		assertEquals(1,tempList.size());
		assertTrue(tempList.contains(board.getCell(23, 14)));
		
		tempList=board.getAdjList(7, 23);
		assertEquals(2,tempList.size());
		assertTrue(tempList.contains(board.getCell(7, 22)));
		assertTrue(tempList.contains(board.getCell(6, 23)));
		
	}
	
	@Test
	public void testCellNextToRoom() {
		//Test movement next to a room wall
		Set<BoardCell> tempList=board.getAdjList(18, 20);
		assertEquals(3,tempList.size());
		assertTrue(tempList.contains(board.getCell(18, 19)));
		assertTrue(tempList.contains(board.getCell(17, 20)));
		assertTrue(tempList.contains(board.getCell(18, 21)));
		
		tempList=board.getAdjList(11, 2);
		assertEquals(2,tempList.size());
		assertTrue(tempList.contains(board.getCell(11, 1)));
		assertTrue(tempList.contains(board.getCell(11, 3)));
	}
	
	@Test
	public void testDoorways() {
		//Test various adjacent cells for doorways
		Set<BoardCell> tempList=board.getAdjList(4, 5);
		assertEquals(4,tempList.size());
		assertTrue(tempList.contains(board.getCell(4, 4)));
		assertTrue(tempList.contains(board.getCell(4, 6)));
		assertTrue(tempList.contains(board.getCell(5, 5)));
		assertTrue(tempList.contains(board.getCell(2, 2)));
		
		tempList=board.getAdjList(21, 9);
		assertEquals(3,tempList.size());
		assertTrue(tempList.contains(board.getCell(21, 8)));
		assertTrue(tempList.contains(board.getCell(22, 9)));
		assertTrue(tempList.contains(board.getCell(20, 12)));
		
		tempList=board.getAdjList(21, 17);
		assertEquals(2,tempList.size());
		assertTrue(tempList.contains(board.getCell(21, 16)));
		assertTrue(tempList.contains(board.getCell(20, 17)));
	}
	
	@Test
	public void insideRoomAdj() {
		//Test adjacent cells in rooms with and without a secret passage
		Set<BoardCell> tempList=board.getAdjList(13, 3);
		assertEquals(2,tempList.size());
		assertTrue(tempList.contains(board.getCell(12, 5)));
		assertTrue(tempList.contains(board.getCell(13, 5)));
		
		tempList=board.getAdjList(21, 4);
		assertEquals(2,tempList.size());
		assertTrue(tempList.contains(board.getCell(18, 6)));
		assertTrue(tempList.contains(board.getCell(2, 19)));
	}
	
	@Test
	public void walkwayTest() {
		//Test targets in walkway
		board.calcTargets(board.getCell(12, 7), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(12, 8)));
		assertTrue(targets.contains(board.getCell(12, 6)));
		assertTrue(targets.contains(board.getCell(13, 7)));
		assertTrue(targets.contains(board.getCell(11, 7)));
		
		board.calcTargets(board.getCell(17, 2), 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(18, 1)));
		assertTrue(targets.contains(board.getCell(17, 4)));
		assertTrue(targets.contains(board.getCell(18, 3)));
	}
	
	@Test
	public void testEnterRoom() {
		//Test entering a room from different spots
		board.calcTargets(board.getCell(22, 9), 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(20, 12)));
		assertTrue(targets.contains(board.getCell(24, 9)));
		assertTrue(targets.contains(board.getCell(22, 7)));
		assertTrue(targets.contains(board.getCell(23, 8)));
		assertTrue(targets.contains(board.getCell(21, 8)));
		
		board.calcTargets(board.getCell(21, 17), 2);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(22, 19)));
		assertTrue(targets.contains(board.getCell(19, 17)));
		assertTrue(targets.contains(board.getCell(21, 15)));
		assertTrue(targets.contains(board.getCell(20, 16)));
	}
	
	@Test
	public void testLeaveRoomNoSecret() {
		//Test leaving a room with no secret passage
		board.calcTargets(board.getCell(12, 20), 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(9, 16)));
		assertTrue(targets.contains(board.getCell(8, 17)));
		assertTrue(targets.contains(board.getCell(15, 16)));
		assertTrue(targets.contains(board.getCell(16, 17)));
	}
	
	@Test
	public void testLeaveRoomSecret() {
		//Test targets in secret passage room
		board.calcTargets(board.getCell(2, 19), 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(5, 16)));
		assertTrue(targets.contains(board.getCell(6, 17)));
		assertTrue(targets.contains(board.getCell(5, 18)));
		assertTrue(targets.contains(board.getCell(21, 4)));
	}
	
	@Test
	public void testOccupiedSpaces() {
		//Test walkway blocked
		board.getCell(11, 15).setOccupied(true);
		board.calcTargets(board.getCell(11, 14), 1);
		board.getCell(11, 15).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(10, 14)));
		assertTrue(targets.contains(board.getCell(12, 14)));
		assertFalse( targets.contains( board.getCell(11, 15))) ;
		assertFalse( targets.contains( board.getCell(11, 15))) ;
		
		//Test exit from room with no secret passage but has a doorway blocked
		board.getCell(15, 17).setOccupied(true);
		board.calcTargets(board.getCell(12, 20), 2);
		board.getCell(15, 17).setOccupied(false);
		targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(9, 16)));
		assertTrue(targets.contains(board.getCell(8, 17)));
		assertFalse( targets.contains( board.getCell(15, 17))) ;
		assertFalse( targets.contains( board.getCell(15, 17))) ;
		
		//Test exit from room with a secret passage and has a doorway blocked
		board.getCell(5, 17).setOccupied(true);
		board.calcTargets(board.getCell(2, 19), 1);
		board.getCell(5, 17).setOccupied(false);
		targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(21, 4)));
		assertFalse( targets.contains( board.getCell(5, 17))) ;
		assertFalse( targets.contains( board.getCell(5, 17))) ;
	}
	
	
	
}
