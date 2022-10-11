package clueGame;

import clueGame.BadConfigFormatException;
import clueGame.DoorDirection;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private int doorNum=0;
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
    	try {
    		loadSetupConfig();
    	}catch(Exception e) {
    		e.getMessage();
    	}
    	
    	try {
    		loadLayoutConfig();
    	}catch(Exception e) {
    		e.getMessage();
    	}
    	
    }
    
    public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
    	
    	File setup=new File("ClueSetup.txt");
    	Scanner myScanner= new Scanner(setup);
    	
    	while(myScanner.hasNext()) {
    		String temp=myScanner.nextLine();
    		if(temp.startsWith("//")) {
    			
    		}
    		else{
    			String[] roomInfo=temp.split(", ");
    			if(roomInfo[0]=="Room") {
    				Room tempRoom= new Room(roomInfo[1]);
    				if(roomInfo[2].length()>1) {
    					throw new BadConfigFormatException();
    				}
    				char tempChar= roomInfo[2].charAt(0);
    				roomMap.put(tempChar, tempRoom);
    			}
    			
    			else if(roomInfo[0]=="Space") {
    				//TODO: Insert something here to take care of space areas
    			}
    			else {
    				throw new BadConfigFormatException();
    			}
    		}
    	}
    	
    	myScanner.close();
    	return;
    }
    
    public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
    	
    	File layout=new File("ClueLayout.csv");
    	Scanner myScanner=new Scanner(layout);
    	
    	int holdingNumForPass=0;
    	String[] cells= {};
    	
    	while(myScanner.hasNext()) {
    		String temp=myScanner.nextLine();
    		String[] tempHold=temp.split(",");
    		
    		if(holdingNumForPass==0) {
    			holdingNumForPass=tempHold.length;
    		}
    		else {
    			if(tempHold.length!=holdingNumForPass) {
    				throw new BadConfigFormatException();
    			}
    		}
    		
    		
    		for(String t:tempHold) {
    			int cellsSize=cells.length;
    			cells[cellsSize]=t;
    		}
    		
    	}
    	
    	numRows=cells.length% holdingNumForPass;
    	numColumns=holdingNumForPass;
    	int curr=0;
    	
    	BoardCell[][] gridTemp= new BoardCell[numRows][numColumns];
    	
    	for(int i=0; i<numColumns; i++) {
    		for(int j=0; j<numRows; j++) {
    			DoorDirection direction= DoorDirection.NONE;
    			
    			String currSpot=cells[curr];
    			BoardCell current= new BoardCell(j,i,currSpot.charAt(0),direction);
    			if(roomMap.containsKey(current.getInitial())){
    				current.setIsRoom(true);
    			}
    			
    			if(currSpot.length()>2) {
    				throw new BadConfigFormatException();
    			}
    			else if(currSpot.length()>1) {
    				switch(currSpot.charAt(1)) {
    				
    					case '*':
    						current.setRoomCenter(true);
    						break;
    					case '#':
    						current.setRoomLabel(true);
    						break;
    					case '<':
    						current.setDoorDirection(DoorDirection.LEFT);
    						doorNum++;
    						break;
    					case '^':
    						current.setDoorDirection(DoorDirection.UP);
    						doorNum++;
    						break;
    					case '>':
    						current.setDoorDirection(DoorDirection.RIGHT);
    						doorNum++;
    						break;
    					case 'v':
    						current.setDoorDirection(DoorDirection.DOWN);
    						doorNum++;
    						break;
    					default:
    						if(roomMap.containsKey(currSpot.charAt(1))) {
    							current.setSecretPassage(currSpot.charAt(1));
    						}
    						else {
    							throw new BadConfigFormatException();
    						}
    				
    				}
    			}
    			curr++;
    			gridTemp[j][i]=current;
    		}
    	}
    	
    	grid=gridTemp;
    	myScanner.close();
    	return;
    }

	public void setConfigFiles(String layout, String setup) {
		layoutConfigFile=layout;
		setupConfigFile=setup;
		return;
	}

	public Room getRoom(char room) {
		return roomMap.get(room);
	}
	
	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}
	
	public int roomMapSize() {
		return roomMap.size();
	}

	public int getDoorNum() {
		return doorNum;
	}
	
}
