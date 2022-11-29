package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	
	private JTextField currentPlayer= new JTextField();
	private JTextField currentRoll= new JTextField();
	private JTextField guess;
	private JTextField guessResult;
	private Board board;
	private JFrame clueGameFrame; 
	private String accusationRoom;
	private String accusationPerson;
	private String accusationWeapon;
	private JFrame accusationFrame;
	
	public GameControlPanel() {
		setLayout(new GridLayout(2, 0));
		JPanel topPanel = createTopPanel();
		JPanel bottomPanel = createBottomPanel();
		add(topPanel);
		add(bottomPanel);
	}
	
	public JPanel createTopPanel() {
		// top 1x4 panel within GameControlPanel
		JPanel returnPanel = new JPanel();
		returnPanel.setLayout(new GridLayout(1, 4));
		// Components within the 1x4, first box on left - turn tracker
		JPanel turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(2, 1));
		JLabel whoseTurn = new JLabel("Whose turn?");
		
		turnPanel.add(whoseTurn);
		turnPanel.add(currentPlayer);
		returnPanel.add(turnPanel);
		// second box - rolls
		JPanel rollPanel = new JPanel();
		rollPanel.setLayout(new GridLayout(1, 2));
		JLabel roll = new JLabel("Roll:");
		
		rollPanel.add(roll);
		rollPanel.add(currentRoll);
		returnPanel.add(rollPanel);
		// third box and fourth box - button 
		JButton accusationButton = new JButton("Make Accusation");
		AccusationListener al = new AccusationListener();
		accusationButton.addActionListener(al);
	    accusationButton.setBackground(Color.CYAN);
	    accusationButton.setOpaque(true);
	    accusationButton.setBorderPainted(false);
		JButton nextButton = new JButton("NEXT!");
	    nextButton.setBackground(Color.GREEN);
	    nextButton.setOpaque(true);
	    nextButton.setBorderPainted(false);
	    NextListener n = new NextListener();
	    nextButton.addActionListener(n);
		returnPanel.add(accusationButton);
		returnPanel.add(nextButton);
		
		return returnPanel;
	}
	
	public JPanel createBottomPanel() {
		// bottom 0x2 panel
		JPanel returnPanel = new JPanel();
		returnPanel.setLayout(new GridLayout(0, 2));
		// 1x0 guess panel
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(1, 0));
		guessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		guess = new JTextField();
		guessPanel.add(guess);
		returnPanel.add(guessPanel);
		// 1x0 no border guess result panel
		JPanel guessResultPanel = new JPanel();
		guessResultPanel.setLayout(new GridLayout(1, 0));
		guessResultPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		guessResult = new JTextField();
		guessResultPanel.add(guessResult);
		returnPanel.add(guessResultPanel);	
		return returnPanel;
	}
	
	public void setGuess(String guessString) {
		guess.setText(guessString);
	}
	
	public void setGuessResult(String guessResultString) {
		guessResult.setText(guessResultString);
	}
	
	public void setTurn(Player player, int diceRoll) {
		currentRoll.setText(Integer.toString(diceRoll));
		currentPlayer.setText(player.getName());
		// get color from setup file
		Color color;
		try {
		    Field field = Color.class.getField(player.getColor().toLowerCase());
		    color = (Color)field.get(null);
		} catch (Exception e) {
		    color = null;
		}
		currentPlayer.setBackground(color);
	    currentPlayer.setOpaque(true);
	    revalidate();
	}
	
	private class NextListener implements ActionListener {
		//Set up listener for buttons being pressed
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean next;
			
			//Send off the next stipulations to where need be
			next = board.nextPlayerFlow();
			if (!next) {
				JOptionPane.showMessageDialog(clueGameFrame, "Complete your turn before pressing Next!");
			} else {
				
				//Update the board
				removePanelFromFrame();
				board.handleNextTurn();
			}
		}
	}
	
	private void removePanelFromFrame() {
		this.removeAll();
		clueGameFrame.remove(this); 
		//setTurn(board.getActualPlayer(), board.getDiceRoll());
		setLayout(new GridLayout(2, 0));
		JPanel topPanel = createTopPanel();
		JPanel bottomPanel = createBottomPanel();
		this.add(topPanel);
		this.add(bottomPanel);
		clueGameFrame.add(this, BorderLayout.SOUTH);
	}
	
	private class AccusationListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (board.getCurrentPlayer() == 0) {
				AccusationGUI ag = new AccusationGUI();
				accusationFrame = ag;
				
			}
			
		}
		
	}
	
	private class AccusationGUI extends JFrame {
		
		public AccusationGUI() {
			setSize(400, 250);
			setTitle("Make an Accusation");
			AccusationPanel ap = new AccusationPanel(this);
			add(ap, BorderLayout.CENTER);
			setVisible(true);
		}
	}
	
	private class AccusationPanel extends JPanel {
		
		private JComboBox<String> room, person, weapon;
		
		private class ComboListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == room) {
					accusationRoom = room.getSelectedItem().toString();
				} else if (e.getSource() == person) {
					accusationPerson = person.getSelectedItem().toString();
				} else if (e.getSource() == weapon) {
					accusationWeapon = weapon.getSelectedItem().toString();
				}
				
			}
			
		}
		
		public AccusationPanel(JFrame frame) {
			setLayout(new GridLayout(4, 2));
			JLabel roomLabel = new JLabel("Room");
			JLabel personLabel = new JLabel("Person");
			JLabel weaponLabel = new JLabel("Weapon");
			ComboListener cl = new ComboListener();
			room = new JComboBox<String>();
			room.addActionListener(cl);
			person = new JComboBox<String>();
			person.addActionListener(cl);
			weapon = new JComboBox<String>();
			weapon.addActionListener(cl);
			for (Card c: board.getDeck()) {
				if (c.getType() == CardType.ROOM) {
					room.addItem(c.getCardName());
				} else if (c.getType() == CardType.PERSON) {
					person.addItem(c.getCardName());
				} else if (c.getType() == CardType.WEAPON) {
					weapon.addItem(c.getCardName());
				}
			}
			add(roomLabel);
			add(room);
			add(personLabel);
			add(person);
			add(weaponLabel);
			add(weapon);
			SubmitAccusationListener sal= new SubmitAccusationListener();
			JButton submitButton = new JButton("Submit");
			submitButton.addActionListener(sal);
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(e ->{
				frame.dispose();
			});
			add(submitButton);
			add(cancelButton);
		}
	}
	
	private class SubmitAccusationListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Solution s = new Solution();
			for (Card c: board.getDeck()) {
				if (c.getCardName().equals(accusationPerson)) {
					s.setPerson(c);
				} else if (c.getCardName().equals(accusationRoom)) {
					s.setRoom(c);
				} else if (c.getCardName().equals(accusationWeapon)) {
					s.setWeapon(c);
				}
			}
			board.checkAccusation(s);
			accusationFrame.dispose();
		}
		
	}
	
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();
		JFrame frame = new JFrame();
		frame.setContentPane(panel);
		frame.setSize(750, 180);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		panel.setTurn(new ComputerPlayer("Col. Mustard", "orange", 0, 0), 5);
		panel.setGuess("I have no guess!");
		panel.setGuessResult("So you have nothing?");
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setClueGameFrame(JFrame clueGameFrame) {
		this.clueGameFrame = clueGameFrame;
	}

	public void setCurrentPlayer(JTextField currentPlayer) {
		this.currentPlayer = currentPlayer;
		this.revalidate();
	}

	public void setCurrentRoll(JTextField currentRoll) {
		this.currentRoll = currentRoll;
		this.revalidate();
	}

	public void setGuess(JTextField guess) {
		this.guess = guess;
		this.revalidate();
	}

	public void setGuessResult(JTextField guessResult) {
		this.guessResult = guessResult;
		this.revalidate();
	}
	
}

