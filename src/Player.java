import java.util.Scanner;

/**
 * Created by IntelliJ IDEA.
 * User: Zonggen
 * Date: 1/3/2018
 * Time: 3:39 PM
 */
public class Player {
    private Board board;

    public Player() {
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void play(){
        int[][] boardArr = board.getBoard();
        System.out.println("Please enter the coordinate: (row, col)");
        Scanner sc = new Scanner (System.in);
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
        boardArr[row][col] = 1;
    }
}
