package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
	
	private static Board board;
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Clue Game - CSCI 306");
		frame.setSize(1000, 1000);
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		board.setFrame(frame);
		frame.add(board, BorderLayout.CENTER);
		GameControlPanel gcp = new GameControlPanel();
		gcp.setBoard(board);
		board.setGcp(gcp);
		frame.add(gcp, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
