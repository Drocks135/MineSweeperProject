package Project2.MineSweeper;


public class Cell {
    private boolean isExposed;
    private boolean isMine;
    private boolean isNeighboringMine;

    public Cell(boolean exposed, boolean mine, boolean neighboringMine) {
        isExposed = exposed;
        isMine = mine;
        isNeighboringMine = neighboringMine;
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

    public boolean isNeighboringMine(){
        return isNeighboringMine;
    }

    public  void setIsNeigboringMine(boolean NeighboringMine){
        isNeighboringMine = NeighboringMine;
    }
}
