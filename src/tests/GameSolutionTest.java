package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

class GameSolutionTest {

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
	public void accusationCheck() {
		Solution temp=new Solution(jailCard, sheriffCard, knifeCard);
		board.setTheAnswer(temp);
		assertTrue(board.checkAccusation(new Solution(jailCard, sheriffCard, knifeCard)));
		
		assertFalse(board.checkAccusation(new Solution(saloonCard, sheriffCard, knifeCard)));
		assertFalse(board.checkAccusation(new Solution(jailCard, banditCard, knifeCard)));
		assertFalse(board.checkAccusation(new Solution(jailCard, sheriffCard, bottleCard)));
	}
	
	
	@Test
	public void suggestionDisprove() {
		ComputerPlayer bandit= new ComputerPlayer("Bandit The Kid","Red",24,9);
		Solution temp= new Solution(jailCard, sheriffCard, knifeCard);
		Set<Card> banditHand=new HashSet<Card>();
		banditHand.add(jailCard);
		banditHand.add(banditCard);
		bandit.setHand(banditHand);
		
		
		assertTrue(jailCard.equals(bandit.disproveSuggestion(temp)));
		
		banditHand.add(sheriffCard);
		assertTrue(jailCard.equals(bandit.disproveSuggestion(temp)) || sheriffCard.equals(bandit.disproveSuggestion(temp)));
		assertTrue(jailCard.equals(bandit.disproveSuggestion(temp)) || sheriffCard.equals(bandit.disproveSuggestion(temp)));
		
		temp=new Solution(saloonCard, snoopCard, knifeCard);
		assertNull(bandit.disproveSuggestion(temp));
	}
	
	@Test
	public void testSuggestion() {
		ComputerPlayer bandit= new ComputerPlayer("Bandit The Kid", "Red", 18, 0);
		HumanPlayer sheriff= new HumanPlayer("Sheriff Silly", "Blue", 5, 0);
		ComputerPlayer snoop= new ComputerPlayer("Snoop Horse", "Green", 24, 9);
		ComputerPlayer temp1=new ComputerPlayer("temp","Blue", 18,1);
		ComputerPlayer temp2=new ComputerPlayer("temp", "Blue", 18,2);
		ComputerPlayer temp3=new ComputerPlayer("temp","Red",18,3);
		ArrayList <Player> temp= new ArrayList<Player>();
		temp.add(bandit);
		temp.add(sheriff);
		temp.add(snoop);
		temp.add(temp1);
		temp.add(temp2);
		temp.add(temp3);
		Set<Card> banditHand=new HashSet<Card>();
		Set<Card> sheriffHand=new HashSet<Card>();
		sheriffHand.add(banditCard);
		board.setPlayers(temp);
		sheriff.setHand(sheriffHand);
		bandit.setHand(banditHand);
		
		Solution suggestion=new Solution(saloonCard, sheriffCard, knifeCard);
		assertNull(board.handleSuggestion(suggestion, 0));
		
		banditHand.add(sheriffCard);
		bandit.setHand(banditHand);
		assertNull(board.handleSuggestion(suggestion, 0));
		
		sheriffHand.add(knifeCard);
		sheriff.setHand(sheriffHand);
		assertTrue(knifeCard.equals(board.handleSuggestion(suggestion, 0)));
		
		Set<Card> snoopHand=new HashSet<Card>();
		snoopHand.add(saloonCard);
		snoop.setHand(snoopHand);
		assertTrue(knifeCard.equals(board.handleSuggestion(suggestion, 0)));
	}

}
