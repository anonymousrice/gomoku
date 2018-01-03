/**
 * Created by IntelliJ IDEA.
 * User: Zonggen
 * Date: 1/3/2018
 * Time: 1:26 PM
 */
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
