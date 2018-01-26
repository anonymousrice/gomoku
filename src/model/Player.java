package model;

import java.util.Scanner;

public class Player {
  private Board board;
  private int stoneColour = 0;

  public Player(int stoneColour, Board board) {
    this.stoneColour = stoneColour;
    this.board = board;
  }

  public int getStoneColour() {
    return stoneColour;
  }

  public void play() {
    System.out.println("Please enter the coordinate: (row, col)");
    Scanner sc = new Scanner(System.in);
    int counter = 0;
    int row = -1;
    int col = -1;
    while (sc.hasNextInt()) {
      if (counter == 0) {
        row = sc.nextInt();
      } else if (counter == 1) {
        col = sc.nextInt();
      }
      counter++;
      if (counter >= 2) break;
    }
    board.placeStone(row, col, stoneColour);
  }
}
