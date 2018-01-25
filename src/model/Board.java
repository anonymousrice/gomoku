package model;


public class Board {
    private int[][] board;

    public Board(int numRow, int numCol) {
        this.board = new int[numRow][numCol];
    }

    public void placeStone(int row, int col){
        board[row][col] = 1;
    }

    public int[][] getBoard() {
        return board;
    }
}
