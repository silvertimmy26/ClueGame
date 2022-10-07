package clueGame;

public class Room {
	
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	
	
	public Room(String name, BoardCell centerCell, BoardCell labelCell) {
		super();
		this.name = name;
		this.centerCell = centerCell;
		this.labelCell = labelCell;
	}

	public String getName() {
		return null;
	}

	public BoardCell getLabelCell() {
		return labelCell;
	}

	public BoardCell getCenterCell() {
		return centerCell;
	}	
	
}
