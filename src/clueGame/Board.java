package clueGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {
	
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private int doorNum;
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	
    // variable and methods used for singleton pattern
    private static Board theInstance = new Board();
   
    // constructor is private to ensure only one can be created
    private Board() {
           super();
    }
    
    // this method returns the only Board
    public static Board getInstance() {
           return theInstance;
    }
    
    //initialize the board (since we are using singleton pattern)
    public void initialize() {
    }
    
    public void loadSetupConfig() {
    	return;
    }
    
    public void loadLayoutConfig() {
    	return;
    }

	public void setConfigFiles(String layout, String setup) {
		return;
	}

	public Room getRoom(char room) {
		BoardCell tempCenterCell = new BoardCell(0, 0, 'q', DoorDirection.NONE);
		BoardCell tempLabelCell = new BoardCell(0, 0, 'q', DoorDirection.NONE);
		Room tempRoom = new Room("name", tempCenterCell, tempLabelCell);
		return tempRoom;
	}
	
	public Room getRoom(BoardCell cell) {
		return null;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public BoardCell getCell(int row, int col) {
		BoardCell tempBoardCell = new BoardCell(0, 0, 'q', DoorDirection.NONE);
		return tempBoardCell;
	}
	
	public int roomMapSize() {
		return roomMap.size();
	}

	public int getDoorNum() {
		return doorNum;
	}
	
}
