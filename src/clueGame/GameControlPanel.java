package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	
	private JTextField currentPlayer;
	private JTextField currentRoll;
	private JTextField guess;
	private JTextField guessResult;
	
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
		currentPlayer = new JTextField(20);
		turnPanel.add(whoseTurn);
		turnPanel.add(currentPlayer);
		returnPanel.add(turnPanel);
		// second box - rolls
		JPanel rollPanel = new JPanel();
		rollPanel.setLayout(new GridLayout(1, 2));
		JLabel roll = new JLabel("Roll:");
		currentRoll = new JTextField(20);
		rollPanel.add(roll);
		rollPanel.add(currentRoll);
		returnPanel.add(rollPanel);
		// third box and fourth box - button 
		JButton accusationButton = new JButton("Make Accusation");
	    accusationButton.setBackground(Color.CYAN);
	    accusationButton.setOpaque(true);
	    accusationButton.setBorderPainted(false);
		JButton nextButton = new JButton("NEXT!");
	    nextButton.setBackground(Color.GREEN);
	    nextButton.setOpaque(true);
	    nextButton.setBorderPainted(false);
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
	}
	
	private class NextListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
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
	
}

