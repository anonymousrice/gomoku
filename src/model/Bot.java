package model;

public class Bot {
  /** Current game mode */
  private static final String MODE = "FREESTYLE";
  /** Mid point of the board that used to place the first stone of the game */
  private static final int MID_ROW = Game.getNumRow() / 2;

  private static final int MID_COL = Game.getNumColumn() / 2;
  /** Current gaming board object */
  private Board board;
  /** Stone colour of the bot, black = 1, white = -1 */
  private int stoneColour = 0;

  /**
   * Constructs a bot object with stone colour stoneColour, playing on board
   *
   * @param stoneColour the stone colour of the bot
   * @param board current playing board object
   */
  Bot(int stoneColour, Board board) {
    this.stoneColour = stoneColour;
    this.board = board;
  }

  public int getStoneColour() {
    return stoneColour;
  }

  /**
   * Makes the bot place a stone and returns a Tuple object that represents location
   * Algorithm as following:
   * 1). determine if first stone on the board
   * 2). determine the score bot can get
   * 3). determine the score player will get, if player is winning next stone, bot should defence
   * 4). if player will not win next stone, let bot attack on the location calculated in 2)
   *
   * @return a Tuple object that represents a location
   */
  public Tuple play() {
    Tuple result = new Tuple(-1, -1);
    if (board.getNumStones() == 0) {
      result.row_co = MID_ROW;
      result.col_co = MID_COL;
      board.placeStone(result.row_co, result.col_co, stoneColour);
      return result;
    }
    // checks bot's score
    int maxScore = -1;
    for (int i = 0; i < board.getNumRow(); ++i) {
      for (int j = 0; j < board.getNumCol(); ++j) {
        if (board.getStone(i, j) != 0) continue;
        int currentScore = board.scoreAfter(i, j, stoneColour);
        if (stoneColour == -1) currentScore = -currentScore;
        if (currentScore > maxScore) {
          result.row_co = i;
          result.col_co = j;
          maxScore = currentScore;
        } else if (currentScore == maxScore) {
          int opponentScore_max = board.scoreAfter(result.row_co, result.col_co, -stoneColour);
          int opponentScore_current = board.scoreAfter(i, j, -stoneColour);
          if (stoneColour == 1) {
            if (opponentScore_current < opponentScore_max) {
              result.row_co = i;
              result.col_co = j;
              maxScore = currentScore;
            }
          } else {
            if (opponentScore_current > opponentScore_max) {
              result.row_co = i;
              result.col_co = j;
              maxScore = currentScore;
            }
          }
        }
      }
    }
    if (board.oneStepToWin(result.row_co, result.col_co, stoneColour)) {
      board.placeStone(result.row_co, result.col_co, stoneColour);
      return result;
    }
    // checks the player's score
    for (int i = 0; i < board.getNumRow(); ++i) {
      for (int j = 0; j < board.getNumCol(); ++j) {
        if (board.getStone(i, j) != 0) continue;

        if (stoneColour == 1) {
          if (board.scoreAfter(i, j, -stoneColour) <= -(4 * stoneColour)) {
            board.placeStone(i, j, stoneColour);
            result.row_co = i;
            result.col_co = j;
            return result;
          }
        } else {
          if (board.scoreAfter(i, j, -stoneColour) >= -(4 * stoneColour)) {
            board.placeStone(i, j, stoneColour);
            result.row_co = i;
            result.col_co = j;
            return result;
          }
        }
      }
    }
//    System.out.format("coordinate: %d %d\n", result.row_co, result.col_co);
    board.placeStone(result.row_co, result.col_co, stoneColour);
    return result;
  }
}
