package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
  /** A variable to store HBox inside VBox */
  private HBox[] hbArr;
  /** The current game, player, and bot objects */
  private Game game;

  private Player player;
  private Bot bot;

  @FXML private Pane pane;

  @FXML private ImageView board;

  /**
   * Initializes the game, by creating game object and builds the interface for the game
   *
   * @param location The location used to resolve relative paths for the root object, or null if the
   *     location is not known.
   * @param resource The resources used to localize the root object, or null if the root object was
   *     not localized.
   */
  @Override
  public void initialize(URL location, ResourceBundle resource) {
    game = new Game();
    game.play();
    player = game.getPlayer();
    bot = game.getBot();

    hbArr = new HBox[15];
    Image image = new Image(getClass().getResource("/images/board.jpg").toString());
    board.setImage(image);

    VBox vb = new VBox();
    for (int row = 0; row < 15; ++row) {
      HBox hb = new HBox();
      hbArr[row] = hb;
      for (int col = 0; col < 15; ++col) {
        final int row_temp = row;
        final int col_temp = col;

        Image stoneImage = new Image(getClass().getResource("/images/black_stone.jpg").toString());
        ImageView iv = new ImageView(stoneImage);
        iv.setFitWidth(43);
        iv.setFitHeight(43);
        iv.setPreserveRatio(true);
        iv.setOpacity(0);
        iv.addEventHandler(
            MouseEvent.MOUSE_CLICKED,
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
      hbArr[tuple.row_co].getChildren().set(tuple.col_co, biv);
    }
  }

  /**
   * Sets the stone on the board Note that as long as this method runs, it is the human's turn to
   * play.
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
    iv.setFitWidth(43);
    iv.setFitHeight(43);
    iv.setPreserveRatio(true);
    hbArr[row].getChildren().set(col, iv);
    if (Game.ifWin(game.getBoardObj())) {
      System.out.println("You Win!");
      return;
    } else if (game.isFull()) {
      System.out.println("Board Full..");
      return;
    }

    // bot place the stone..
    Tuple tuple = bot.play();
    Image botStoneImage = new Image(bot.getStoneColour() == 1
            ? getClass().getResource("/images/black_stone.jpg").toString()
            : getClass().getResource("/images/white_stone.jpg").toString());
    ImageView biv = new ImageView(botStoneImage);
    biv.setFitWidth(43);
    biv.setFitHeight(43);
    biv.setPreserveRatio(true);
    hbArr[tuple.row_co].getChildren().set(tuple.col_co, biv);
    if (Game.ifWin(game.getBoardObj())) {
      System.out.println("Bot Wins!");
    } else if (game.isFull()) {
      System.out.println("Board Full..");
    }
  }

  /** A button action that will make the game begin */
  @FXML
  private void handleStart() {}
}
