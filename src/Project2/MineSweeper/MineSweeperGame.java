package Project2.MineSweeper;

import java.util.Random;

public class MineSweeperGame {
	private Cell[][] board;
	private GameStatus status;

	public MineSweeperGame() {
		status = GameStatus.NotOverYet;
		board = new Cell[10][5];
		setEmpty();
		layMines (7);
		
	}

	private void setEmpty() {
		for (int r = 0; r < board.length; r++)
			for (int c = 0; c < board[r].length; c++)
				board[r][c] = new Cell(false, false, false);  // totally clear.
	}

	public Cell getCell(int row, int col) {
		return board[row][col];
	}

	public void select(int row, int col) {
		board[row][col].setExposed(true);

		if (board[row][col].isMine())   // did I lose
			status = GameStatus.Lost;
		else {
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

	public int neighboringMines(int row, int col){
		int neighborCount = 0;

		for(int i = row - 1; i <= row + 1; i++)
			for(int j = col - 1; j <= col + 1; j++)
				if(tileIsInbounds(i, j))
					if(board[i][j].isMine())
						neighborCount++;

		return neighborCount;
	}

	public GameStatus getGameStatus() {
		return status;
	}

	public void reset() {
		status = GameStatus.NotOverYet;
		setEmpty();
		layMines (10);
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
}




	//  a non-recursive from .... it would be much easier to use recursion


