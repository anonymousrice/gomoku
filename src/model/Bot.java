package model;


class Bot{
    private static final String MODE = "FREESTYLE";
    private static final int MID_ROW = Game.getNumRow() / 2;
    private static final int MID_COL = Game.getNumColumn() / 2;
    private Board board;
    private int stone_colour = 0;

    public Bot(int stone_colour, Board board) {
        this.stone_colour = stone_colour;
        this.board = board;
    }

    public int getStone_colour() {
        return stone_colour;
    }

    public void play(){
        if (board.getNum_stones() == 0){
            board.placeStone(MID_ROW, MID_COL, stone_colour);
            return;
        }

    }
}
