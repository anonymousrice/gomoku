import java.util.Scanner;

/**
 * Created by IntelliJ IDEA.
 * User: Zonggen
 * Date: 1/2/2018
 * Time: 2:12 PM
 */
public class Game {
    private static final int NUM_ROW = 15;
    private static final int NUM_COLUMN = 15;
    private int[][] board;

    public Game() {
        this.board = new int [NUM_ROW][NUM_COLUMN];
    }

    private boolean horizontalCheck(int[] row){
        int counter = 0;
        for (int i : row){
            if (row[i]==1){
                counter += row[i];
            } else {
                counter = 0;
            }
            if (counter>=5){
                return true;
            }
        }
        return false;
    }

    private boolean verticalCheck(int[][] arr, int col){
        int[] vertArr = new int [arr.length];
        for (int row = 0; row < arr.length; ++row){
            vertArr[row] = arr[row][col];
        }
        return horizontalCheck(vertArr);
    }

    @SuppressWarnings("all")
    private boolean diagonalCheck (int[][] arr){
        int min = (NUM_ROW > NUM_COLUMN)? NUM_COLUMN : NUM_ROW;
        int[] diagArr = new int[min];
        // Three cases when deciding first element of the diagonal array
        // i). Main diagonal, where row# = col#
        for (int row = 0; row < min; ++row){
            diagArr[row] = arr [row][row];
        }
        if (horizontalCheck(diagArr)){
            return true;
        }
        // ii). Diagonals in upper triangle of the matrix
        for (int row = 0, col = 1; col < NUM_COLUMN; ++col){
            for (int dia_row = 0, dia_col = col; dia_col < NUM_COLUMN; ++dia_row, ++dia_col){
                diagArr [dia_row] = arr[dia_row][dia_col];
            }
            if (horizontalCheck(diagArr)){
                return true;
            } else {
                diagArr = new int[min];
            }
        }
        // iii). Diagonals in lower triangle of the matrix
        for (int row = 1, col = 0; row < NUM_ROW; ++row){
            for (int dia_row = row, dia_col = 0; dia_row < NUM_ROW; ++dia_row, ++dia_col){
                diagArr [dia_col] = arr[dia_row][dia_col];
            }
            if (horizontalCheck(diagArr)){
                return true;
            } else {
                diagArr = new int [min];
            }
        }
        return false;
    }

    // Check diagonal of the other direction
    private boolean reverseDiagonalCheck (int[][] arr){
        int[][] reverse = new int[NUM_ROW][NUM_COLUMN];
        for (int row = 0; row < NUM_ROW; ++ row){
            for (int col = 0; col < NUM_COLUMN; ++col){
                reverse[row][col] = arr[row][NUM_COLUMN-1-col];
            }
        }
        return diagonalCheck(reverse);
    }


    public boolean ifWin(){
        for (int[] row : board){
            if (horizontalCheck(row)){
                return true;
            }
        }
        for (int col = 0; col < NUM_COLUMN; ++col){
            if (verticalCheck(board, col)){
                return true;
            }
        }
        return diagonalCheck(board) || reverseDiagonalCheck(board);
    }

    public void play (){
        System.out.println("Please enter the coordinate: (row, col)");
        Scanner sc = new Scanner(System.in);
        int counter = 0;
        int row = -1;
        int col = -1;
        while (sc.hasNextInt()){
            if (counter == 0){
                row = sc.nextInt();
            } else if (counter == 1){
                col = sc.nextInt();
            }
            counter++;
            if (counter >= 2) break;
        }
        board[row][col] = 1;
        if (ifWin()){
            System.out.println("You win!");
        } else {
            System.out.println("Continue...");
        }
    }
}
