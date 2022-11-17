package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Player {
	
	private String name;
	private String color;
	protected int row;
	protected int column;
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

	public void draw(Graphics g, int cellWidth, int cellHeight) {
		//Method to draw each player as an 'oval'
		
		//Get some circle locations for cell placement
		int circleX = (cellWidth * this.getColumn());
		int circleY = (cellHeight * this.getRow());
		
		//Go through the strings of color to make them actual colors
		Color realColor;
		try {
		    Field field = Color.class.getField(this.color.toLowerCase());
		    realColor = (Color)field.get(null);
		} catch (Exception e) {
		    realColor = null;
		}
		//Set the color and draw.
		g.setColor(realColor);
		g.fillOval(circleX, circleY, cellWidth, cellHeight);
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

	public abstract Solution turnHandling(Set<BoardCell> targets, Map<Character, Room> roomMap);

	protected abstract Solution createSuggestion(Room room);
	
}
