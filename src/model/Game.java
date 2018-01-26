package model;

/**
 * Record the black stone as 1 on board,
 * and -1 for the white stone
 */
public class Game {
    private static final int NUM_ROW = 15;
    private static final int NUM_COLUMN = 15;
    private Board boardObj;
    private int[][] board;

    public Game() {
        boardObj = new Board(NUM_ROW, NUM_COLUMN);
        this.board = boardObj.getBoard();
    }

    public static int getNumRow() {
        return NUM_ROW;
    }

    public static int getNumColumn() {
        return NUM_COLUMN;
    }

    private boolean horizontalCheck(int[] row){
        int counter = 0;
        for (int i : row){
            if (i != 0){
                counter += i;
            } else {
                counter = 0;
            }
            if (counter <= -4 || counter >= 4){
                return true;
            }
        }
        return false;
    }

    private boolean verticalCheck(int[][] arr, int col){
        int[] vertArr = new int [NUM_ROW];
        for (int row = 0; row < NUM_ROW; ++row){
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

    public boolean isFull(){
        for (int[] arr : board){
            for (int value : arr){
                if (value == 0){
                    return false;
                }
            }
        }
        return true;
    }

    public void play (){
        boolean playerFirst = false;
        System.out.println("Flipping a coin...");
        if (Math.random() < 0.5){
            System.out.println("Heads");
            System.out.println("You go first...");
            playerFirst = true;
        } else {
            System.out.println("Tails");
            System.out.println("Computer goes first...");
        }

        Bot bot = new Bot((playerFirst?-1:1),boardObj);
        Player player = new Player((playerFirst?1:-1),boardObj);

        while (!ifWin() && !isFull()){
            if (playerFirst){
                player.play();
            } else {
                bot.play();
            }
            System.out.println("-----------------------------------");
            for (int[] rowArr : board){
                for (int i : rowArr){
                    System.out.print(i + "\t");
                }
                System.out.println("");
            }
            playerFirst = !playerFirst;
        }

        if (ifWin() && !playerFirst){
            System.out.println("You win!");
        } else if(ifWin() && playerFirst){
            System.out.println("Computer wins!");
        } else if (isFull()){
            System.out.println("Board is full...");
        }
    }
}
