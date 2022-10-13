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
    	//goes through the setup file
    	try {
    		loadSetupConfig();
    	}catch(Exception e) {
    		e.getMessage();
    	}
    	
    	//goes through the layout file
    	try {
    		loadLayoutConfig();
    	}catch(Exception e) {
    		e.getMessage();
    	}
    	
    }
    
    public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
    	//Sets up our map that will become the main room map
    	Map<Character, Room> tempMap= new HashMap<Character, Room>();
    	//Establishes our scanner for reading the file in
    	File setup=new File(setupConfigFile);
    	Scanner myScanner= new Scanner(setup);
    	
    	//Goes through every line in the setup file
    	while(myScanner.hasNext()) {
    		String temp=myScanner.nextLine();
    		//ignore lines that are comments
    		if(temp.contains("//")) {
    			
    		}
    		//Lines that are rooms or walkways/unused are here
    		else{
    			//Split line into components
    			String[] roomInfo=temp.split(", ");
    			//Check to make sure that the files are in the correct form, and if so, create a new room
    			if(roomInfo[0].contains("Room")||roomInfo[0].contains("Space")) {
    				Room tempRoom= new Room(roomInfo[1]);
    				//If there is more than 1 character for the room/space, throw an error
    				if(roomInfo[2].length()>1) {
    					throw new BadConfigFormatException();
    				}
    				char tempChar= roomInfo[2].charAt(0);
    				
    				//Add to our temp map
    				tempMap.put(tempChar, tempRoom);
    			}
    			
    			//throw an error in the case of extra lines in file
    			else {
    				throw new BadConfigFormatException();
    			}
    		}
    	}
    	//Set up our room map and close the scanner
    	roomMap=tempMap;
    	myScanner.close();
    	return;
    }
    
    public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
    	
    	//Load a new scanner to read in the layout file
    	File layout=new File(layoutConfigFile);
    	Scanner myScanner=new Scanner(layout);
    	
    	//holdingNumFor Pass is all about the number of columns gotten
    	int holdingNumForPass=0;
    	//An array list to hold all of the letters in order
    	ArrayList<String> cells=new ArrayList<String>();
    	
    	while(myScanner.hasNextLine()) {
    		String temp=myScanner.nextLine();
    		//split letters apart and throw in an array
    		String[] tempHold=temp.split(",");
    		
    		//If first time through, sets our column number to the number gotten
    		if(holdingNumForPass==0) {
    			holdingNumForPass=tempHold.length;
    		}
    		//Checks for error of improper column numbers
    		else {
    			if(tempHold.length!=holdingNumForPass) {
    				throw new BadConfigFormatException();
    			}
    		}
    		
    		//Add each cell letter/s to array list
    		for(String t:tempHold) {
    			cells.add(t);
    		}
    		
    	}
    	
    	//Get out number of rows and columns from the files
    	numRows=(cells.size() / holdingNumForPass);
    	numColumns=(holdingNumForPass);
    	
    	//Set up variables curr (spot in array list) and tempDoorNum(current number of doors found)
    	int curr=0;
    	int tempDoorNum=0;
    	//Sets up a grid that will become the main grid eventually
    	BoardCell[][] gridTemp= new BoardCell[numRows][numColumns];
    	
    	//goes through each spot of data
    	for(int i=0; i<numRows; i++) {
    		for(int j=0; j<numColumns; j++) {
    			DoorDirection direction= DoorDirection.NONE;
    			
    			//Creates a string of the current array list location
    			String currSpot=cells.get(curr);
    			
    			//Makes a board cell with the string and data given
    			BoardCell current= new BoardCell(i,j,currSpot.charAt(0),direction);
    			
    			//Checks for specific rooms
    			if(roomMap.containsKey(current.getInitial())&& roomMap.get(current.getInitial()).getCenterCell()!=null){
    				current.setIsRoom(true);
    			}
    			
    			//looks to make sure the spot is in the room map
    			if(!roomMap.containsKey(current.getInitial())) {
    				throw new BadConfigFormatException();
    			}
    			
    			//Makes sure we never have a cell over 2 items long
    			if(currSpot.length()>2) {
    				throw new BadConfigFormatException();
    			}
    			else if(currSpot.length()>1) {
    				//Switch statement to check if the data item has more than 1 item, what the extra item is
    				switch(currSpot.charAt(1)) {
    				
    					case '*':
    						current.setRoomCenter(true);
    						roomMap.get(current.getInitial()).setCenterCell(current);
    						break;
    					case '#':
    						current.setRoomLabel(true);
    						roomMap.get(current.getInitial()).setLabelCell(current);
    						break;
    					case '<':
    						current.setDoorDirection(DoorDirection.LEFT);
    						tempDoorNum++;
    						break;
    					case '^':
    						current.setDoorDirection(DoorDirection.UP);
    						tempDoorNum++;
    						break;
    					case '>':
    						current.setDoorDirection(DoorDirection.RIGHT);
    						tempDoorNum++;
    						break;
    					case 'v':
    						current.setDoorDirection(DoorDirection.DOWN);
    						tempDoorNum++;
    						break;
    					default:
    						//Default check for secret passage, and looks to make sure location is apart of the board
    						if(roomMap.containsKey(currSpot.charAt(1))) {
    							current.setSecretPassage(currSpot.charAt(1));
    						}
    						else {
    							throw new BadConfigFormatException();
    						}
    						break;
    				
    				}
    			}
    			//Iterate the array list location and add our new cell to the temp grid
    			curr++;
    			gridTemp[i][j]=current;
    		}
    	}
    	
    	//finalize some variables and close out
    	doorNum=tempDoorNum;
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
