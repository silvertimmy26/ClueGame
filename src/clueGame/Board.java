package clueGame;

import clueGame.BadConfigFormatException;
import clueGame.DoorDirection;
import experiment.TestBoardCell;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Board extends JPanel {
	
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private String layoutConfigFile = new String();
	private String setupConfigFile = new String();
	private int doorNum=0;
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	private Set<Card> deck = new HashSet<Card>();
	private ArrayList<Card> roomCards = new ArrayList<Card>(); 
	private ArrayList<Card> peopleCards = new ArrayList<Card>(); 
	private ArrayList<Card> weaponCards = new ArrayList<Card>(); 
	private Solution theAnswer=new Solution();
	private ArrayList<Player> players = new ArrayList<Player>();
	private int currentPlayer = 0;
	private boolean currentPlayerDone = false;
	private int diceRoll = 0;
	private int cellWidth;
	private int cellHeight;
	private JFrame frame = new JFrame();
	private GameControlPanel gcp;
	private KnownCardsPanel kcp;
	private String suggestionRoom;
	private String suggestionPerson;
	private String suggestionWeapon;
	private JFrame suggestionFrame;
	private String tempRoomName;
	private Character tempRoomInitial;
	
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
    	
    	//Added a listener for board clicks
    	BoardListener b = new BoardListener();
    	this.addMouseListener(b);
    	
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
    	ArrayList<Point> startingPoints =  new ArrayList<Point>();
    	//Set up deck to be new every time
    	deck = new HashSet<Card>();
    	roomCards=new ArrayList<Card>();
    	weaponCards=new ArrayList<Card>();
    	peopleCards=new ArrayList<Card>();
    	players=new ArrayList<Player>();

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
    					//Make card if it is a room
    					Card tempCard = new Card(gameInfo[1], CardType.ROOM);
    					deck.add(tempCard);
    					roomCards.add(tempCard);
    				}
    				
    			} else if (gameInfo[0].contains("Person")) {
    				//Make cards for people
    				
    				//Add name to array list
    				peopleNames.add(gameInfo[1]);
    				//Create new temp card
    				Card tempCard = new Card(gameInfo[1], CardType.PERSON);
    				//Add color to array list
    				peopleColors.add(gameInfo[2]);
    				//Create a new point for starting location
    				Point tempPoint = new Point();
    				tempPoint.x = Integer.parseInt(gameInfo[3]);
    				tempPoint.y = Integer.parseInt(gameInfo[4]);
    				//Add point to array list
    				startingPoints.add(tempPoint);
    				//Add our temp card to actual deck
    				deck.add(tempCard);
    				peopleCards.add(tempCard);
    			} else if (gameInfo[0].contains("Weapon")) {
    				//Create card for weapons
    				Card tempCard = new Card(gameInfo[1], CardType.WEAPON);
    				deck.add(tempCard);
    				weaponCards.add(tempCard);
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
    	//Create all of our players
    	for (int i = 0; i < peopleNames.size(); i++) {
    		Player p0;
    		if (i == 0) {
    			p0 = new HumanPlayer(peopleNames.get(0), peopleColors.get(0), startingPoints.get(0).x, startingPoints.get(0).y);
    		} else {
    			p0 = new ComputerPlayer(peopleNames.get(i), peopleColors.get(i), startingPoints.get(i).x, startingPoints.get(i).y);
    		}
    		//Add player to player list
    		players.add(p0);
    	}
    	
    	//Add a version of the deck to each player
    	for (Player p:players) {
    		p.setTheDeck(deck);
    	}
    	
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
    						currCell.setRoomName(roomMap.get(currSpot.charAt(0)).getName());
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
		
		//Check for empty targets in case of being boxed in
		if (targets.isEmpty()) {
			currentPlayerDone = true;
		}
		//Set all of the targets to know they're targets
		for (BoardCell b: targets) {
			b.setTarget(true);
		}
		if(getActualPlayer().getIsMoved()) {
			targets.add(startCell);
			startCell.setTarget(true);
		} else {
			targets.remove(startCell);
		}
		getActualPlayer().setMoved(false);
		
	}
	
	public void recursiveTargeting(BoardCell startCell, int pathLength) {
		//Obtain adjacent cells
		Set<BoardCell> temp= new HashSet<BoardCell>();
		temp=startCell.getAdjList();
		
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
		//Create copy of deck for moving cards
		
		theAnswer=new Solution();
		Set<Card> allCards = new HashSet<Card>();
		allCards.addAll(deck);
		
		//Create random numbers for indexes
		Random rand = new Random();
		int roomRand = rand.nextInt(1000);
		roomRand = roomRand % roomCards.size();
		int peopleRand = rand.nextInt(1000);
		peopleRand = peopleRand % peopleCards.size();
		int weaponRand = rand.nextInt(1000);
		weaponRand = weaponRand % weaponCards.size();
		
		//Set up a solution
		theAnswer = new Solution(roomCards.get(roomRand),peopleCards.get(peopleRand), weaponCards.get(weaponRand));
		
		//Remove our solution cars prior to passing cards out
		allCards.remove(roomCards.get(roomRand));
		allCards.remove(peopleCards.get(peopleRand));
		allCards.remove(weaponCards.get(weaponRand));
		
		//Set up variables for while looping to deal out cards
		int currentPerson = 0;
		int cardDealRand;
		ArrayList<Card> allCardsCopy = new ArrayList<Card>(allCards);
		
		//While loop for dealing
		while (allCardsCopy.size() > 0) {
			cardDealRand = rand.nextInt(1000);
			cardDealRand = cardDealRand % allCardsCopy.size();
			players.get(currentPerson).updateHand(allCardsCopy.get(cardDealRand));
			allCardsCopy.remove(cardDealRand);
			
			//Iterate along players
			if (currentPerson == 5) {
				currentPerson = 0;
			} else {
				currentPerson++;
			}
		}
	}
	
	public boolean checkAccusation(Solution accusation) {
		//Check to see if all parts of accusation match our answer
		if(accusation.getRoom().equals(this.getTheAnswer().getRoom())
			&& accusation.getPerson().equals(this.getTheAnswer().getPerson())
			&& accusation.getWeapon().equals(this.getTheAnswer().getWeapon())) {
			if (currentPlayer == 0) {
				JOptionPane.showMessageDialog(frame, "Congratulations! You win, yeehaw!");
				frame.dispose();
			} else {
				JOptionPane.showMessageDialog(frame, "Sorry partner, you lost! Dag nabbit!");
				frame.dispose();
			}
			return true;
		} 
		//If not all parts match, set to false
		JOptionPane.showMessageDialog(frame, "Your accusation was wrong! You lose and are going to jail!");
		frame.dispose();
		return false;
	}
	
	public Card handleSuggestion(Solution suggestion, int playerArrayLocation) {
		//Set up variables for while loop
		int stopIndex=0;
		int playerLookAt=playerArrayLocation+1;
		if(playerLookAt==6) {
			playerLookAt=0;
		}
		
		gcp.setGuess(suggestion.getPerson().getCardName()+", "+suggestion.getRoom().getCardName()+", "+suggestion.getWeapon().getCardName());
		//While loop to go through each player to see if their cards can disprove other people
		while(stopIndex<5) {
			Card temp=new Card();
			temp=players.get(playerLookAt).disproveSuggestion(suggestion);
			//Return if there is a card that can disprove
			if (temp !=null) {
				getActualPlayer().addToSeen(temp);
				if(currentPlayer == 0) {
					//Update the seen cards panel and guess result panels
					gcp.setGuessResult(temp.getCardName());
					kcp.updateAllPanels(getActualPlayer());
				}
				else {
					gcp.setGuessResult("Disproved, Buckaroo");
				}
				return temp;
			}
			//Increment variables
			stopIndex++;
			playerLookAt++;
			if(playerLookAt==6) {
				playerLookAt=0;
			}
		}
		
		//If there was no cards that disproved, update guess results
		if(currentPlayer == 0) {
			gcp.setGuessResult("You might be on to something, partner! Your suggestion wasn't disproved!");
		}
		else {
			gcp.setGuessResult("Not disproven");
		}
	
		//If no card found, return null
		getActualPlayer().setMakeAccusation(true);
		getActualPlayer().setSuggestionForAccusation(suggestion);
		return null;
	}
	
	public void setConfigFiles(String layout, String setup) {
		layoutConfigFile=layout;
		setupConfigFile=setup;
		return;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Obtain the width and heights from the window
		int width = this.getWidth();
		int height = this.getHeight();
		
		//Get our cell sizes
		cellWidth = width / numColumns;
		cellHeight = height / numRows;
		
		//Create an array list to hold all cells that are rooms
		ArrayList<BoardCell> roomLabels = new ArrayList<BoardCell>();
		
		//Go through all board cells to draw them and if they are a room center add them to the array list
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				grid[i][j].draw(g, cellWidth, cellHeight, cellWidth * j, cellHeight * i, roomMap, currentPlayer);
				// if it's a label cell, add to an array list that has roomLabel cells
				if (grid[i][j].getIsLabel()) {
					roomLabels.add(grid[i][j]);
				}
			}
		}
		// draws room names on roomLabel cells
		for (BoardCell c: roomLabels) {
		    Font font = new Font("New Roma", Font.BOLD, 15);
		    g.setFont(font);
			String roomName = roomMap.get(c.getInitial()).getName();
			g.drawString(roomName, (c.getCol()-1) * cellWidth, (c.getRow() + 1) * cellHeight);
		}
		
		//Go through all of our players to draw them
		for (Player p: players) {
			p.draw(g, cellWidth, cellHeight);
		}
		getActualPlayer().draw(g, cellWidth, cellHeight);
	}
	
	//Create the text field for current player
	public JTextField setCurrPlayer() {
		JTextField temp= new JTextField(20);
		temp.setText(getActualPlayer().getName());
		
		//Set color of text field
		Color color;
		try {
		    Field field = Color.class.getField(getActualPlayer().getColor().toLowerCase());
		    color = (Color)field.get(null);
		} catch (Exception e) {
		    color = null;
		}
		temp.setBackground(color);
	    temp.setOpaque(true);
	    return temp;
	}
	
	
	//Create the text field for our current roll
	public JTextField setRoll (int roll) {
		JTextField temp=new JTextField(20);
		temp.setText(Integer.toString(roll));
		return temp;
	}
	
	public boolean nextPlayerFlow() {
		
		//check if current player is done
		if (!currentPlayerDone) {
			return false;
		} 
		
		//Increment player, get dice roll, change whos turn it is
		currentPlayer = (currentPlayer + 1) % players.size(); // update current player
		Random rand = new Random();
		diceRoll = ((rand.nextInt(1000)) % 6) + 1;			   // random dice roll
		Player player = players.get(currentPlayer);
		
		for(int i=0; i<numRows; i++) {
			for(int j=0; j<numColumns; j++) {
				grid[i][j].setTarget(false);
			}
		}
		
		calcTargets(getCell(player.getRow(), player.getColumn()), diceRoll);
		Set<Character> roomTargets = new HashSet<Character>();
		//gcp.setTurn(player, diceRoll);
		JTextField currPlayer = setCurrPlayer();
	    revalidate();
	
		gcp.setCurrentPlayer(currPlayer);
		
		JTextField currRoll = setRoll(diceRoll);
		gcp.setCurrentRoll(currRoll);
		//gcp.setTurn(player, diceRoll);
		revalidate();
		
		return true;
	}
	
	public void handleNextTurn() {
		
		//Ensure it is current player's turn
		if (currentPlayer == 0)  {
			for (BoardCell b: targets) {
				grid[b.getRow()][b.getCol()].setTarget(true);	// make tile blue
			}
			repaint();
			currentPlayerDone = false;
		} else {
			
			grid[getActualPlayer().getRow()][getActualPlayer().getColumn()].setOccupied(false);
			//turn situation for computer players
			Solution s = new Solution();
			s = players.get(currentPlayer).turnHandling(targets, roomMap);
			repaint();
			
			//Check if s is accusation or suggestion and then handle
			if (s != null) {
				
				if (s.getIsAccusation()) {
					checkAccusation(s); // either win or eliminate
				} else {
					
					//Code for moving a player when they are suggested 
					String movePlayer= s.getPerson().getCardName();
					for(Player p: players) {
						if(p.getName().equals(movePlayer)) {
							p.setRow(getActualPlayer().getRow());
							p.setColumn(getActualPlayer().getColumn());
							p.setMoved(true);
						}
					}
					
					Card c = new Card();
					c = handleSuggestion(s, currentPlayer);
					if (c != null) {
						players.get(currentPlayer).addToSeen(c);
					}
					kcp.updateAllPanels(players.get(0));
				}
			} else {
				grid[getActualPlayer().getRow()][getActualPlayer().getColumn()].setOccupied(true);
			}
		}
	}
	
	private class BoardListener implements MouseListener {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			//Check for mouse clicks
			
			//Make sure it is our current players turn
			if (currentPlayer != 0 || currentPlayerDone == true) {
				return;
			} else {
				//Check click location
				grid[getActualPlayer().getRow()][getActualPlayer().getColumn()].setOccupied(false);
				boolean validClick = false;
				for (int i = 0; i < numRows; i++) {
					for (int j = 0; j < numColumns; j++) {
						if (isValidTarget(e.getX(), e.getY(), getCell(i, j).getCol() * cellWidth, getCell(i, j).getRow() * cellHeight)) {
							BoardCell roomCenterCell = roomMap.get(grid[i][j].getInitial()).getCenterCell();
							if (targets.contains(grid[i][j]) || ((roomCenterCell != null) && roomCenterCell.getIsTarget())) {
								//If click is a valid location and everything works, change player location
								validClick = true;
								players.get(currentPlayer).setRow(i);
								players.get(currentPlayer).setColumn(j);
								
								if(((roomCenterCell != null) && roomCenterCell.getIsTarget())) {
									players.get(currentPlayer).setRow(roomCenterCell.getRow());
									players.get(currentPlayer).setColumn(roomCenterCell.getCol());
								}
							}
						}
					}
				}
				
				//If invalid location give error message
				if (!validClick) {
					JOptionPane.showMessageDialog(frame, "Invalid target!");
					return;
				}
			}
			
			//Set all of the board cells back to false
			for (BoardCell b: targets) {
				b.setTarget(false);
			}
			
			repaint();
			
			//If the player is in a room, handle this situation
			BoardCell playerLocation = getCell(players.get(currentPlayer).getRow(), players.get(currentPlayer).getColumn());
			if (playerLocation.getIsRoom()) {
				Solution s = new Solution();
				tempRoomInitial=playerLocation.getInitial();
				SuggestionGUI sg = new SuggestionGUI(playerLocation.getRoomName());
				repaint(); // will be called elsewhere depending on suggested player to move that player
			} else {
			grid[playerLocation.getRow()][playerLocation.getCol()].setOccupied(true);	
			}
			currentPlayerDone=true;
		}
		
		//Check for click position
		public boolean isValidTarget(int mouseX, int mouseY, int boxX, int boxY) {
			Rectangle rect = new Rectangle(boxX, boxY, cellWidth, cellHeight);
			if (rect.contains(new Point(mouseX, mouseY))) {
				return true;
			}
			return false;
		}

		// dont need these functions
		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
	}
	
	private class SuggestionGUI extends JFrame {
		
		//GUI for suggestions
		public SuggestionGUI(String roomName) {
			setSize(400, 250);
			setTitle("Make an Suggestion");
			SuggestionPanel sp = new SuggestionPanel(this, roomName);
			add(sp, BorderLayout.CENTER);
			setVisible(true);
		}
	}
	
	private class SuggestionPanel extends JPanel {
		
		JTextField room;
		private JComboBox<String> person, weapon;
		
		private class ComboListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Check sources and update the information
				if (e.getSource() == person) {
					suggestionPerson = person.getSelectedItem().toString();
				} else if (e.getSource() == weapon) {
					suggestionWeapon = weapon.getSelectedItem().toString();
				}
				
			}
			
		}
		
		public SuggestionPanel(JFrame frame, String roomName) {
			
			//Set up the suggestion panel
			suggestionFrame=frame;
			setLayout(new GridLayout(4, 2));
			
			//Create each label and the combo listeners
			JLabel roomLabel = new JLabel("Room");
			JLabel personLabel = new JLabel("Person");
			JLabel weaponLabel = new JLabel("Weapon");
			ComboListener cl = new ComboListener();
			
			//Set text for room, person, and weapon
			room = new JTextField();
			room.setText(roomName);
			tempRoomName = roomName;
			person = new JComboBox<String>();
			person.addActionListener(cl);
			weapon = new JComboBox<String>();
			weapon.addActionListener(cl);
			for (Card c: getDeck()) {
				if (c.getType() == CardType.PERSON) {
					person.addItem(c.getCardName());
				} else if (c.getType() == CardType.WEAPON) {
					weapon.addItem(c.getCardName());
				}
			}
			
			//Add each of our creations
			add(roomLabel);
			add(room);
			add(personLabel);
			add(person);
			add(weaponLabel);
			add(weapon);
			
			//Make buttons for listening
			SubmitSuggestionListener ssl= new SubmitSuggestionListener();
			JButton submitButton = new JButton("Submit");
			submitButton.addActionListener(ssl);
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(e ->{
				frame.dispose();
			});
			add(submitButton);
			add(cancelButton);
		}
	}
	
	private class SubmitSuggestionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//Create button for submit on the suggestion panel
			Solution s = new Solution();
			
			//Look thrugh our deck to match things up
			for (Card c: getDeck()) {
				if (c.getCardName().equals(suggestionPerson)) {
					s.setPerson(c);
				} else if (c.getCardName().equals(suggestionWeapon)) {
					s.setWeapon(c);
				} else if (c.getCardName().equals(tempRoomName)) {
					s.setRoom(c);
				}
			}
			
			//Check for moving people
			String personToMove = s.getPerson().getCardName();
			Room currRoom=roomMap.get(tempRoomInitial);
			for (Player p: players) {
				if (p.getName().equals(personToMove)) {
					p.setRow(currRoom.getCenterCell().getRow());
					p.setColumn(currRoom.getCenterCell().getCol());
					p.setMoved(true);
				}
			}
			handleSuggestion(s, currentPlayer);
			suggestionFrame.dispose();
		}
		
	}
	
	//Function for more start up requirements
	public void startUp() {
		//Rolls dice
		Random rand=new Random();
		
		diceRoll = (rand.nextInt(1000) % 6) + 1;
		
		//Calculate or initial targets
		BoardCell playerLocation = getCell(players.get(currentPlayer).getRow(), players.get(currentPlayer).getColumn());
		calcTargets(playerLocation, diceRoll);
		
		//Make references to panels
		gcp.setTurn(getActualPlayer(), diceRoll);
		deal();
		kcp.updateAllPanels(getActualPlayer());
		repaint();
		revalidate();
	}
	
	public Player getActualPlayer() {
		return players.get(currentPlayer);
	}
	
	public int getDiceRoll() {
		return diceRoll;
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

	public void setDeck(Set<Card> deck) {
		this.deck = deck;
	}

	public void setTheAnswer(Solution theAnswer) {
		this.theAnswer = theAnswer;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public void setGcp(GameControlPanel gcp) {
		this.gcp = gcp;
	}

	public void setKcp(KnownCardsPanel kcp) {
		this.kcp = kcp;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}
	
}
