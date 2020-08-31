package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

public class FindImageController{
    public TextField fldFindImage;
    public ListView listImages;
    private Vehicle vehicle;
    public ObservableList<String> obslistImages=FXCollections.observableArrayList();
    public String imagePath="";
    private Thread thread;
    private boolean continueSearch;
    public FindImageController(Vehicle vehicle) {
        //obslistImages= FXCollections.observableArrayList(new ArrayList<String>() );
        this.vehicle = vehicle;
    }

    public Thread getThread() {
        return thread;
    }

    public void stopSearch() {
        continueSearch = false;
        //thread.stop();
    }



    @FXML
    public void initialize() {
        //listImages.setItems(obslistImages);
        thread=new Thread(() -> {
            switch (Util.getOS()) {
                case WINDOWS:
                    searchDirectory(new File("C:\\Users"));
                    break;
                case LINUX:
                    searchDirectory(new File("/"));
                    break;
            }
        });
    }

    public void searchDirectory(File directory) {
        if (directory.isDirectory()) {
            search(directory);
        } else {
            System.out.println(directory.getAbsoluteFile() + " is not a directory!");
        }

    }

    private void search(File file) {
        if (!continueSearch) return;
        if (file.isDirectory() && Files.exists(file.toPath()) && !file.isHidden()) {
            System.out.println("Searching directory ... " + file.getAbsoluteFile());
            //do you have permission to read this directory?
            if (file.canRead() && !file.isHidden() ) {
                for (File temp : file.listFiles()) {
                    //System.out.println(temp.getPath());

                    System.out.println("File:" + temp.getAbsolutePath());
                    if (temp.isDirectory()) {
                        //if(temp.isHidden() || Files.notExists(temp.toPath()))continue;
                        search(temp);
                    } else {
                        if (temp.getAbsolutePath().contains(fldFindImage.getText())) {

                            Platform.runLater(() ->
                                    {obslistImages.add(temp.getAbsoluteFile().toString());
                                    listImages.setItems(obslistImages);}
                            );
                        }
                    }
                }

            } else {
                System.out.println(file.getAbsoluteFile() + "Permission Denied");
            }
        }

    }
    private void showAlert(String title, String headerText, Alert.AlertType type) {
        Alert error = new Alert(type);
        error.setTitle(title);
        error.setHeaderText(headerText);
        error.show();
    }

        public void findImageAction (ActionEvent actionEvent) {
            //try different directory and filename :)
            continueSearch = true;
            thread.start();
            //thread.setDaemon(true);
        }
    public void confirmImageAction(ActionEvent actionEvent) {
        boolean sveOk = true;
        if (fldFindImage.getText().trim().isEmpty()) {
            sveOk = false;
            showAlert("Greška", "Unesite pojam za pretraživanje", Alert.AlertType.ERROR);
            return;
        }
        if (listImages.getSelectionModel().getSelectedItem() == null) {
            sveOk = false;
            showAlert("Greška", "Morate odabrati fotografiju vozila", Alert.AlertType.ERROR);
            return;
        }
        if (sveOk) {
            String imagePath = listImages.getSelectionModel().getSelectedItem().toString();
            if (imagePath.substring(imagePath.length() - 4).equals(".png") || imagePath.substring(imagePath.length() - 4).equals(".jpg")  || imagePath.substring(imagePath.length() - 5).equals(".jpeg"))
                this.imagePath = imagePath;
            else{
                showAlert("Greška", "Niste odabrali ispravan format", Alert.AlertType.ERROR);
                return;
            }
            //System.out.println(imagePath);
        }
        Stage stage2 = (Stage) fldFindImage.getScene().getWindow();
        stage2.close();
    }

    public String getImagePath() {
        return imagePath;
    }
    public void closeFIndImageAction(ActionEvent actionEvent){
        Stage stage2 = (Stage) fldFindImage.getScene().getWindow();
        stage2.close();
    }

}

