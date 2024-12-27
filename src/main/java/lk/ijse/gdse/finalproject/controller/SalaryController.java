package lk.ijse.gdse.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.finalproject.dto.SalaryDto;
import lk.ijse.gdse.finalproject.dto.tm.SalaryTM;
import lk.ijse.gdse.finalproject.model.SalaryModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SalaryController implements Initializable {
    public TableView<SalaryTM> tblSalary;
    public TableColumn<SalaryTM,String> salaryId;
    public TableColumn<SalaryTM,Double> amount;
    public TableColumn<SalaryTM,Date> payDay;
    public TableColumn<SalaryTM,Integer> holidays;
    public TableColumn<SalaryTM,String> isReceived;
    public TableColumn<SalaryTM,String> adminId;

    public Label lblSalaryId;
    public TableColumn<SalaryTM,String> stafId;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public TextField txtAmount;
    public TextField txtPayDay;
    public TextField txtReceived;
    public TextField txtStaffId;
    public TextField txtAdminId;
    public TextField txtHolidays;

    SalaryModel salaryModel = new SalaryModel();
    public void loadTableData() throws SQLException, ClassNotFoundException{
        ArrayList<SalaryDto> salaryDtos = salaryModel.getAllSalary();
        ObservableList<SalaryTM> salaryTMS = FXCollections.observableArrayList();
        for(SalaryDto salaryDto:salaryDtos){
            SalaryTM salaryTM = new SalaryTM();
            salaryTM.setSalaryId(salaryDto.getSalaryId());
            salaryTM.setAmount(salaryDto.getAmount());
            salaryTM.setPayDay(salaryDto.getPayDay());
            salaryTM.setHolidays(salaryDto.getHolidays());
            salaryTM.setIsReceived(salaryDto.getIsReceived());
            salaryTM.setAdminId(salaryDto.getAdminId());
            salaryTM.setStafId(salaryDto.getStafId());
            salaryTMS.add(salaryTM);
        }
        tblSalary.setItems(salaryTMS);
    }

    public void loadNextSalaryId() throws SQLException, ClassNotFoundException {
        String nextSalaryId = salaryModel.getNextSalaryId();
        lblSalaryId.setText(nextSalaryId);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        salaryId.setCellValueFactory(new PropertyValueFactory<>("salaryId"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        payDay.setCellValueFactory(new PropertyValueFactory<>("payDay"));
        holidays.setCellValueFactory(new PropertyValueFactory<>("holidays"));
        isReceived.setCellValueFactory(new PropertyValueFactory<>("isReceived"));
        adminId.setCellValueFactory(new PropertyValueFactory<>("adminId"));
        stafId.setCellValueFactory(new PropertyValueFactory<>("stafId"));

        try{
            refreshPage();
        }catch(Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail Salary id").show();
        }
    }

    public void saveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String salaryId = lblSalaryId.getText();
        Double amount = Double.valueOf(txtAmount.getText());
        Date payday = Date.valueOf(txtPayDay.getText());
        int holidays = Integer.parseInt(txtHolidays.getText());
        String isReceived = txtReceived.getText();
        String adminId = txtAdminId.getText();
        String staffId = txtStaffId.getText();

        SalaryDto salaryDto = new SalaryDto(
                salaryId,
                amount,
                payday,
                holidays,
                isReceived,
                adminId,
                staffId
        );
        boolean isSaved = salaryModel.saveSalary(salaryDto);
        if (isSaved) {
            loadNextSalaryId();
            txtAmount.setText("");
            txtPayDay.setText("");
            txtHolidays.setText("");
            txtReceived.setText("");
            txtAdminId.setText("");
            txtStaffId.setText("");
            new Alert(Alert.AlertType.INFORMATION, "Salary Saved").show();
            loadTableData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Save fail").show();

        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String salaryId = lblSalaryId.getText();
        Double amount = Double.valueOf(txtAmount.getText());
        Date payday = Date.valueOf(txtPayDay.getText());
        int holidays = Integer.parseInt(txtHolidays.getText());
        String isReceived = txtReceived.getText();
        String adminId = txtAdminId.getText();
        String staffId = txtStaffId.getText();

        SalaryDto salaryDto = new SalaryDto(
                salaryId,
                amount,
                payday,
                holidays,
                isReceived,
                adminId,
                staffId
        );
        boolean isUpdated = salaryModel.updateSalary(salaryDto);
        if (isUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Salary Updated").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Update fail").show();

        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String salaryId = lblSalaryId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = salaryModel.deleteSalary(salaryId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Salary deleted").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Salary...!").show();
            }
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        SalaryTM salaryTM = tblSalary.getSelectionModel().getSelectedItem();
        if (salaryTM != null) {
            lblSalaryId.setText(salaryTM.getSalaryId());
            txtAmount.setText(String.valueOf(salaryTM.getAmount()));
            txtPayDay.setText(String.valueOf(salaryTM.getPayDay()));
            txtHolidays.setText(String.valueOf(salaryTM.getHolidays()));
            txtReceived.setText(salaryTM.getIsReceived());
            txtAdminId.setText(salaryTM.getAdminId());
            txtStaffId.setText(salaryTM.getStafId());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    public void refreshOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }
    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextSalaryId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtAmount.setText("");
        txtPayDay.setText("");
        txtHolidays.setText("");
        txtReceived.setText("");
        txtAdminId.setText("");
        txtStaffId.setText("");
    }


}
