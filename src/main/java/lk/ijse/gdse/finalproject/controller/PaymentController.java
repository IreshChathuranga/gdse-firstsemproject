package lk.ijse.gdse.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.finalproject.db.DBConnection;
import lk.ijse.gdse.finalproject.dto.PaymentDto;
import lk.ijse.gdse.finalproject.dto.tm.PaymentTM;
import lk.ijse.gdse.finalproject.model.PaymentModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

public class PaymentController implements Initializable {
    public Button btnPaymentPlan;
    public AnchorPane paymentMain;
    public TableView<PaymentTM> tblPayment;
    public TableColumn<PaymentTM,String> payId;
    public TableColumn<PaymentTM, Date> payDate;
    public TableColumn<PaymentTM,String> payMethod;
    public TableColumn<PaymentTM,String> adminId;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public Label lblPayId;
    public TextField txtPayMethod;
    public TextField txtDate;
    public TextField txtAdminId;
    public Button btnReceipt;

    PaymentModel paymentModel = new PaymentModel();
    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<PaymentDto> paymentDtos = paymentModel.getAllPayment();
        ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();
        for(PaymentDto paymentDto:paymentDtos){
            PaymentTM paymentTM=new PaymentTM();
            paymentTM.setPayId(paymentDto.getPayId());
            paymentTM.setPayDate(paymentDto.getPayDate());
            paymentTM.setPayMethod(paymentDto.getPayMethod());
            paymentTM.setAdminId(paymentDto.getAdminId());
            paymentTMS.add(paymentTM);
        }
        tblPayment.setItems(paymentTMS);
    }

    public void navigateTo(String fxmlpath) throws IOException {
        paymentMain.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlpath));
        paymentMain.getChildren().add(load);
    }

    public void saveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String payId = lblPayId.getText();
        Date payDate = Date.valueOf(txtDate.getText());
        String payMethod = txtPayMethod.getText();
        String adminId = txtAdminId.getText();

        PaymentDto paymentDto = new PaymentDto(
                payId,
                payDate,
                payMethod,
                adminId
        );
        boolean isSaved = paymentModel.savePayment(paymentDto);
        if (isSaved) {
            loadNextPaymentId();
            txtDate.setText("");
            txtPayMethod.setText("");
            txtAdminId.setText("");
            new Alert(Alert.AlertType.INFORMATION, "Payment Saved").show();
            loadTableData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Save fail").show();

        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String payId = lblPayId.getText();
        Date payDate = Date.valueOf(txtDate.getText());
        String payMethod = txtPayMethod.getText();
        String adminId = txtAdminId.getText();

        PaymentDto paymentDto = new PaymentDto(
                payId,
                payDate,
                payMethod,
                adminId
        );
        boolean isUpdated = paymentModel.updatePayment(paymentDto);
        if (isUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Payment Updated").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Update fail").show();

        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String payId = lblPayId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = paymentModel.deletePayment(payId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Payment deleted").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete payment...!").show();
            }
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        PaymentTM paymentTM = tblPayment.getSelectionModel().getSelectedItem();
        if (paymentTM != null) {
            lblPayId.setText(paymentTM.getPayId());
            txtDate.setText(String.valueOf(paymentTM.getPayDate()));
            txtPayMethod.setText(paymentTM.getPayMethod());
            txtAdminId.setText(paymentTM.getAdminId());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    public void loadNextPaymentId() throws SQLException, ClassNotFoundException {
        String nextPaymentId = paymentModel.getNextPaymentId();
        lblPayId.setText(nextPaymentId);
    }
    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextPaymentId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtDate.setText("");
        txtPayMethod.setText("");
        txtAdminId.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        payId.setCellValueFactory(new PropertyValueFactory<>("payId"));
        payDate.setCellValueFactory(new PropertyValueFactory<>("payDate"));
        payMethod.setCellValueFactory(new PropertyValueFactory<>("payMethod"));
        adminId.setCellValueFactory(new PropertyValueFactory<>("adminId"));

        try{
            refreshPage();
        }catch(Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail Payment id").show();
        }
    }

    public void refreshOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    public void paymentPlanOnAction(ActionEvent actionEvent) throws IOException {
        navigateTo("/view/PaymentPlan.fxml");
    }

    public void receiptOnAction(ActionEvent actionEvent) {
        PaymentTM paymentTM = tblPayment.getSelectionModel().getSelectedItem();

        if(paymentTM==null){
            return;
        }

        try{
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream("/reports/PaymentReceipt.jrxml"));

            Connection connection= DBConnection.getInstance().getConnection();

            Map<String,Object> parameters = new HashMap<>();
            parameters.put("P_Pay_Id", paymentTM.getPayId());
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );

            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR,"Fail to generate receipt").show();
            e.printStackTrace();
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,"DB error").show();
        }
    }
}
