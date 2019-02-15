package Project2.MineSweeper;


public class Cell {
    private boolean isExposed;
    private boolean isMine;
    private boolean isNeighboringMine;
    private boolean isFlagged;
    private int numNeighboringMines;

    public Cell(boolean exposed, boolean mine, boolean neighboringMine, boolean flagged) {
        isExposed = exposed;
        isMine = mine;
        isNeighboringMine = neighboringMine;
        isFlagged = flagged;
    }

    public boolean isExposed() {
        return isExposed;
    }

    public void setExposed(boolean exposed) {
        isExposed = exposed;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean IsNeighboringMine(){
        return isNeighboringMine;
    }

    public  void setIsNeighboringMine(boolean NeighboringMine){
        isNeighboringMine = NeighboringMine;
    }

    public int getNumNeighboringMines(){
        return numNeighboringMines;
    }

    public void setNumNeighboringMines(int numNeighboringMines){
        this.numNeighboringMines = numNeighboringMines;
    }

    public boolean isFlagged(){
        return isFlagged;
    }

    public void setFlagged(boolean flagged){
        isFlagged = flagged;
    }
}
