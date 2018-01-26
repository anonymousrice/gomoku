package model;


import java.util.ArrayList;

class Bot {
    private static final String MODE = "FREESTYLE";
    private static final int MID_ROW = Game.getNumRow() / 2;
    private static final int MID_COL = Game.getNumColumn() / 2;
    private Board board;
    private int stoneColour = 0;

    public Bot(int stoneColour, Board board) {
        this.stoneColour = stoneColour;
        this.board = board;
    }

    public int getStoneColour() {
        return stoneColour;
    }

    public void play() {
        if (board.getNumStones() == 0) {
            board.placeStone(MID_ROW, MID_COL, stoneColour);
            return;
        }
        // checks bot's score
        int maxScore = -1;
        Tuple result = new Tuple(-1, -1);
        for (int i = 0; i < board.getNumRow(); ++i) {
            for (int j = 0; j < board.getNumCol(); ++j) {
                if (board.getStone(i, j) != 0) continue;
                int currentScore = board.scoreAfter(i, j, stoneColour);
                if (stoneColour==-1) currentScore = -currentScore;
                if (currentScore > maxScore){
                    result.row_co = i;
                    result.col_co = j;
                } else if (currentScore == maxScore){
                    int opponentScore_max = board.scoreAfter(i,j,-stoneColour);
                    int opponentScore_current = board.scoreAfter(i,j,-stoneColour);
                    if (stoneColour == 1){
                        if (opponentScore_current < opponentScore_max){
                            result.row_co = i;
                            result.col_co = j;
                        }
                    } else {
                        if (opponentScore_current > opponentScore_max){
                            result.row_co = i;
                            result.col_co = j;
                        }
                    }
                }
            }
        }
        if (board.oneStepToWin(result.row_co, result.col_co, stoneColour)) {
            board.placeStone(result.row_co, result.col_co, stoneColour);
            return;
        }
        // checks the opponent's score
        for (int i = 0; i < board.getNumRow(); ++i) {
            for (int j = 0; j < board.getNumCol(); ++j) {
                if (board.getStone(i, j) != 0) continue;

                if (stoneColour == 1) {
                    if (board.scoreAfter(i, j, -stoneColour) <= -4 * stoneColour) {
                        board.placeStone(i, j, stoneColour);
                        return;
                    }
                } else {
                    if (board.scoreAfter(i, j, -stoneColour) >= 4 * -stoneColour) {
                        board.placeStone(i, j, stoneColour);
                        return;
                    }
                }
            }
        }
        System.out.format("coordinate: %d %d\n", result.row_co, result.col_co);
        board.placeStone(result.row_co, result.col_co, stoneColour);
    }

    private class Tuple {
        private int row_co;
        private int col_co;
        private Tuple(int row_co, int col_co){
            this.row_co = row_co;
            this.col_co = col_co;
        }
    }
}
