package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Game;

import java.io.IOException;

import static javafx.stage.StageStyle.TRANSPARENT;

public class Main extends Application{

  @Override
  public void start (Stage primaryStage) throws IOException{
    Parent root = FXMLLoader.load(getClass().getResource("../view/Board.fxml"));
    Scene scene = new Scene(root);
    primaryStage.setTitle("Gomoku Game Non-AI Version");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
    Game game = new Game();
    game.play();
  }
}
