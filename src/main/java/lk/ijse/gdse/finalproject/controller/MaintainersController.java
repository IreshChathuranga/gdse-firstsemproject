package lk.ijse.gdse.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.finalproject.dto.MaintainersDto;
import lk.ijse.gdse.finalproject.dto.tm.MaintainersTM;
import lk.ijse.gdse.finalproject.model.MaintainersModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MaintainersController implements Initializable {
    public TextField txtName;
    public TextField txtTask;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public TableView<MaintainersTM> tblMaintainer;
    public TextField txtNumber;
    public Label lblMaintainId;
    public Button btnRefresh;
    public TableColumn<MaintainersTM,String> maintainId;
    public TableColumn<MaintainersTM,String> maintainName;
    public TableColumn<MaintainersTM,String> maintainTask;
    public TableColumn<MaintainersTM,Integer> contactNumber;

    MaintainersModel maintainersModel = new MaintainersModel();

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<MaintainersDto> maintainersDtos = maintainersModel.getAllMaintainer();
        ObservableList<MaintainersTM> maintainersTMS = FXCollections.observableArrayList();
        for(MaintainersDto maintainersDto:maintainersDtos){
            MaintainersTM maintainersTM=new MaintainersTM();
            maintainersTM.setMaintainId(maintainersDto.getMaintainId());
            maintainersTM.setMaintainName(maintainersDto.getMaintainName());
            maintainersTM.setMaintainTask(maintainersDto.getMaintainTask());
            maintainersTM.setContactNumber(maintainersDto.getContactNumber());
            maintainersTMS.add(maintainersTM);
        }
        tblMaintainer.setItems(maintainersTMS);
    }

    public void onClickTable(MouseEvent mouseEvent) {
        MaintainersTM maintainersTM = tblMaintainer.getSelectionModel().getSelectedItem();
        if(maintainersTM != null){
            lblMaintainId.setText(maintainersTM.getMaintainId());
            txtName.setText(String.valueOf(maintainersTM.getMaintainName()));
            txtTask.setText(maintainersTM.getMaintainTask());
            txtNumber.setText(String.valueOf(maintainersTM.getContactNumber()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String maintainId = lblMaintainId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if(optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES){
            boolean isDeleted = maintainersModel.deleteMaintainer(maintainId);
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Maintainer deleted").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to delete maintainer...!").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String maintainId = lblMaintainId.getText();
        String maintainName = txtName.getText();
        String maintainTask = txtTask.getText();
        int contactNumber = Integer.parseInt(txtNumber.getText());

        MaintainersDto maintainersDto = new MaintainersDto(
                maintainId,
                maintainName,
                maintainTask,
                contactNumber
        );

        boolean isUpdate = maintainersModel.updateMaintainer(maintainersDto);
        if(isUpdate){
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Maintainer Updated").show();
        }else{
            new Alert(Alert.AlertType.ERROR, "Update fail").show();
        }
    }

    public void saveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String maintainId = lblMaintainId.getText();
        String maintainName = txtName.getText();
        String maintainTask = txtTask.getText();
        int contactNumber = Integer.parseInt(txtNumber.getText());

        MaintainersDto maintainersDto = new MaintainersDto(
                maintainId,
                maintainName,
                maintainTask,
                contactNumber
        );

        boolean isSave = maintainersModel.saveMaintainer(maintainersDto);
        if(isSave){
            loadNextMaintainId();
            txtName.setText("");
            txtTask.setText("");
            txtNumber.setText("");
            new Alert(Alert.AlertType.INFORMATION, "Maintainer Saved").show();
            loadTableData();
        }else{
            new Alert(Alert.AlertType.ERROR, "Save fail").show();
        }
    }

    public void refershOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }
    public void loadNextMaintainId() throws SQLException, ClassNotFoundException {
        String nextMaintainId = maintainersModel.getNextMaintainerId();
        lblMaintainId.setText(nextMaintainId);
    }
    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextMaintainId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.setText("");
        txtTask.setText("");
        txtNumber.setText("");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        maintainId.setCellValueFactory(new PropertyValueFactory<>("maintainId"));
        maintainName.setCellValueFactory(new PropertyValueFactory<>("maintainName"));
        maintainTask.setCellValueFactory(new PropertyValueFactory<>("maintainTask"));
        contactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));

        try{
            refreshPage();
        }catch(Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail Maintainer id").show();
        }
    }
}
