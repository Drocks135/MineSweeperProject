package Project2.MineSweeper;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MineSweeperPanel extends JPanel {

	private JButton[][] board;
	private JButton butQuit;
	private Cell iCell;
	private int row = 10;
	private int col = 5;
	private int numMines = 7;
	MouseListener mouseListener = new MouseListener();
	JPanel center = new JPanel();
	JPanel optionMenu = new JPanel();
	JTextField txtMines, txtRow, txtCol;

	private MineSweeperGame game;  // model

	public MineSweeperPanel (){
		JPanel bottom = new JPanel();
        setLayout(new BorderLayout());

		//instaniate the game
		game = new MineSweeperGame(row, col, numMines);

		createBoard();
		optionPanel();

		//Instantiate quit button, and make it exit on click
		butQuit = new JButton("Quit");
		butQuit.addActionListener(e -> System.exit(0));

		bottom.setLayout(new GridLayout(3, 2));
		bottom.add(butQuit);

		// add all to contentPane
		add(new JLabel("!!!!! MineSweeper !!!!!"), BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

	}

	public void createBoard(){
		center.removeAll();

		// create the board
		board = new JButton[row][col];
		center.setLayout(new GridLayout(board.length,
				board[0].length));

		//instantiate buttons, add listeneres
		// and add them to the center panel
		for (int r = 0; r < board.length; r++)
			for (int c = 0; c < board[r].length; c++) {
				board[r][c] = new JButton("");
				board[r][c].addMouseListener(mouseListener);
				center.add(board[r][c]);
			}

		displayBoard();
	}

	private void optionPanel(){
		//sets up the option menu
		JTextField mines, row, col;
		JLabel labMines, labRow, labCol;
		optionMenu.setLayout(new GridLayout(2, 3));

		labMines = new JLabel("Enter Mines: " + "\n" + "Default: 7");
		labRow = new JLabel("Enter rows: " + "\n" + "Default: 10");
		labCol = new JLabel("Enter Columns: " + "\n" + "Default: 5");

		txtMines = new JTextField("7");
		txtRow = new JTextField("10");
		txtCol = new JTextField("5");

		optionMenu.add(labMines);
		optionMenu.add(labRow);
		optionMenu.add(labCol);
		optionMenu.add(txtMines);
		optionMenu.add(txtRow);
		optionMenu.add(txtCol);
	}

	public void showOptionPanel(){
		int optionResult;
		optionResult = JOptionPane.showConfirmDialog(null,
						optionMenu,
						"MineSweeper Options",
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE);
		if (optionResult == 0) {
			try {
				this.row = Integer.parseInt(txtRow.getText());
				this.col = Integer.parseInt(txtCol.getText());
				this.numMines = Integer.parseInt(txtMines.getText());
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null,
						"Invalid input, please use integer numbers",
						"Warning",
						JOptionPane.WARNING_MESSAGE);
				showOptionPanel();
			}

			if (this.numMines >= this.row * this.col) {
				JOptionPane.showMessageDialog(null,
						"Number of mines cannot exceed board size",
						"Warning",
						JOptionPane.WARNING_MESSAGE);
				showOptionPanel();
			} else if (this.row > 30 || this.col > 30
					|| this.row <= 0 || this.col <= 0){
				JOptionPane.showMessageDialog(null,
						"Board Size not valid, board must be bigger " +
								"than 0 and less than 30",
						"Warning",
						JOptionPane.WARNING_MESSAGE);
				showOptionPanel();
			} else {
				game = new MineSweeperGame(row, col, numMines);
				createBoard();
			}



		}
	}


	/******************************************************************
	 * Refreshes the board and put's text onto corresponding mines
	 *****************************************************************/
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
						if (iCell.getNeighbor() > 0 && iCell.isExposed()) {
							board[r][c].setText(
									Integer.toString(iCell.getNeighbor()));
						}
					}
				}


			}
	}

	private class MouseListener implements java.awt.event.MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			//Handles the flagging mechanic, set's the text to F
			//for flagged, or removes and F and changes the cells
			//properties
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

			//Handles left clicks, calls the game to make necessary
			//actions
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




