package clueGame;

import java.awt.Color;
import java.util.Set;

public abstract class Player {
	
	private String name;
	private String color;
	private int row;
	private int column;
	private Set<Card> hand;
	
	public abstract void updateHand(Card card);

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
	
	
	
}
