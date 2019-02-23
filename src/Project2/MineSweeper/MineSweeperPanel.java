package Project2.MineSweeper;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MineSweeperPanel extends JPanel {

	private JButton[][] board;
	private JButton butQuit;
	private Cell iCell;
	private int row = 10;
	private int col = 10;
	private int numMines = 7;

	private MineSweeperGame game;  // model

	public MineSweeperPanel (){
		JPanel bottom = new JPanel();
		JPanel center = new JPanel();
        setLayout(new BorderLayout());

        // create game, listeners
		MouseListener mouseListener = new MouseListener();

		game = new MineSweeperGame(row, col, numMines);

		// create the board
		board = new JButton[row][col];
		center.setLayout(new GridLayout(board.length, board[0].length));


		for (int r = 0; r < board.length; r++)
			for (int c = 0; c < board[r].length; c++) {
				board[r][c] = new JButton("");
				board[r][c].addMouseListener(mouseListener);
				center.add(board[r][c]);
			}

		displayBoard();

		bottom.setLayout(new GridLayout(3, 2));

		// add all to contentPane
		add(new JLabel("!!!!! MineSweeper !!!!!"), BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);

	}



	private void displayBoard() {

		for (int r = 0; r < board.length; r++)
			for (int c = 0; c < board[r].length; c++) {
				iCell = game.getCell(r, c);

				board[r][c].setText("");

				// readable, ifs are verbose
				if (iCell.isExposed())
					board[r][c].setEnabled(false);
				else
					board[r][c].setEnabled(true);

				if(iCell.isFlagged())
					board[r][c].setText("F");
				else {
					if (iCell.isMine())
						board[r][c].setText("!");

					if (!iCell.isMine()) {
						if (iCell.getNumNeighboringMines() > 0) {
							board[r][c].setText(Integer.toString(iCell.getNumNeighboringMines()));
						}
					}
				}


			}
	}

	public void setRowColMine(int row, int col, int mines){
	    this.row = row;
	    this.col = col;
	    numMines = mines;
    }

	//Can flag and reactivate an already clicked mine, fix later
	private class MouseListener implements java.awt.event.MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if(SwingUtilities.isRightMouseButton(e))
				for (int r = 0; r < board.length; r++)
					for (int c = 0; c < board[r].length; c++)
						if (board[r][c] == e.getSource()) {
							iCell = game.getCell(r, c);
							if (!iCell.isExposed())
								if(iCell.isFlagged()) {
									game.flag(r, c);
									board[r][c].setText("");
								} else {
									game.flag(r, c);
									board[r][c].setText("F");
							}
						}

			if(SwingUtilities.isLeftMouseButton(e)){
				for (int r = 0; r < board.length; r++)
					for (int c = 0; c < board[r].length; c++)
						if (board[r][c] == e.getSource()) {
							iCell = game.getCell(r, c);
							if(!iCell.isFlagged())
								game.select(r, c);
						}

				displayBoard();



				if (game.getGameStatus() == GameStatus.Lost) {
					displayBoard();
					JOptionPane.showMessageDialog(null, "You Lose " +
							"\n The game will reset");
					//exposeMines = false;
					game.reset();
					displayBoard();

				}

				if (game.getGameStatus() == GameStatus.WON) {
					JOptionPane.showMessageDialog(null,
							"You Win: all mines have been found!" +
									"\n The game will reset");
					game.reset();
					displayBoard();
				}

			}


		}

		@Override
		public void mousePressed(MouseEvent e) { }

		@Override
		public void mouseReleased(MouseEvent e) { }

		@Override
		public void mouseEntered(MouseEvent e) { }

		@Override
		public void mouseExited(MouseEvent e) { }
		}

	}




