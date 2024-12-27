package lk.ijse.gdse.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.finalproject.dto.VehicleDto;
import lk.ijse.gdse.finalproject.dto.tm.VehicleTM;
import lk.ijse.gdse.finalproject.model.VehicleModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class VehicleController implements Initializable {
    public TextField txtLesson;
    public TableView<VehicleTM> tblVehicel;
    public TableColumn<VehicleTM,String> vehicleId;
    public TableColumn<VehicleTM,String> vehicleType;
    public TableColumn<VehicleTM,Double> lessonFee;
    public TableColumn<VehicleTM,String> adminId;
    public TextField txtAdmin;


    public Label lblVehicelId;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public ComboBox<String> cmbType;

    VehicleModel vehicleModel = new VehicleModel();
    public void loadTableData() throws SQLException, ClassNotFoundException{
        ArrayList<VehicleDto> vehicleDtos = vehicleModel.getAllVehicles();
        ObservableList<VehicleTM> vehicleTMS = FXCollections.observableArrayList();
        for(VehicleDto vehicleDto:vehicleDtos){
            VehicleTM vehicleTM = new VehicleTM();
            vehicleTM.setVehicleId(vehicleDto.getVehicleId());
            vehicleTM.setVehicleType(vehicleDto.getVehicleType());
            vehicleTM.setLessonFee(vehicleDto.getLessonFee());
            vehicleTM.setAdminId(vehicleDto.getAdminId());
            vehicleTMS.add(vehicleTM);
        }
        tblVehicel.setItems(vehicleTMS);
    }

    public void saveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String vehicleId = lblVehicelId.getText();
        String vehicleType = cmbType.getValue();
        Double lessonFee = Double.valueOf(txtLesson.getText());
        String adminId = txtAdmin.getText();

        VehicleDto vehicleDto = new VehicleDto(
                vehicleId,
                vehicleType,
                lessonFee,
                adminId
        );

        boolean isSave = vehicleModel.saveVehicle(vehicleDto);
        if(isSave){
            loadNextVehicelId();
            cmbType.getSelectionModel().clearSelection();
            txtLesson.setText("");
            txtAdmin.setText("");
            new Alert(Alert.AlertType.INFORMATION, "Vehicle Saved").show();
            loadTableData();
        }else{
            new Alert(Alert.AlertType.ERROR, "Save fail").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String vehicleId = lblVehicelId.getText();
        String vehicleType = cmbType.getValue();
        Double lessonFee = Double.valueOf(txtLesson.getText());
        String adminId = txtAdmin.getText();

        VehicleDto vehicleDto = new VehicleDto(
                vehicleId,
                vehicleType,
                lessonFee,
                adminId
        );

        boolean isUpdated = vehicleModel.updateVehicle(vehicleDto);
        if(isUpdated){
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Vehicle Updated").show();
        }else{
            new Alert(Alert.AlertType.ERROR, "Update fail").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String vehicleId = lblVehicelId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if(optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES){
            boolean isDeleted = vehicleModel.deleteVehicle(vehicleId);
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Vehicle deleted").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to delete vehicle...!").show();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        VehicleTM vehicleTM = tblVehicel.getSelectionModel().getSelectedItem();
        if(vehicleTM != null){
            lblVehicelId.setText(vehicleTM.getVehicleId());
            cmbType.getSelectionModel().select(vehicleTM.getVehicleType());
            txtLesson.setText(String.valueOf(vehicleTM.getLessonFee()));
            txtAdmin.setText(vehicleTM.getAdminId());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    public void refreshOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    public void loadNextVehicelId() throws SQLException, ClassNotFoundException {
        String nextVehicelId = vehicleModel.getNextVehicleId();
        lblVehicelId.setText(nextVehicelId);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        vehicleType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        lessonFee.setCellValueFactory(new PropertyValueFactory<>("lessonFee"));
        adminId.setCellValueFactory(new PropertyValueFactory<>("adminId"));

        try{
            refreshPage();
        }catch(Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail Vehicle id").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextVehicelId();
        loadVehicleType();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        cmbType.getSelectionModel().clearSelection();
        txtLesson.setText("");
        txtAdmin.setText("");
    }

    private void loadVehicleType() throws SQLException, ClassNotFoundException {
        ArrayList<String> vehicleType = vehicleModel.getAllVehicleType();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(vehicleType);
        cmbType.setItems(observableList);
    }
}
