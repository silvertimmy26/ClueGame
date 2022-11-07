package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

class GameSetupTests {

	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
		board.deal();
	}
	
	@Test
	public void testDeck() {
		
		boolean testValue;
		Set<Card> testDeck = board.getDeck();
		Card testCard = new Card("Snoop Horse", CardType.PERSON);
		testValue = false; 
		for (Card card: testDeck) {
			if (card.equals(testCard)) {
				testValue = true;
			}
		}
		assertTrue(testValue);
		
		testCard = new Card("Jail", CardType.ROOM);
		testValue = false; 
		for (Card card: testDeck) {
			if (card.equals(testCard)) {
				testValue = true;
			}
		}
		assertTrue(testValue);
		
		testCard = new Card("Knife", CardType.WEAPON);
		testValue = false; 
		for (Card card: testDeck) {
			if (card.equals(testCard)) {
				testValue = true;
			}
		}
		assertTrue(testValue);
		
		assertEquals(21, testDeck.size());
		
	}

	@Test
	public void testPeople() {
		
		ArrayList<Player> testPlayers = board.getPlayers();
		assertEquals(6, testPlayers.size());
		assertTrue(testPlayers.get(0) instanceof HumanPlayer);
		
		int humanPlayerCount = 0;
		for (Player p: testPlayers) {
			if (p instanceof HumanPlayer) {
				humanPlayerCount++;
			}
		}
		assertEquals(1, humanPlayerCount);
		
		assertEquals(testPlayers.get(0).getRow(), 5);
		assertEquals(testPlayers.get(0).getColumn(), 0);
		assertEquals(testPlayers.get(4).getRow(), 17);
		assertEquals(testPlayers.get(4).getColumn(), 23);
		
		assertEquals(testPlayers.get(0).getName(), "Sheriff Silly");
		assertEquals(testPlayers.get(0).getColor(), "Blue");
		assertEquals(testPlayers.get(2).getName(), "Snoop Horse");
		assertEquals(testPlayers.get(2).getColor(), "Green");
		
		boolean fairDeal = true;
		for (Player p: testPlayers) {
			if (p.getHand().size() != 3) {
				fairDeal = false;
			}
		}
		assertTrue(fairDeal);
	}
	
	@Test
	public void testSolution() {
		Set<Card> testDeck = board.getDeck();
		Solution testSolution = board.getTheAnswer();
		boolean roomExists = false, weaponExists = false, personExists = false;
		for (Card c: testDeck) {
			if (c.equals(testSolution.getPerson())) {
				personExists = true;
			} else if (c.equals(testSolution.getWeapon())) {
				weaponExists = true;
			} else if (c.equals(testSolution.getRoom())) {
				roomExists = true;
			}
		}
		
		assertTrue(roomExists);
		assertTrue(weaponExists);
		assertTrue(personExists);
	}
	
}
