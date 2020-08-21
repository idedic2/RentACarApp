package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.File;

public class VehicleImagesController {
    public GridPane grid;

    @FXML
    public void initialize(){
        File folder = new File("resources/images");
        File[] listOfFiles = folder.listFiles();
        int x = 0, y = 0;

        for (int i = 0; i < listOfFiles.length; i++) {
                Image image = new Image(listOfFiles[i].toURI().toString());
                //Image image = new Image("file:/C:/Users/Windows%2010/IdeaProjects/projekat/resources/images/admin.png");
                System.out.println(listOfFiles[i].toURI().toString());
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(300);
                imageView.setFitWidth(300);
                //grid.add(imageView,0,0);
                HBox hBox=new HBox(imageView);
                grid.add(imageView, x, y);

                if (x % 3 == 0) {
                    y++;
                    x=0;
                }

        }
    }
}
