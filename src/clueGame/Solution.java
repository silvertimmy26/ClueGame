package clueGame;

public class Solution {
	
	private Card room= new Card();
	private Card person= new Card();
	private Card weapon=new Card();
	
	public Solution() {
		
	}
	
	public Solution(Card room, Card person, Card weapon) {
		super();
		this.room = room;
		this.person = person;
		this.weapon = weapon;
	}
	public Card getRoom() {
		return room;
	}
	public Card getPerson() {
		return person;
	}
	public Card getWeapon() {
		return weapon;
	}
	public void setRoom(Card room) {
		this.room = room;
	}
	public void setPerson(Card person) {
		this.person = person;
	}
	public void setWeapon(Card weapon) {
		this.weapon = weapon;
	}
	
}
