package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;

class ComputerAITest {

	private static Board board;
	public static Card sheriffCard, banditCard, snoopCard, lassoCard, knifeCard, bottleCard, jailCard, saloonCard, bankCard;

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
		
	}

	
	@Test
	public void creatingSuggestions() {
		ComputerPlayer bandit= new ComputerPlayer("Bandit The Kid", "Red", 18, 0);
		bandit.addToSeen(banditCard);
	}
	
}
