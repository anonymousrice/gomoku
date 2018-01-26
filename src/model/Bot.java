package model;


import java.util.ArrayList;

class Bot{
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

    public void play(){
        ArrayList<Tuple> possible_move = new ArrayList<>();
        if (board.getNumStones() == 0){
            board.placeStone(MID_ROW, MID_COL, stoneColour);
            return;
        }
        // checks the opponent's score, aka defense mode
        for (int i = 0; i < board.getNumRow(); ++i){
            for (int j = 0; j < board.getNumCol(); ++j){
                if (board.getStone(i,j)!=0) continue;
                if (stoneColour == 1){
                    if (board.scoreAfter(i,j, -stoneColour)<=-4*stoneColour){
                        board.placeStone(i,j,stoneColour);
                        return;
                    }
                } else {
                    if (board.scoreAfter(i,j, -stoneColour)>=4*-stoneColour){
                        board.placeStone(i,j,stoneColour);
                        return;
                    }
                }
            }
        }
        // checks bot's score, aka attack mode

    }

    private class Tuple {
        private int row_co;
        private int col_co;
        private Tuple (int row_co, int col_co){
            this.row_co = row_co;
            this.col_co = col_co;
        }

        public int getRow_co() {
            return row_co;
        }

        public int getCol_co() {
            return col_co;
        }
    }
}
