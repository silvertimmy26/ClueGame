package clueGame;

public class Card {
	
	private String cardName;
	private CardType type;
	
	
	public Card(String cardName, CardType type) {
		super();
		this.cardName = cardName;
		this.type = type;
	}
	
	public Card() {
		
	}
	
	//Check if cars are equal
	public boolean equals(Card target) {
		//If name and type are the same, return true
		if(this.getCardName().equals(target.getCardName())&& this.getType()==target.getType()) {
			return true;
		}
		//If name/type don't match, return false
		return false;
	}

	public String getCardName() {
		return cardName;
	}

	public CardType getType() {
		return type;
	}
	
	
	
}
