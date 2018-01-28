package model;

import controller.BoardController;

/** Record the black stone as 1 on board, and -1 for the white stone */
public class Game {
  /** The board size of the game */
  private static final int NUM_ROW = 15;

  private static final int NUM_COLUMN = 15;
  /** Actual Board object and int[][] to store actual stone values */
  private Board boardObj;

  private int[][] board;
  /** Currently playing Player and Bot objects */
  private Player player;

  private Bot bot;
  /** Whether player win go first */
  private boolean playerFirst;

  /** Constructs a Game object and initialize a Board object with size (NUM_ROW, NUM_COLUMN) */
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

  /**
   * Checks if the game is already over by checking horizontal value of one row
   *
   * @param row integer array that represents a row on the board
   * @return true if game is over, false otherwise
   */
  private static boolean horizontalCheck(int[] row) {
    int colour = 0;
    int counter = 0;
    for (int i : row) {
      if (i != 0 && i == colour) {
        ++counter;
      } else {
        counter = 0;
        if (i != 0) {
          colour = i;
          ++counter;
        }
      }
      if (counter == 5) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if the game is already over by checking vertical value of one column
   *
   * @param arr an array represents the board
   * @param col desired checking column
   * @return true if game is over, false otherwise
   */
  private static boolean verticalCheck(int[][] arr, int col) {
    int[] vertArr = new int[NUM_ROW];
    for (int row = 0; row < NUM_ROW; ++row) {
      vertArr[row] = arr[row][col];
    }
    return horizontalCheck(vertArr);
  }

  /**
   * Checks if the game is already over by checking diagonal value
   *
   * @param arr an array represents the board
   * @return true if game is over, false otherwise
   */
  @SuppressWarnings("all")
  private static boolean diagonalCheck(int[][] arr) {
    int min = (NUM_ROW > NUM_COLUMN) ? NUM_COLUMN : NUM_ROW;
    int[] diagArr = new int[min];
    // Three cases when deciding first element of the diagonal array
    // i). Main diagonal, where row# = col#
    for (int row = 0; row < min; ++row) {
      diagArr[row] = arr[row][row];
    }
    if (horizontalCheck(diagArr)) {
      return true;
    }
    // ii). Diagonals in upper triangle of the matrix
    for (int row = 0, col = 1; col < NUM_COLUMN; ++col) {
      for (int dia_row = 0, dia_col = col; dia_col < NUM_COLUMN; ++dia_row, ++dia_col) {
        diagArr[dia_row] = arr[dia_row][dia_col];
      }
      if (horizontalCheck(diagArr)) {
        return true;
      } else {
        diagArr = new int[min];
      }
    }
    // iii). Diagonals in lower triangle of the matrix
    for (int row = 1, col = 0; row < NUM_ROW; ++row) {
      for (int dia_row = row, dia_col = 0; dia_row < NUM_ROW; ++dia_row, ++dia_col) {
        diagArr[dia_col] = arr[dia_row][dia_col];
      }
      if (horizontalCheck(diagArr)) {
        return true;
      } else {
        diagArr = new int[min];
      }
    }
    return false;
  }

  /**
   * Checks if the game is already over by checking reversed diagonal value
   *
   * @param arr an array represents the board
   * @return true if game is over, false otherwise
   */
  private static boolean reverseDiagonalCheck(int[][] arr) {
    int[][] reverse = new int[NUM_ROW][NUM_COLUMN];
    for (int row = 0; row < NUM_ROW; ++row) {
      for (int col = 0; col < NUM_COLUMN; ++col) {
        reverse[row][col] = arr[row][NUM_COLUMN - 1 - col];
      }
    }
    return diagonalCheck(reverse);
  }

  /**
   * Checks if the game is over, by checking all directions
   *
   * @param board currently playing board
   * @return true if the game is over, false otherwise
   */
  public static boolean ifWin(Board board) {
    for (int[] row : board.getBoard()) {
      if (horizontalCheck(row)) {
        return true;
      }
    }
    for (int col = 0; col < NUM_COLUMN; ++col) {
      if (verticalCheck(board.getBoard(), col)) {
        return true;
      }
    }
    return diagonalCheck(board.getBoard()) || reverseDiagonalCheck(board.getBoard());
  }

  public Player getPlayer() {
    return player;
  }

  public Bot getBot() {
    return bot;
  }

  public Board getBoardObj() {
    return boardObj;
  }

  public boolean isPlayerFirst() {
    return playerFirst;
  }

  /**
   * Checks if there's place to place another stone on the board
   *
   * @return true if there's space on the board, false otherwise
   */
  public boolean isFull() {
    for (int[] arr : board) {
      for (int value : arr) {
        if (value == 0) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Initializes the game and flips a coin
   * If heads, player plays first
   * otherwise bot plays first
   */
  public void play(BoardController controller) {
    boolean playerFirst = false;
    controller.appendText("Flipping a coin...\n");
    if (Math.random() < 0.5) {
      controller.appendText("Heads\n");
      controller.appendText("You go first...\n");
      playerFirst = true;
    } else {
      controller.appendText("Tails\n");
      controller.appendText("Computer goes first...\n");
    }
    this.bot = new Bot((playerFirst ? -1 : 1), boardObj);
    this.player = new Player((playerFirst ? 1 : -1), boardObj);
    this.playerFirst = playerFirst;
  }
}
