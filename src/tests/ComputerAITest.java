package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Solution;

class ComputerAITest {

	private static Board board;
	public static Card revolverCard, snakeCard, steelCard, cedricCard, margaretCard, mamaCard, sheriffCard, banditCard, snoopCard, lassoCard, knifeCard, bottleCard, jailCard, saloonCard, bankCard, officeCard, cigarCard, postCard, stableCard, motelCard, smithCard;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
		
		sheriffCard=new Card("Sheriff Silly",CardType.PERSON);
		banditCard=new Card("Bandit The Kid",CardType.PERSON);
		snoopCard=new Card("Snoop Horse",CardType.PERSON);
		lassoCard=new Card("Lasso", CardType.WEAPON);
		knifeCard=new Card("Knife", CardType.WEAPON);
		bottleCard=new Card("Bottle", CardType.WEAPON);
		jailCard=new Card("Jail", CardType.ROOM);
		saloonCard=new Card("Saloon", CardType.ROOM);
		bankCard=new Card("Bank", CardType.ROOM);
		officeCard=new Card("Sheriff's Office",CardType.ROOM);
		cigarCard=new Card("Cigar Store", CardType.ROOM);
		postCard=new Card("Post Office", CardType.ROOM);
		stableCard=new Card("Horse Stables", CardType.ROOM);
		motelCard=new Card("Motel",CardType.ROOM);
		smithCard=new Card("Gunsmith", CardType.ROOM);
		cedricCard=new Card("Cedric Highnoon", CardType.PERSON);
		margaretCard= new Card("Margaret The Wise",CardType.PERSON);
		mamaCard=new Card("Big Mama",CardType.PERSON);
		revolverCard=new Card("Revolver",CardType.WEAPON);
		snakeCard=new Card("Snake Oil", CardType.WEAPON);
		steelCard=new Card("Steel Chair", CardType.WEAPON);
		
	}

	
	@Test
	public void creatingSuggestions() {
		ComputerPlayer temp= new ComputerPlayer("Sheriff Silly", "Blue", 2,2);
		Solution tempSuggestion=new Solution();
		temp.addToSeen(lassoCard);
		temp.addToSeen(knifeCard);
		temp.addToSeen(bottleCard);
		temp.addToSeen(revolverCard);
		temp.addToSeen(snakeCard);
		temp.addToSeen(sheriffCard);
		temp.addToSeen(banditCard);
		temp.addToSeen(snoopCard);
		temp.addToSeen(cedricCard);
		temp.addToSeen(margaretCard);
		
		tempSuggestion=temp.createSuggestion(board.getRoom(board.getCell(temp.getRow(), temp.getColumn())));
		assertTrue(jailCard.equals(tempSuggestion.getRoom()));
		assertTrue(steelCard.equals(tempSuggestion.getWeapon()));
		assertTrue(mamaCard.equals(tempSuggestion.getPerson()));
		
		
		ComputerPlayer temp2= new ComputerPlayer("Sheriff Silly", "Blue", 2,2);
		Solution tempSuggestion2=new Solution();
		temp.addToSeen(lassoCard);
		temp.addToSeen(knifeCard);
		temp.addToSeen(bottleCard);
		temp.addToSeen(revolverCard);
		
		temp.addToSeen(sheriffCard);
		temp.addToSeen(banditCard);
		temp.addToSeen(snoopCard);
		temp.addToSeen(cedricCard);
		
		
		tempSuggestion2=temp2.createSuggestion(board.getRoom(board.getCell(temp.getRow(), temp.getColumn())));
		assertTrue(jailCard.equals(tempSuggestion.getRoom()));
		assertTrue(steelCard.equals(tempSuggestion.getWeapon()) || snakeCard.equals(tempSuggestion.getWeapon()));
		assertTrue(mamaCard.equals(tempSuggestion.getPerson()) || margaretCard.equals(tempSuggestion.getPerson()));
		
	}
	
	
	@Test
	public void aiTargetTests() {
		ComputerPlayer temp=new ComputerPlayer("Matthew","Blue", 7,19);
		BoardCell targetCell=new BoardCell();
		targetCell=temp.selectTarget(1);
		
		assertTrue(targetCell==board.getCell(7, 18) || targetCell==board.getCell(7, 20) ||targetCell==board.getCell(6, 19)||targetCell==board.getCell(8, 19));
		
		temp.setRow(5);
		temp.setColumn(17);
		targetCell=temp.selectTarget(1);
		
		assertTrue(targetCell==board.getCell(2, 19));
		
		temp.addToSeen(officeCard);
		temp.setRow(4);
		temp.setColumn(14);
		targetCell=temp.selectTarget(1);
		assertTrue(targetCell==board.getCell(4,12)|| targetCell==board.getCell(3, 14)||targetCell==board.getCell(4, 15));
		
	}
	
}
