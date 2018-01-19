/**
 * Created by IntelliJ IDEA.
 * User: Zonggen
 * Date: 1/3/2018
 * Time: 12:51 PM
 */
public class Bot implements GameStrategy {
    private static final String MODE = "FREESTYLE";
    private static final int MID_ROW = Game.getNumRow()/2;
    private static final int MID_COL = Game.getNumColumn()/2;
    private Board board;

    public Bot() {
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public void ifNone(){
        board.placeStone(MID_ROW, MID_COL);
    }

    @Override
    public void ifOne(){

    }

    @Override
    public void ifTwo(){

    }

    @Override
    public void ifThree(){

    }

    @Override
    public void ifFour(){

    }

    public void play (){

    }
}
