package model;

public class Board {
  /** Number of board columns and rows */
  private int numRow;

  private int numCol;
  /** The array of arrays to store value of each location on board (aka stones) */
  private int[][] board;
  /** The total number of stones that have placed */
  private int numStones;

  /**
   * Constructs a new Board object with desired number of columns and rows
   *
   * @param numRow desired number of rows
   * @param numCol desired number of columns
   */
  Board(int numRow, int numCol) {
    this.board = new int[numRow][numCol];
    this.numRow = numRow;
    this.numCol = numCol;
    numStones = 0;
  }

  /**
   * Places a stone on the location (row, col) in int[][]board with black colour = 1, white = -1
   *
   * @param row row coordinate of the location
   * @param col column coordinate of the location
   * @param colour color of stone to be placed
   */
  public void placeStone(int row, int col, int colour) {
    board[row][col] = colour;
    ++numStones;
  }

  /**
   * Removes a stone at the desired location (row, col) with desired colour
   * black = 1, white = -1
   *
   * @param row row coordinate of the location
   * @param col column coordinate of the location
   */
  public void removeStone(int row, int col) {
    board[row][col] = 0;
    --numStones;
  }

  /**
   * Gets the stone value at the location (row, col)
   * black = 1, white = -1, none = 0
   *
   * @param row row coordinate
   * @param col column coordinate
   * @return
   */
  public int getStone(int row, int col) {
    return board[row][col];
  }

  public int[][] getBoard() {
    return board;
  }

  public int getNumStones() {
    return numStones;
  }

  public int getNumRow() {
    return numRow;
  }

  public int getNumCol() {
    return numCol;
  }

  /**
   * Calculates the score after placing one specified colour of stone at a coordinate. For example,
   * 4 continuous black stones return 4 whereas 4 continuous white stones return -4.
   *
   * @param row the row coordinate
   * @param col the column coordinate
   * @param colour the colour of stone to be placed
   * @return the score point after placing the stone
   */
  public int scoreAfter(int row, int col, int colour) {
    // Note that the following scores account the opponent's score
    // Horizontal, Vertical, Diagonal, Reverse Diagonal
    int score[] = {0, 0, 0, 0};

    // horizontal check
    for (int i = col - 1; i >= 0; --i) {
      if (board[row][i] == colour) {
        score[0] += colour;
      } else {
        score[0] += board[row][i];
        break;
      }
    }
    for (int i = col + 1; i < numCol; ++i) {
      if (board[row][i] == colour) {
        score[0] += colour;
      } else {
        score[0] += board[row][i];
        break;
      }
    }
    // vertical check
    for (int i = row - 1; i >= 0; --i) {
      if (board[i][col] == colour) {
        score[1] += colour;
      } else {
        score[1] += board[i][col];
        break;
      }
    }
    for (int i = row + 1; i < numRow; ++i) {
      if (board[i][col] == colour) {
        score[1] += colour;
      } else {
        score[1] += board[i][col];
        break;
      }
    }

    // diagonal check
    for (int i = 1; row - i >= 0 && col - i >= 0; ++i) {
      if (board[row - i][col - i] == colour) {
        score[2] += colour;
      } else {
        score[2] += board[row - i][col - i];
        break;
      }
    }
    for (int i = 1; row + i < numRow && col + i < numCol; ++i) {
      if (board[row + i][col + i] == colour) {
        score[2] += colour;
      } else {
        score[2] += board[row + i][col + i];
        break;
      }
    }
    // reverse diagonal check
    for (int i = 1; row + i < numRow && col - i >= 0; ++i) {
      if (board[row + i][col - i] == colour) {
        score[3] += colour;
      } else {
        score[3] += board[row + i][col - i];
        break;
      }
    }
    for (int i = 1; row - i >= 0 && col + i < numCol; ++i) {
      if (board[row - i][col + i] == colour) {
        score[3] += colour;
      } else {
        score[3] += board[row - i][col + i];
        break;
      }
    }
    if ((Math.abs(score[0]) >= 2 && Math.abs(score[1]) >= 2) ||
            (Math.abs(score[0]) >= 2 && Math.abs(score[2]) >= 2) ||
            (Math.abs(score[0]) >= 2 && Math.abs(score[3]) >= 2) ||
            (Math.abs(score[1]) >= 2 && Math.abs(score[2]) >= 2) ||
            (Math.abs(score[1]) >= 2 && Math.abs(score[3]) >= 2) ||
            (Math.abs(score[2]) >= 2 && Math.abs(score[3]) >= 2)) {
      return 5 * colour;
    } else if (oneStepToWin(row, col, colour)){
      return 6 * colour;
    } else if (colour == 1) {
      return Math.max(Math.max(score[0], score[1]), Math.max(score[2], score[3])) + 1;
    } else {
      return Math.min(Math.min(score[0], score[1]), Math.min(score[2], score[3])) - 1;
    }
  }

  /**
   * Determines if the game will end after placing on location (row, col)
   *
   * @param row row coordinate
   * @param col column coordinate
   * @param colour colour of the stone, black = 1, white = -1
   * @return true if win after placing this stone, false otherwise
   */
  public boolean oneStepToWin(int row, int col, int colour) {
    this.placeStone(row, col, colour);
    boolean result = Game.ifWin(this);
    this.removeStone(row, col);
    return result;
  }
}
