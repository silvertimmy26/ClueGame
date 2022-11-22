package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
	
	private static Board board;
	
	public static void main(String[] args) {
		
		//Make the frame
		JFrame frame = new JFrame("Clue Game - CSCI 306");
		frame.setSize(1000, 1000);
		
		//Create board and add it to the frame
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		board.setFrame(frame);
		frame.add(board, BorderLayout.CENTER);
		
		//Create our game control panel and add it where need be
		GameControlPanel gcp = new GameControlPanel();
		gcp.setBoard(board);
		gcp.setClueGameFrame(frame);
		frame.add(gcp, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.setGcp(gcp);
		
		//Create our known cards panel and add it where need be
		KnownCardsPanel kcp = new KnownCardsPanel(board.getPlayers().get(0));
		kcp.setFrame(frame);
		kcp.setPreferredSize(new Dimension(150, 150));
		frame.add(kcp, BorderLayout.EAST);
		board.setKcp(kcp);
		board.startUp();
		frame.setVisible(true);
		
		JOptionPane.showMessageDialog(frame, "You are Sheriff Silly. Can you find the solution before the computer players?");
		
	}
	
}
