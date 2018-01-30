package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Bot;
import model.Game;
import model.Player;
import model.Tuple;

import java.net.URL;
import java.util.ResourceBundle;

public class BoardController implements Initializable {
  private static final int NUM_ROW = 15;
  private static final int NUM_COL = 15;
  private static final int PX_STONE = 43;
  
  /** Whether the program is first time running. */
  private boolean ifFirst;
  /** A variable to store HBox inside VBox */
  private HBox[] hbArr;
  /** The current game, player, and bot objects */
  private Game game;
  private Player player;
  private Bot bot;

  @FXML private Pane pane;

  @FXML private ImageView board;

  @FXML private TextArea textArea;

  /**
   * Set the board image as background
   *
   * @param location The location used to resolve relative paths for the root object, or null if the
   *     location is not known.
   * @param resource The resources used to localize the root object, or null if the root object was
   *     not localized.
   */
  @Override
  public void initialize(URL location, ResourceBundle resource) {
    ifFirst = true;
    textArea.appendText("Welcome!\nPress Start to Begin...\n");
    Image image = new Image(getClass().getResource("/images/board.jpg").toString());
    board.setImage(image);
  }

  /**
   * Sets the stone on the board
   * when the player click
   *
   * @param row row number
   * @param col column number
   */
  private void clickAction(int row, int col) {

    // player place the stone..
    player.putStone(row, col, player.getStoneColour());
    Image stoneImage = new Image(player.getStoneColour() == 1
            ? getClass().getResource("/images/black_stone.jpg").toString()
            : getClass().getResource("/images/white_stone.jpg").toString());
    ImageView iv = new ImageView(stoneImage);
    iv.setFitWidth(PX_STONE);
    iv.setFitHeight(PX_STONE);
    iv.setPreserveRatio(true);
    hbArr[row].getChildren().set(col, iv);
    if (Game.ifWin(game.getBoardObj())) {
      textArea.appendText("You Win!\n");
      removeMouseClickedEvent();
      return;
    } else if (game.isFull()) {
      textArea.appendText("Board Full..\n");
      removeMouseClickedEvent();
      return;
    }

    // bot place the stone..
    Tuple tuple = bot.play();
    Image botStoneImage = new Image(bot.getStoneColour() == 1
            ? getClass().getResource("/images/black_stone.jpg").toString()
            : getClass().getResource("/images/white_stone.jpg").toString());
    ImageView biv = new ImageView(botStoneImage);
    biv.setFitWidth(PX_STONE);
    biv.setFitHeight(PX_STONE);
    biv.setPreserveRatio(true);
    hbArr[tuple.row_co].getChildren().set(tuple.col_co, biv);
    if (Game.ifWin(game.getBoardObj())) {
      textArea.appendText("Bot Wins!\n");
      removeMouseClickedEvent();
    } else if (game.isFull()) {
      textArea.appendText("Board Full..\n");
      removeMouseClickedEvent();
    }
  }

  /**
   * Starts the game by initializing the Game, Player, and Bot objects
   * sets the proper on-click action on the board
   */
  @FXML
  private void handleStart() {
    game = new Game();
    game.play(this);
    player = game.getPlayer();
    bot = game.getBot();
    if (!ifFirst){
      for (int i = 0; i < NUM_ROW; ++i){
        for (int j = 0; j < NUM_COL; ++j){
          hbArr[i].getChildren().get(j).setOpacity(0);
        }
      }
    }

    hbArr = new HBox[NUM_COL];
    VBox vb = new VBox();
    for (int row = 0; row < NUM_ROW; ++row) {
      HBox hb = new HBox();
      hbArr[row] = hb;
      for (int col = 0; col < NUM_COL; ++col) {
        final int row_temp = row;
        final int col_temp = col;

        Image stoneImage = new Image(getClass().getResource("/images/black_stone.jpg").toString());
        ImageView iv = new ImageView(stoneImage);
        iv.setFitWidth(PX_STONE);
        iv.setFitHeight(PX_STONE);
        iv.setPreserveRatio(true);
        iv.setOpacity(0);
        iv.setOnMouseClicked(
                event -> {
                  clickAction(row_temp, col_temp);
                  iv.setOpacity(1.0);
                });
        hb.getChildren().add(iv);
      }
      vb.getChildren().add(hb);
    }
    pane.getChildren().add(vb);

    if (!game.isPlayerFirst()) {
      Tuple tuple = bot.play();
      Image botStoneImage = new Image(bot.getStoneColour() == 1
              ? getClass().getResource("/images/black_stone.jpg").toString()
              : getClass().getResource("/images/white_stone.jpg").toString());
      ImageView biv = new ImageView(botStoneImage);
      biv.setFitWidth(PX_STONE);
      biv.setFitHeight(PX_STONE);
      biv.setPreserveRatio(true);
      hbArr[tuple.row_co].getChildren().set(tuple.col_co, biv);
    }
    ifFirst = false;
  }

  /**
   * Appends a String text to the textArea
   * @param text String to be appended to textArea
   */
  public void appendText(String text){
    textArea.appendText(text);
  }

  private void removeMouseClickedEvent (){
    for (int i = 0; i < NUM_ROW; ++i){
      for (int j = 0; j < NUM_COL; ++j){
        hbArr[i].getChildren().get(j).setOnMouseClicked(null);
      }
    }
  }
}
