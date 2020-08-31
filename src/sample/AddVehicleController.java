package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class AddVehicleController {
    public TextField fldName;
    public TextField fldBrand;
    public TextField fldModel;
    public TextField fldColor;
    public ChoiceBox<String> choiceEngine;
    public ChoiceBox<String> choiceTransmission;
    public ChoiceBox<String> choiceType;
    public Spinner<Integer> spinnerNmbSeats;
    public Spinner<Integer> spinnerNmbDoors;
    public ComboBox<String> comboFuelConsumption;
    public ComboBox<String> comboYear;
    public ComboBox<String> comboPrice;
    public CheckBox checkAvailability;
    public String imagePath;
    public Vehicle vehicle;
    public ImageView placeholderImage;
    private boolean edit;
    private boolean sveOk=true;
    public Vehicle getVehicle() {
        return vehicle;
    }
    public AddVehicleController(Vehicle vehicle) {
        this.vehicle=vehicle;
        if(vehicle!=null)
        this.edit=true;
    }
    @FXML
    public void initialize() {
        if (vehicle != null) {
            String path="File:"+vehicle.getImage();
            System.out.println(path);
            Image image = new Image(path);
            placeholderImage.setImage(image);
            imagePath=vehicle.getImage();
           fldName.setText(vehicle.getName());
           fldBrand.setText(vehicle.getBrand());
           fldModel.setText(vehicle.getModel());
           fldColor.setText(vehicle.getColor());
           choiceEngine.getSelectionModel().select(vehicle.getEngine());
           choiceTransmission.getSelectionModel().select(vehicle.getTransmission());
           choiceType.getSelectionModel().select(vehicle.getType());
           spinnerNmbSeats.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, vehicle.getSeatsNumber()));
           spinnerNmbDoors.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, vehicle.getDoorsNumber()));
           comboFuelConsumption.getSelectionModel().select(Double.toString(vehicle.getFuelConsumption()));
           comboYear.getSelectionModel().select(Integer.toString(vehicle.getYear()));
           comboPrice.getSelectionModel().select(Double.toString(vehicle.getPricePerDay()));
           if(vehicle.getAvailability().equals("DA"))
               checkAvailability.setSelected(true);
           else checkAvailability.setSelected(false);

        } else {
            String path="/images/placeholderVehicle.png";
            System.out.println(path);
            Image image = new Image(path);
            placeholderImage.setImage(image);
            choiceEngine.getSelectionModel().selectFirst();
            choiceTransmission.getSelectionModel().selectFirst();
            choiceType.getSelectionModel().selectFirst();
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 5);
            spinnerNmbDoors.setValueFactory(valueFactory);
            SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 5);
            spinnerNmbSeats.setValueFactory(valueFactory2);
        }
        fldName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (fldName.getText().trim().isEmpty()) {
                    fldName.getStyleClass().removeAll("ispravnoPolje");
                    fldName.getStyleClass().add("neispravnoPolje");
                    sveOk = false;
                } else {
                    fldName.getStyleClass().removeAll("neispravnoPolje");
                    fldName.getStyleClass().add("ispravnoPolje");
                    sveOk = true;
                }
            }
        });
        fldBrand.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (fldBrand.getText().trim().isEmpty()) {
                    fldBrand.getStyleClass().removeAll("ispravnoPolje");
                    fldBrand.getStyleClass().add("neispravnoPolje");
                    sveOk = false;
                } else {
                    fldBrand.getStyleClass().removeAll("neispravnoPolje");
                    fldBrand.getStyleClass().add("ispravnoPolje");
                    sveOk = true;
                }
            }
        });
        fldModel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (fldModel.getText().trim().isEmpty()) {
                    fldModel.getStyleClass().removeAll("ispravnoPolje");
                    fldModel.getStyleClass().add("neispravnoPolje");
                    sveOk = false;
                } else {
                    fldModel.getStyleClass().removeAll("neispravnoPolje");
                    fldModel.getStyleClass().add("ispravnoPolje");
                    sveOk = true;
                }
            }
        });
        fldColor.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (!fldColor.getText().trim().isEmpty()) {
                    if(!allLetters(fldColor.getText())){
                        fldColor.getStyleClass().removeAll("ispravnoPolje");
                        fldColor.getStyleClass().add("neispravnoPolje");
                    }
                    else{
                        fldColor.getStyleClass().removeAll("neispravnoPolje");
                        fldColor.getStyleClass().add("ispravnoPolje");
                    }
                } else {
                    fldColor.getStyleClass().removeAll("ispravnoPolje");
                    fldColor.getStyleClass().add("neispravnoPolje");
                }
            }
        });


    }
    private boolean allLetters(String str){
        return  str.chars().allMatch(Character::isLetter);
    }
    private boolean validationForDouble(String str){
       for(int i=0;i<str.length();i++){
           if(!(str.charAt(i)=='.' || (str.charAt(i)>='0' && str.charAt(i)<='9')))
               return false;
       }
       return true;
    }
    private boolean allDigits(String str){
        return  str.chars().allMatch(Character::isDigit);
    }

    private void showAlert(String title, String headerText, Alert.AlertType type) {
        Alert error = new Alert(type);
        error.setTitle(title);
        error.setHeaderText(headerText);
        error.show();
    }
    public void addConfirmAction(ActionEvent actionEvent) {

        boolean sveOk=true;
        if(fldName.getText().trim().isEmpty()){
            sveOk=false;
            showAlert("Greška", "Unesite naziv vozila", Alert.AlertType.ERROR);
            return;
        }
        if(fldBrand.getText().trim().isEmpty()){
            sveOk=false;
            showAlert("Greška", "Unesite brend vozila", Alert.AlertType.ERROR);
            return;
        }
        if(fldModel.getText().trim().isEmpty()){
            sveOk=false;
            showAlert("Greška", "Unesite model vozila", Alert.AlertType.ERROR);
            return;
        }
        if(fldColor.getText().trim().isEmpty()){
            sveOk=false;
            showAlert("Greška", "Unesite boju vozila", Alert.AlertType.ERROR);
            return;
        }
            if(!allLetters(fldColor.getText())){
                sveOk=false;
                showAlert("Greška", "Boja mora sadržavati isključivo slova", Alert.AlertType.ERROR);
                return;
            }
            if(!allDigits(comboYear.getValue())){
                showAlert("Greška", "Godina mora sadržavati isključivo brojeve", Alert.AlertType.ERROR);
                return;
            }
        if(!validationForDouble(comboFuelConsumption.getValue())){
            showAlert("Greška", "Potrošnja mora sadržavati isključivo brojeve", Alert.AlertType.ERROR);
            return;
        }
        if(!validationForDouble(comboPrice.getValue())){
            showAlert("Greška", "Cijena mora sadržavati isključivo brojeve", Alert.AlertType.ERROR);
            return;
        }
        if(vehicle==null){
            if (imagePath.equals("")) {
                showAlert("Greška", "Morate dodati fotografiju vozila", Alert.AlertType.ERROR);
                return;
            }
        }
        if (!sveOk) return;
        if (vehicle == null) vehicle = new Vehicle();
        vehicle.setName(fldName.getText());
        vehicle.setBrand(fldBrand.getText());
        vehicle.setModel(fldModel.getText());
        vehicle.setColor(fldColor.getText());
        vehicle.setEngine(choiceEngine.getValue());
        vehicle.setTransmission(choiceTransmission.getValue());
        vehicle.setType(choiceType.getValue());
        vehicle.setImage(imagePath);
        placeholderImage.setImage(new Image("File:"+vehicle.getImage()));
        //placeholderImage.setImage(new Image(imagePath));
        System.out.println("dodavanje u vozilo" +vehicle.getImage());
        try {
            vehicle.setDoorsNumber(spinnerNmbDoors.getValue());
        }
        catch (NegativeNumberException e){
            e.printStackTrace();
        }
        try{
        vehicle.setSeatsNumber(spinnerNmbSeats.getValue());
        }
        catch (NegativeNumberException e){
            e.printStackTrace();
        }
        try{
        vehicle.setYear(Integer.parseInt(comboYear.getValue()));
        }
        catch (NegativeNumberException e){
            e.printStackTrace();
        }
        try{
        vehicle.setFuelConsumption(Double.parseDouble(comboFuelConsumption.getValue()));
        }
        catch (NegativeNumberException e){
            e.printStackTrace();
        }
        try{
        vehicle.setPricePerDay(Double.parseDouble(comboPrice.getValue()));
        }
        catch (NegativeNumberException e){
            e.printStackTrace();
        }
        if(checkAvailability.isSelected()){
            // da bi stavili dostupnost da je "da" moramo se uvjerit da to auto nije trenutno na listi rezervacija
            vehicle.setAvailability("DA");
        }
        else vehicle.setAvailability("NE");
        Stage stage = (Stage) fldName.getScene().getWindow();
        stage.close();
    }
    public void CloseAddCarAction(ActionEvent actionEvent){
        vehicle=null;
        Stage stage= (Stage) fldBrand.getScene().getWindow();
        stage.close();
    }

    public void addImageAction(ActionEvent actionEvent) {
            Stage stage = new Stage();
            Parent root = null;

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/findImage.fxml"));
                FindImageController findImageController = new FindImageController(null);
                loader.setController(findImageController);
                root = loader.load();
                stage.setTitle("Pretraga datoteka");
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.setResizable(true);
                stage.show();
                stage.setOnHiding(event -> {
                    imagePath = findImageController.getImagePath();
                    System.out.println("kod zatvaranja forme" + imagePath);
                    if (!imagePath.equals("")) {
                        Image image = new Image("File:" + imagePath);
                        placeholderImage.setImage(image);
                    }
                    //findImageController.getThread().stop();
                    findImageController.stopSearch();

                    //if (!imagePath.equals("")) {
                    //rentACarDAO.editReservation(newReservation);
                    //vehicle.setImage(imagePath);
                    //}
                });
            } catch (IOException e) {
                e.printStackTrace();
            }



    }
   /* public void slika(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/vehicleImages.fxml"));
        VehicleImagesController vehicleImagesController = new VehicleImagesController();
        loader.setController(vehicleImagesController);
        root = loader.load();
        stage.setTitle("Slike");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(true);
        stage.show();
    }
    */
   /*public void addImageAction(ActionEvent actionEvent){
       FileChooser fileChooser = new FileChooser();
       fileChooser.getExtensionFilters().addAll(
               new FileChooser.ExtensionFilter("png", "*.png")
               ,new FileChooser.ExtensionFilter("jpg", "*.jpg")
       );
       File selectedFile = fileChooser.showOpenDialog(fldBrand.getScene().getWindow());

   }*/
}
