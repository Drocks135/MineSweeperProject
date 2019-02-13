package Project2.MineSweeper;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MineSweeperPanel extends JPanel {

	private JButton[][] board;
	private JButton butQuit;
	private Cell iCell;

	private MineSweeperGame game;  // model

	public MineSweeperPanel() {

		JPanel bottom = new JPanel();
		JPanel center = new JPanel();

		// create game, listeners
		ButtonListener listener = new ButtonListener();

		game = new MineSweeperGame();

		// create the board
		center.setLayout(new GridLayout(5, 5));
		board = new JButton[10][5];

		for (int row = 0; row < board.length; row++)
			for (int col = 0; col < board[row].length; col++) {
				board[row][col] = new JButton("");
				board[row][col].addActionListener(listener);
				center.add(board[row][col]);
			}

		displayBoard();

		bottom.setLayout(new GridLayout(3, 2));

		// add all to contentPane
		add(new JLabel("!!!!!!  Mine Sweeper  !!!!"), BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

	}


	private void displayBoard() {

		for (int r = 0; r < board.length; r++)
			for (int c = 0; c < board[r].length; c++) {
				iCell = game.getCell(r, c);

				board[r][c].setText("");

				// readable, ifs are verbose
					
				if (iCell.isMine())
					board[r][c].setText("!");

				if (iCell.isExposed())
					board[r][c].setEnabled(false);
				else
					board[r][c].setEnabled(true);

				int neighborCount = 0;
				if(!iCell.isMine()) {
					neighborCount = game.neighboringMines(r, c);
					if (neighborCount > 0)
						iCell.setIsNeigboringMine(true);
						board[r][c].setText(Integer.toString(neighborCount));
				}



			}
	}


	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			for (int r = 0; r < board.length; r++)
				for (int c = 0; c < board[r].length; c++)
					if (board[r][c] == e.getSource())
						game.select(r, c);

			displayBoard();
								
			if (game.getGameStatus() == GameStatus.Lost) {
				displayBoard();
				JOptionPane.showMessageDialog(null, "You Lose \n The game will reset");
				//exposeMines = false;
				game.reset();
				displayBoard();

			}

			if (game.getGameStatus() == GameStatus.WON) {
				JOptionPane.showMessageDialog(null, "You Win: all mines have been found!\n The game will reset");
				game.reset();
				displayBoard();
			}

		}

	}

}



