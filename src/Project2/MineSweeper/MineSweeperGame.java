package Project2.MineSweeper;

import java.util.Random;

public class MineSweeperGame {
	private Cell[][] board;
	private GameStatus status;

	/******************************************************************
	 * @param row integer to set the amount of rows for the board
	 * @param col integer to set the number of columns for the board
	 * @param numMines integer to set how many mines are placed
	 * Default constructor to create an array of cells, set mines in
	 * the game and calculate how many mines are around each cell
	 *****************************************************************/
	public MineSweeperGame(int row, int col, int numMines) {
		status = GameStatus.NotOverYet;
		board = new Cell[row][col];
		setEmpty();
		layMines (numMines);
		setNeighboringMines();
	}

	/******************************************************************
	 * Sets the entire board to empty cells
	 *****************************************************************/
	private void setEmpty() {
		for (int r = 0; r < board.length; r++)
			for (int c = 0; c < board[r].length; c++)
				board[r][c] = new Cell(false, false,
						false, false);  // totally clear.
	}

	/******************************************************************
	 * returns the current cell
	 *****************************************************************/
	public Cell getCell(int row, int col) {
		return board[row][col];
	}

	/******************************************************************
	 * @param row integer corresponding to the row value of a cell
	 * @param col integer corresponding to the column value of a cell
	 * This class selects a cell, determines what the cell is and what
	 * should be done based on minesweeper rules
	 *****************************************************************/
	public void select(int row, int col) {
		board[row][col].setExposed(true);

		//Checks if the cell is a mine and lose the game if it is
		if (board[row][col].isMine())
			status = GameStatus.Lost;
		else {
			if(!board[row][col].IsNeighboringMine())
				zeroFill(row, col);
			for (int r = 0; r < board.length; r++)     // are only mines left
			    for (int c = 0; c < board[0].length; c++) {
					if (!board[r][c].isExposed()) {
						if(!board[r][c].isMine()) {
							status = GameStatus.NotOverYet;
							c = board[r].length;
							r = board.length + 1;
						}
					}
					else
						status = GameStatus.WON;
				}

		}
	}

	public void recursiveFill(int r, int c) {
		if (tileIsInbounds(r, c))
			board[r][c].setExposed(true);

		//Check left
		if (tileIsInbounds(r, c - 1))
				if (isWhiteSpace(r, c -1))
					recursiveFill(r, c - 1);
				 else if (board[r][c - 1].IsNeighboringMine()
						&& !board[r][c - 1].isFlagged())
					board[r][c - 1].setExposed(true);

		//Check right
		if (tileIsInbounds(r, c + 1))
			if (isWhiteSpace(r, c + 1))
				recursiveFill(r, c + 1);
			else if (board[r][c + 1].IsNeighboringMine())
				board[r][c + 1].setExposed(true);

		//Check up
		if (tileIsInbounds(r - 1, c))
			if (isWhiteSpace(r - 1, c))
				recursiveFill(r - 1, c);
			else if (board[r - 1][c].IsNeighboringMine())
				board [r - 1][c].setExposed(true);

		//Check down
		if (tileIsInbounds(r + 1, c))
			if (isWhiteSpace(r + 1, c))
				recursiveFill(r + 1, c);
			else if (board[r + 1][c].IsNeighboringMine())
				board[r + 1][c].setExposed(true);
	}

	/******************************************************************
	 * @param r integer representing the row value of the first tile
	 * @param c integer representing the column value of the first tile
	 * A non recursive zero fill, if using this method go to
	 * isWhiteSpace and comment out the last line.
	 ******************************************************************/
	public void zeroFill(int r, int c){
		boolean foundNew = true;
		int countExpose = 0;

		board[r][c].setExposed(true);

		while (foundNew){
			for (int row = 0; row < board.length; row++)     // are only mines left
				for (int col = 0; col < board[0].length; col++){
					if (tileIsInbounds(row, col - 1))
						if (isWhiteSpace(row, col) && board[row][col].isExposed()) {
							board[row][col - 1].setExposed(true);
							countExpose++;
						}

					if (tileIsInbounds(row, col + 1))
						if (isWhiteSpace(row, col) && board[row][col].isExposed()){
							board[row][col + 1].setExposed(true);
							countExpose++;
						}

					if (tileIsInbounds(row + 1, col))
						if (isWhiteSpace(row, col) && board[row][col].isExposed()) {
							board[row + 1][col].setExposed(true);
							countExpose++;
						}

					if (tileIsInbounds(row - 1, col))
						if (isWhiteSpace(row, col) && board[row][col].isExposed()) {
							board[row - 1][col].setExposed(true);
							countExpose++;
						}
				}
			if (!(countExpose > 0))
				foundNew = false;
		}
	}

	public void flag(int row, int col) {
		if(board[row][col].isFlagged())
			board[row][col].setFlagged(false);
		 else
		 	board[row][col].setFlagged(true);
		}

	public GameStatus getGameStatus() {
		return status;
	}

	public void reset() {
		status = GameStatus.NotOverYet;
		setEmpty();
		layMines (10);
		setNeighboringMines();
	}

	/******************************************************************
	 * Helper method to check if a tile is in bounds
	 * @param row the row value of the tile being checked
	 * @param col the column value of the tile being checked
	 * @return true if the tile is in the array, false if it is not
	 *****************************************************************/
	private boolean tileIsInbounds(int row, int col){
		return row >= 0 && col >= 0
				&& row < board.length && col < board[0].length;
	}

	private boolean isWhiteSpace(int r, int c){
		return !board[r][c].isMine()
				&& !board[r][c].isFlagged()
				&& !board[r][c].IsNeighboringMine()
				//comment out this line if using the non recursive fill
				&& !board[r][c].isExposed();
	}

	private void layMines(int mineCount) {
		int i = 0;		// ensure all mines are set in place

		Random random = new Random();
		while (i < mineCount) {			// perhaps the loop will never end :)
			int c = random.nextInt(board[0].length);
			int r = random.nextInt(board.length);

			if (!board[r][c].isMine()) {
				board[r][c].setMine(true);
				i++;
			}
		}
	}

	private int neighboringMines(int row, int col){
		int neighborCount = 0;

		for(int i = row - 1; i <= row + 1; i++)
			for(int j = col - 1; j <= col + 1; j++)
				if(tileIsInbounds(i, j))
					if(board[i][j].isMine())
						neighborCount++;

		return neighborCount;
	}

	private void setNeighboringMines(){
		for (int r = 0; r < board.length; r++)
			for (int c = 0; c < board[r].length; c++){
				int neighborCount = 0;
				if(!board[r][c].isMine()) {
					neighborCount = neighboringMines(r, c);
					if (neighborCount > 0) {
						board[r][c].setIsNeighboringMine(true);
						board[r][c].setNumNeighboringMines(neighborCount);
				}
			}
		}
	}
}




	//  a non-recursive from .... it would be much easier to use recursion


