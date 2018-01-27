package model;

public class Player {
  private Board board;
  private int stoneColour = 0;

  /**
   * Constructs a player object, playing with stone color, stoneColour and playing on the board,
   * board
   *
   * @param stoneColour the colour of stone the player is playing
   * @param board the Board object that player is playing on
   */
  Player(int stoneColour, Board board) {
    this.stoneColour = stoneColour;
    this.board = board;
  }

  public int getStoneColour() {
    return stoneColour;
  }

  /**
   * Places a stone on the location (row, col), with colour stoneColour
   *
   * @param row row coordinate
   * @param col column coordinate
   * @param stoneColour the stone colour of player
   */
  public void putStone(int row, int col, int stoneColour) {
    board.placeStone(row, col, stoneColour);
  }
}
