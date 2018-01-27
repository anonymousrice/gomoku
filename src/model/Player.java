package model;

import java.util.Scanner;

public class Player {
  private Board board;
  private int stoneColour = 0;

  Player(int stoneColour, Board board) {
    this.stoneColour = stoneColour;
    this.board = board;
  }

  public int getStoneColour() {
    return stoneColour;
  }

  public void putStone (int row, int col, int stoneColour){
    board.placeStone(row, col, stoneColour);
  }
}
