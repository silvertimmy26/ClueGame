package clueGame;

public class Card {
	
	private String cardName;
	private CardType type;
	
	
	public Card(String cardName, CardType type) {
		super();
		this.cardName = cardName;
		this.type = type;
	}
	
	public boolean equals(Card target) {
		Card targetTemp=new Card("name",CardType.ROOM);
		return (targetTemp.getCardName().equals(this.getCardName()));
	}

	public String getCardName() {
		return cardName;
	}

	public CardType getType() {
		return type;
	}
	
	
	
}
