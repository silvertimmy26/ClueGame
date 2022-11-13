package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class KnownCardsPanel extends JPanel {
	
	private JTextField cardName;
	
	public KnownCardsPanel(Player player) {
		setLayout(new GridLayout(3, 0));
		JPanel peoplePanel = new JPanel();
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		peoplePanel = createPanel(player, CardType.PERSON);
		JPanel roomPanel = new JPanel();
		roomPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		roomPanel = createPanel(player, CardType.ROOM);
		JPanel weaponPanel = new JPanel();
		weaponPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		weaponPanel = createPanel(player, CardType.WEAPON);
		add(peoplePanel);
		add(roomPanel);
		add(weaponPanel);
		setBorder(new TitledBorder (new EtchedBorder(), "Known Cards"));
	}
	
	public JPanel updatePanel(Player player, CardType type) {
		JPanel thePanel = new JPanel();
		if (type == CardType.PERSON) {
			thePanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
			thePanel = createPanel(player, CardType.PERSON);
		} else if (type == CardType.ROOM) {
			thePanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
			thePanel = createPanel(player, CardType.ROOM);
		} else {
			thePanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
			thePanel = createPanel(player, CardType.WEAPON);
		}
		return thePanel;
	}
	
	public JPanel createPanel(Player player, CardType type) {
		
		ArrayList<Card> seenCardsCopy = player.getSeenCards();
		Set<Card> handCardsCopy = player.getHand();
		
		// 0x1 People panel
		JPanel thePanel = new JPanel();
		thePanel.setLayout(new GridLayout(0, 1));
		// 0 x 1 In hand panel
		JPanel inHandPanel = new JPanel();
		inHandPanel.setLayout(new GridLayout(0, 1));
		inHandPanel.setBorder(new TitledBorder (new EtchedBorder(), "In Hand:"));
		JPanel seenPanel = new JPanel();
		seenPanel.setLayout(new GridLayout(0, 1));
		seenPanel.setBorder(new TitledBorder (new EtchedBorder(), "Seen:"));
		boolean isSeenEmpty = true;
		boolean isHandEmpty = true;
		for (Card c: seenCardsCopy) {
			cardName = new JTextField();
			cardName.setText(c.getCardName());
			if (handCardsCopy.contains(c) && c.getType() == type) {
				inHandPanel.add(cardName);
				isHandEmpty = false;
			} else if (c.getType() == type) {
				seenPanel.add(cardName);
				isSeenEmpty = false;
			}
		}
		if (isHandEmpty) {
			cardName = new JTextField();
			cardName.setText("None");
			inHandPanel.add(cardName);
		}
		if (isSeenEmpty) {
			cardName = new JTextField();
			cardName.setText("None");
			seenPanel.add(cardName);	
		}
		
		thePanel.add(inHandPanel);
		thePanel.add(seenPanel);
		return thePanel;

	}
	
	public static void main(String[] args) {
		// testing out all our cards when in hand or seen
		Card testRoom = new Card("Test Room", CardType.ROOM);
		Card testRoom2 = new Card("Test Room 2", CardType.ROOM);
		Card testHand = new Card("Hand Card", CardType.PERSON);
		Card testSeen = new Card("Seen Card", CardType.WEAPON);
		Card testUpdate = new Card("Update Card", CardType.WEAPON);
		Player player = new HumanPlayer("Billy", "red", 0, 0);
		player.addToSeen(testSeen);
		player.addToSeen(testHand);
		player.addToSeen(testRoom);
		player.addToSeen(testRoom2);
		player.updateHand(testRoom);
		player.updateHand(testRoom2);
		KnownCardsPanel panel = new KnownCardsPanel(player);
		JFrame frame = new JFrame();
		frame.setContentPane(panel);
		frame.setSize(180, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		panel.removeAll();
		panel.setLayout(new GridLayout(3, 0));
		JPanel peoplePanel = panel.createPanel(player, CardType.PERSON);
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		JPanel roomPanel = panel.createPanel(player, CardType.ROOM);
		roomPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		JPanel weaponPanel = panel.createPanel(player, CardType.WEAPON);
		weaponPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		panel.add(peoplePanel);
		panel.add(roomPanel);
		panel.add(weaponPanel);
		frame.setContentPane(panel);
		player.addToSeen(testUpdate);
		panel.removeAll();
		peoplePanel = panel.updatePanel(player, CardType.PERSON);
		roomPanel = panel.updatePanel(player, CardType.ROOM);
		weaponPanel = panel.updatePanel(player, CardType.WEAPON);
		panel.add(peoplePanel);
		panel.add(roomPanel);
		panel.add(weaponPanel);
		frame.setContentPane(panel);
	}
	
}
