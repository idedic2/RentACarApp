package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;

public class VehicleImagesController {

    public ListView<String> listViewImages;
    public ObservableList<String> listImages;
    private String path;
    public VehicleImagesController(){
        listImages = FXCollections.observableArrayList();
    }
    private void showAlert(String title, String headerText, Alert.AlertType type) {
        Alert error = new Alert(type);
        error.setTitle(title);
        error.setHeaderText(headerText);
        error.show();
    }

    public String getPath() {
        return path;
    }

    @FXML
    public void initialize() {
        File folder = new File("resources/images");
        File[] listOfFiles = folder.listFiles();
        //System.out.println(listOfFiles.length);
        for (int i = 0; i < listOfFiles.length; i++) {
            String url = listOfFiles[i].toURI().toString();
            if (!url.contains("Vehicle")) continue;
            listImages.add(url);
            //System.out.println(url);
        }
        listViewImages.setItems(listImages);
    }
    public void confirmChoosenImageAction(ActionEvent actionEvent){
        if(listViewImages.getSelectionModel().getSelectedItem()==null){
            showAlert("Greška", "Morate odabrati fotografiju", Alert.AlertType.ERROR);
            return;
        }
        path=listViewImages.getSelectionModel().getSelectedItem();
        Stage stage= (Stage) listViewImages.getScene().getWindow();
        stage.close();
    }
}