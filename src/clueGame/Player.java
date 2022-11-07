package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class Player {
	
	private String name;
	private String color;
	private int row;
	private int column;
	private Set<Card> hand = new HashSet<Card>();
	private ArrayList<Card> seenCards= new ArrayList<Card>();
	private Set<Card>theDeck=new HashSet<Card>();
	
	public Player(String name, String color, int row, int column) {
		super();
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
	}

	//Set up update hand to be called by children
	public abstract void updateHand(Card card);
	
	
	public Card disproveSuggestion(Solution suggestion) {
		//go through hand to see if there is any card that can disprove the suggestion
		for(Card c: hand) {
			if (c.equals(suggestion.getRoom())) {
				return c;
			}
			else if(c.equals(suggestion.getPerson())) {
				return c;
			}
			else if (c.equals(suggestion.getWeapon())) {
				return c;
			}
		}
		//If no matching card is found, return null
		return null;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public Set<Card> getHand() {
		return hand;
	}

	public void setHand(Set<Card> hand) {
		this.hand = hand;
	}
	
	public void addToSeen(Card seen) {
		seenCards.add(seen);
	}

	public Set<Card> getTheDeck() {
		return theDeck;
	}

	public void setTheDeck(Set<Card> theDeck) {
		this.theDeck = theDeck;
	}

	public ArrayList<Card> getSeenCards() {
		return seenCards;
	}
	
	
	
}
