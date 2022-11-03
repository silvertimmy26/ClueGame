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
	
	public Player(String name, String color, int row, int column) {
		super();
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
	}

	public abstract void updateHand(Card card);
	
	public void updateSeen(Card seenCard) {
		//seenCards.add(seenCard);
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		Card temp=new Card("temp",CardType.PERSON);
		return temp;
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
	
	
	
}
