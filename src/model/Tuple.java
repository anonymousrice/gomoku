package model;

/** A simple Object created to store the coordinate of (row, col) on the board */
public class Tuple {
  public int row_co;
  public int col_co;

  /**
   * Constructs a tuple
   *
   * @param row_co row coordinate
   * @param col_co column coordinate
   */
  Tuple(int row_co, int col_co) {
    this.row_co = row_co;
    this.col_co = col_co;
  }
}
