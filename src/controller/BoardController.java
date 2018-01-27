package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class BoardController implements Initializable{
    @FXML
    private Pane pane;

    @FXML
    private ImageView board;

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        File file = new File ("./src/images/board.jpg");
        Image image = new Image(file.toURI().toString());
        board.setImage(image);

        VBox vb = new VBox();
        for (int row = 0; row < 15; ++row){
            HBox hb = new HBox();
            for (int col = 0; col < 15; ++col){
                File stone = new File("./src/images/black_stone.jpg");
                Image stoneImage = new Image(stone.toURI().toString());
                ImageView iv = new ImageView(stoneImage);
                iv.setFitWidth(43);
                iv.setFitHeight(43);
                iv.setPreserveRatio(true);
                iv.setOpacity(0);
                iv.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    iv.setOpacity(1.0);
                });
                hb.getChildren().add(iv);
            }
            vb.getChildren().add(hb);
        }
        pane.getChildren().add(vb);
    }


}
