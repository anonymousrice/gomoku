package model;


public class Board {
    private int[][] board;
    private int num_stones;

    public Board(int numRow, int numCol) {
        this.board = new int[numRow][numCol];
        num_stones = 0;
    }

    public void placeStone(int row, int col, int colour){
        board[row][col] = colour;
        ++num_stones;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getNum_stones() {
        return num_stones;
    }
}
