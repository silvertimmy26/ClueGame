package clueGame;

import clueGame.BadConfigFormatException;
import clueGame.DoorDirection;
import experiment.TestBoardCell;

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
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private String layoutConfigFile;
	private String setupConfigFile;
	private int doorNum=0;
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	private Set<Card> deck = new HashSet<Card>();
	private Solution theAnswer;
	private ArrayList<Player> players = new ArrayList<Player>();
	
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
    		e.getMessage(); // syso
    	}
    	
    	//goes through the layout file
    	try {
    		loadLayoutConfig();
    	}catch(Exception e) {
    		e.getMessage(); // syso
    	}
    	
		for(int i=0; i<numRows; i++) {
			for(int j=0; j<numColumns; j++) {   	
				
				BoardCell current = grid[i][j];
				
				if (current.getIsRoomCenter()) {
					
					if (current.getSecretPassage() != ' ') {
						current.addAdj(roomMap.get(current.getSecretPassage()).getCenterCell());
					}
					continue;
				}
				
				if (i - 1 >= 0) {
					
					// if space above is not a room and not unused
					if (grid[i - 1][j].getIsRoom() == false && roomMap.get(grid[i - 1][j].getInitial()).getName() != "Unused") {
						
						if (grid[i - 1][j].getInitial() == 'W') {
							current.addAdj(grid[i - 1][j]);
						}
						
						if (grid[i][j].getDoorDirection() == DoorDirection.NONE) {
							//current.addAdj(grid[i-1][j]);
						} else {	
							BoardCell roomToLeftCenter = roomMap.get(grid[i][j-1].getInitial()).getCenterCell();
							BoardCell roomToRightCenter = roomMap.get(grid[i][j+1].getInitial()).getCenterCell();
							BoardCell roomAboveCenter = roomMap.get(grid[i-1][j].getInitial()).getCenterCell();
							BoardCell roomBelowCenter = roomMap.get(grid[i+1][j].getInitial()).getCenterCell();
							switch(grid[i][j].getDoorDirection()) {
								case LEFT: 	// if the door points left, add the room to the left's center cell to adjacency
									current.addAdj(roomToLeftCenter);
									roomToLeftCenter.addAdj(current);
									break;
								case RIGHT:	// if the door points right, add the room to the right's center cell to adjacency
									current.addAdj(roomToRightCenter);
									roomToRightCenter.addAdj(current);
									break;
								case UP:	// if the door points left, add the above room's center cell to adjacency
									current.addAdj(roomAboveCenter);
									roomAboveCenter.addAdj(current);
									break;
								case DOWN:	// if the door points left, add the room below's center cell to adjacency
									current.addAdj(roomBelowCenter);
									roomBelowCenter.addAdj(current);
									break;
							}		
						}
					}
				}
				if (i + 1 < numRows) {
					// if space below is not a room and not unused
					if (!(grid[i + 1][j].getIsRoom()) && roomMap.get(grid[i + 1][j].getInitial()).getName() != "Unused") {
						
						if (grid[i + 1][j].getInitial() == 'W') {
							current.addAdj(grid[i + 1][j]);
						}
						
						if (grid[i][j].getDoorDirection() == DoorDirection.NONE) {
							//current.addAdj(grid[i+1][j]);
						} else {
							BoardCell roomToLeftCenter = roomMap.get(grid[i][j-1].getInitial()).getCenterCell();
							BoardCell roomToRightCenter = roomMap.get(grid[i][j+1].getInitial()).getCenterCell();
							BoardCell roomAboveCenter = roomMap.get(grid[i-1][j].getInitial()).getCenterCell();
							BoardCell roomBelowCenter = roomMap.get(grid[i+1][j].getInitial()).getCenterCell();
							switch(grid[i][j].getDoorDirection()) {
								case LEFT:	// if the door points left, add the room to the left's center cell to adjacency
									current.addAdj(roomToLeftCenter);
									roomToLeftCenter.addAdj(current);
									break;
								case RIGHT:	// if the door points right, add the room to the right's center cell to adjacency
									current.addAdj(roomToRightCenter);
									roomToRightCenter.addAdj(current);
									break;
								case UP:	// if the door points left, add the above room's center cell to adjacency
									current.addAdj(roomAboveCenter);
									roomAboveCenter.addAdj(current);
									break;
								case DOWN:	// if the door points left, add the room below's center cell to adjacency
									current.addAdj(roomBelowCenter);
									roomBelowCenter.addAdj(current);
									break;
							}		
						}
					}
				}
				if (j - 1 >= 0) {
					// if space to the left is not a room and not unused
					if (!(grid[i][j - 1].getIsRoom()) && roomMap.get(grid[i][j - 1].getInitial()).getName() != "Unused") {
						
						if (grid[i][j - 1].getInitial() == 'W') {
							current.addAdj(grid[i][j - 1]);
						}
						
						if (grid[i][j].getDoorDirection() == DoorDirection.NONE) {
							//current.addAdj(grid[i][j - 1]);
						} else {	
							BoardCell roomToLeftCenter = roomMap.get(grid[i][j-1].getInitial()).getCenterCell();
							BoardCell roomToRightCenter = roomMap.get(grid[i][j+1].getInitial()).getCenterCell();
							BoardCell roomAboveCenter = roomMap.get(grid[i-1][j].getInitial()).getCenterCell();
							BoardCell roomBelowCenter = roomMap.get(grid[i+1][j].getInitial()).getCenterCell();
							switch(grid[i][j].getDoorDirection()) {
								case LEFT:	// if the door points left, add the room to the left's center cell to adjacency
									current.addAdj(roomToLeftCenter);
									roomToLeftCenter.addAdj(current);
									break;
								case RIGHT:	// if the door points right, add the room to the right's center cell to adjacency
									current.addAdj(roomToRightCenter);
									roomToRightCenter.addAdj(current);
									break;
								case UP:	// if the door points left, add the above room's center cell to adjacency
									current.addAdj(roomAboveCenter);
									roomAboveCenter.addAdj(current);
									break;
								case DOWN:	// if the door points left, add the room below's center cell to adjacency
									current.addAdj(roomBelowCenter);
									roomBelowCenter.addAdj(current);
									break;
							}		
						}
					}
				}
				// if space to the right is not a room and not unused
				if (j + 1 < numColumns) {
					if (!(grid[i][j + 1].getIsRoom()) && roomMap.get(grid[i][j + 1].getInitial()).getName() != "Unused") {
						
						if (grid[i][j + 1].getInitial() == 'W') {
							current.addAdj(grid[i][j + 1]);
						}
						
						if (grid[i][j].getDoorDirection() == DoorDirection.NONE) {
							//current.addAdj(grid[i][j + 1]);
						} else {	
							BoardCell roomToLeftCenter = roomMap.get(grid[i][j-1].getInitial()).getCenterCell();
							BoardCell roomToRightCenter = roomMap.get(grid[i][j+1].getInitial()).getCenterCell();
							BoardCell roomAboveCenter = roomMap.get(grid[i-1][j].getInitial()).getCenterCell();
							BoardCell roomBelowCenter = roomMap.get(grid[i+1][j].getInitial()).getCenterCell();
							switch(grid[i][j].getDoorDirection()) {
								case LEFT:	// if the door points left, add the room to the left's center cell to adjacency
									current.addAdj(roomToLeftCenter);
									roomToLeftCenter.addAdj(current);
									break;
								case RIGHT:	// if the door points right, add the room to the right's center cell to adjacency
									current.addAdj(roomToRightCenter);
									roomToRightCenter.addAdj(current);
									break;
								case UP:	// if the door points left, add the above room's center cell to adjacency
									current.addAdj(roomAboveCenter);
									roomAboveCenter.addAdj(current);
									break;
								case DOWN:	// if the door points left, add the room below's center cell to adjacency
									current.addAdj(roomBelowCenter);
									roomBelowCenter.addAdj(current);
									break;
							}		
						}
					}
				}
			}
		}
    }
    
    public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
    	//Sets up our map that will become the main room map
    	Map<Character, Room> tempMap= new HashMap<Character, Room>();
    	//Establishes our scanner for reading the file in
    	File setup=new File(setupConfigFile);
    	Scanner myScanner= new Scanner(setup);
    	ArrayList<String> peopleNames = new ArrayList<String>(); 
    	ArrayList<String> peopleColors = new ArrayList<String>(); 
    	
    	//Goes through every line in the setup file
    	while(myScanner.hasNext()) {
    		String temp=myScanner.nextLine();
    		//ignore lines that are comments
    		if(temp.contains("//")) {
    			
    		}
    		//Lines that are rooms or walkways/unused are here
    		else{
    			//Split line into components
    			String[] gameInfo=temp.split(", ");
    			//Check to make sure that the files are in the correct form, and if so, create a new room
    			if(gameInfo[0].contains("Room")||gameInfo[0].contains("Space")) {
    				Room tempRoom= new Room(gameInfo[1]);
    				//If there is more than 1 character for the room/space, throw an error
    				if(gameInfo[2].length()>1) {
    					throw new BadConfigFormatException("Error: More than one letter for room symbol");
    				}
    				char tempChar= gameInfo[2].charAt(0);
    				//Add to our temp map
    				tempMap.put(tempChar, tempRoom);
    				
    				if (gameInfo[0].contains("Room")) {
    					Card tempCard = new Card(gameInfo[1], CardType.ROOM);
    					deck.add(tempCard);
    				}
    				
    			} else if (gameInfo[0].contains("Person")) {
    				peopleNames.add(gameInfo[1]);
    				Card tempCard = new Card(gameInfo[1], CardType.PERSON);
    				peopleColors.add(gameInfo[2]);
    				deck.add(tempCard);
    			} else if (gameInfo[0].contains("Weapon")) {
    				Card tempCard = new Card(gameInfo[1], CardType.WEAPON);
    				deck.add(tempCard);
    			}
    			
    			//throw an error in the case of extra lines in file
    			else {
    				throw new BadConfigFormatException("Error: Undefined space type");
    			}
    		}
    	}
    	//Set up our room map and close the scanner
    	roomMap=tempMap;
    	myScanner.close();
    	HumanPlayer p0 = new HumanPlayer(peopleNames.get(0), peopleColors.get(0), 5, 0);
    	ComputerPlayer p1 = new ComputerPlayer(peopleNames.get(1), peopleColors.get(1), 18, 0);
    	ComputerPlayer p2 = new ComputerPlayer(peopleNames.get(2), peopleColors.get(2), 24, 9);
    	ComputerPlayer p3 = new ComputerPlayer(peopleNames.get(3), peopleColors.get(3), 24, 14);
    	ComputerPlayer p4 = new ComputerPlayer(peopleNames.get(4), peopleColors.get(4), 17, 23);
    	ComputerPlayer p5 = new ComputerPlayer(peopleNames.get(5), peopleColors.get(5), 0, 16);
    	players.add(p0);
    	players.add(p1);
    	players.add(p2);
    	players.add(p3);
    	players.add(p4);
    	players.add(p5);
    	return;
    }
    
    public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
    	
    	//Load a new scanner to read in the layout file
    	File layout=new File(layoutConfigFile);
    	Scanner myScanner=new Scanner(layout);
    	
    	//holdingNumFor Pass is all about the number of columns gotten
    	int columnSize=0;
    	//An array list to hold all of the letters in order
    	ArrayList<String> cells=new ArrayList<String>();
    	
    	while(myScanner.hasNextLine()) {
    		String temp=myScanner.nextLine();
    		//split letters apart and throw in an array
    		String[] tempHold=temp.split(",");
    		
    		//If first time through, sets our column number to the number gotten
    		if(columnSize==0) {
    			columnSize=tempHold.length;
    		}
    		//Checks for error of improper column numbers
    		else {
    			if(tempHold.length!=columnSize) {
    				throw new BadConfigFormatException("Error: Column sizes are inconsistent");
    			}
    		}
    		
    		//Add each cell letter/s to array list
    		for(String t:tempHold) {
    			cells.add(t);
    		}
    		
    	}
    	
    	//Get out number of rows and columns from the files
    	numRows=(cells.size() / columnSize);
    	numColumns=(columnSize);
    	
    	//Set up variables arrayListLoc (spot in array list) and tempDoorNum(current number of doors found)
    	int arrayListLoc=0;
    	int tempDoorNum=0;
    	//Sets up a grid that will become the main grid eventually
    	BoardCell[][] gridTemp= new BoardCell[numRows][numColumns];
    	
    	Set<String> secretRoomCells = new HashSet<String>();
    	
    	//goes through each spot of data
    	for(int i=0; i<numRows; i++) {
    		for(int j=0; j<numColumns; j++) {
    			DoorDirection direction= DoorDirection.NONE;
    			
    			//Creates a string of the current array list location
    			String currSpot=cells.get(arrayListLoc);
    			
    			//Makes a board cell with the string and data given
    			BoardCell currCell= new BoardCell(i,j,currSpot.charAt(0),direction);
    			
    			//Checks for specific rooms
    			char currCellInitial = currCell.getInitial();
    			
				//if(roomMap.containsKey(currCellInitial) && roomMap.get(currCellInitial).getCenterCell()!=null){
    			//	currCell.setIsRoom(true);
    			//}
    			
    			//looks to make sure the spot is in the room map
    			if(!roomMap.containsKey(currCellInitial)) {
    				throw new BadConfigFormatException("Error: Cell not supported by room map");
    			}
    			
    			//Makes sure we never have a cell over 2 items long
    			if(currSpot.length()>2) {
    				throw new BadConfigFormatException("Error: Cell is longer than two symbols");
    			}
    			else if(currSpot.length()>1) {
    				currCell.setInitial(currSpot.charAt(0));
    				//Switch statement to check if the data item has more than 1 item, what the extra item is
    				switch(currSpot.charAt(1)) {
    				
    					case '*':
    						currCell.setRoomCenter(true);						
    						roomMap.get(currCellInitial).setCenterCell(currCell);
    						currCell.setIsRoom(true);
    						break;
    					case '#':
    						currCell.setRoomLabel(true);
    						roomMap.get(currCellInitial).setLabelCell(currCell);
    						break;
    					case '<':    						
    						currCell.setDoorDirection(DoorDirection.LEFT);
    						tempDoorNum++;
    						break;
    					case '^':
    						currCell.setDoorDirection(DoorDirection.UP);
    						tempDoorNum++;
    						break;
    					case '>':
    						currCell.setDoorDirection(DoorDirection.RIGHT);
    						tempDoorNum++;
    						break;
    					case 'v':
    						currCell.setDoorDirection(DoorDirection.DOWN);
    						tempDoorNum++;
    						break;
    					default:
    						//Default check for secret passage, and looks to make sure location is apart of the board
    						if(roomMap.containsKey(currSpot.charAt(1))) {
    							currCell.setSecretPassage(currSpot.charAt(1));
    							//System.out.println(currSpot.charAt(1));
    							//roomMap.get(currSpot.charAt(0)).getCenterCell().setSecretPassage(currSpot.charAt(1));
    							//System.out.println("hi");
    							secretRoomCells.add(currSpot);
    						}
    						else {
    							throw new BadConfigFormatException("Error: The secret passage room is incompatible/doesn't exist");
    						}
    						break;
    				
    				}
    			}
    			//Iterate the array list location and add our new cell to the temp grid
    			arrayListLoc++;
    			gridTemp[i][j]=currCell;
    		}
    	}
    	
    	for (String b: secretRoomCells) {
    		BoardCell centerCell = roomMap.get(b.charAt(0)).getCenterCell();
    		BoardCell secretRoomCenter = roomMap.get(b.charAt(1)).getCenterCell();
			centerCell.setSecretPassage(b.charAt(1));
			centerCell.addAdj(secretRoomCenter);
    	}    	
    	
    	//finalize some variables and close out
    	doorNum=tempDoorNum;
    	grid=gridTemp;
    	myScanner.close();
    	return;
    }
  
    public void calcTargets(BoardCell startCell, int pathLength) {
		//Reset targets and visited
		targets=new HashSet<BoardCell>();
		visited=new HashSet<BoardCell>();
		//Call recursive formula
		visited.add(startCell);
		recursiveTargeting(startCell, pathLength);
		//Remove the starting cell
		targets.remove(startCell);
		
	}
	
	public void recursiveTargeting(BoardCell startCell, int pathLength) {
		//Obtain adjacent cells
		Set<BoardCell> temp=startCell.getAdjList();
		
		for(BoardCell t:temp) {
			
			//If in visited or if occupied, ignore
			if(visited.contains(t)) {
				continue;
			}
			// If it's occupied or is not a room, go to the next BoardCell t
			if(t.getIsOccupied() && !(t.getIsRoom())) {
				continue;
			}
			
			//Add cell to visited
			visited.add(t); // move before loop and add start cell
			
			//Go through looking if cell should actually be added, or if we call recursive again
			if(t.getIsRoom()) {	// If it's a room, add t to targets and remove the start cell
				targets.add(t);
				targets.remove(startCell);
			} else if(pathLength==1) {	// else and if the pathLength is 1, only add t to targets
				targets.add(t);
			} else {					// otherwise, do a recursive call to calculateTargets with pathLength minus one
				recursiveTargeting(t,pathLength-1);
			}
			
			//Remove cell from visited
			visited.remove(t); // move down a loop
		}
	}
	
	
	
	public void deal() {
		//TODO
	}
	
	public void setConfigFiles(String layout, String setup) {
		layoutConfigFile=layout;
		setupConfigFile=setup;
		return;
	}

	
	public Set<BoardCell> getAdjList(int row, int column){
		return grid[row][column].getAdjList();
	}
	
	public Set<BoardCell> getTargets(){
		return targets;
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
	
	public int getRoomMapSize() {
		return roomMap.size();
	}

	public int getDoorNum() {
		return doorNum;
	}

	public Set<Card> getDeck() {
		return deck;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Solution getTheAnswer() {
		return theAnswer;
	}
	
}
