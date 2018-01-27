package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Starts to load the Board.fxml, and pops a window
   *
   * @param primaryStage the primary stage for this application, onto which the application scene
   *     can be set. The primary stage will be embedded in the browser if the application was
   *     launched as an applet. Applications may create other stages, if needed, but they will not
   *     be primary stages and will not be embedded in the browser.
   * @throws IOException when Board.fxml cannot be loaded
   */
  @Override
  public void start(Stage primaryStage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("../view/Board.fxml"));
    Scene scene = new Scene(root);
    primaryStage.setTitle("Gomoku Game Non-AI Version");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
