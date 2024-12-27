package lk.ijse.gdse.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.finalproject.dto.PaymentPlanDto;
import lk.ijse.gdse.finalproject.dto.tm.PaymentPlanTM;
import lk.ijse.gdse.finalproject.model.PaymentPlanModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class PaymentPlanController implements Initializable {
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public TableView<PaymentPlanTM> tblPaymentPlan;
    public TableColumn<PaymentPlanTM,String> payplanId;
    public TableColumn<PaymentPlanTM,String> payId;
    public TableColumn<PaymentPlanTM,Double> amount;
    public TableColumn<PaymentPlanTM,Integer> rate;
    public TableColumn<PaymentPlanTM,Double> ratePrice;
    public TableColumn<PaymentPlanTM,String> description;
    public Label lblPaymentPlanId;
    public TextField txtRatePrice;
    public TextField txtDescription;
    public TextField txtRate;
    public TextField txtAmount;
    public TextField txtPayId;
    public Button btnReceipt;
    public ComboBox<String> cmbPayId;
    PaymentPlanModel paymentPlanModel= new PaymentPlanModel();
    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<PaymentPlanDto> paymentPlanDtos = paymentPlanModel.getAllPaymentPlan();
        ObservableList<PaymentPlanTM> paymentPlanTMS = FXCollections.observableArrayList();
        for(PaymentPlanDto paymentPlanDto:paymentPlanDtos){
            PaymentPlanTM paymentPlanTM=new PaymentPlanTM();
            paymentPlanTM.setPayplanId(paymentPlanDto.getPayplanId());
            paymentPlanTM.setAmount(paymentPlanDto.getAmount());
            paymentPlanTM.setRate(paymentPlanDto.getRate());
            paymentPlanTM.setRatePrice(paymentPlanDto.getRatePrice());
            paymentPlanTM.setDescription(paymentPlanDto.getDescription());
            paymentPlanTM.setPayId(paymentPlanDto.getPayId());
            paymentPlanTMS.add(paymentPlanTM);
        }
        tblPaymentPlan.setItems(paymentPlanTMS);
    }

    public void loadNextPaymentPlanId() throws SQLException, ClassNotFoundException {
        String nextPaymentPalnId = paymentPlanModel.getNextPaymentPlanId();
        lblPaymentPlanId.setText(nextPaymentPalnId);
    }
    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextPaymentPlanId();
        loadPayId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtAmount.setText("");
        txtRate.setText("");
        txtRatePrice.setText("");
        txtDescription.setText("");
        cmbPayId.getSelectionModel().clearSelection();
    }
    public void saveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String payPlanId = lblPaymentPlanId.getText();
        Double amount = Double.valueOf(txtAmount.getText());
        int rate = Integer.parseInt(txtRate.getText());
        Double ratePrice = Double.valueOf(txtRatePrice.getText());
        String description = txtDescription.getText();
        String payId = cmbPayId.getValue();


        PaymentPlanDto paymentPlanDto = new PaymentPlanDto(
                payPlanId,
                amount,
                rate,
                ratePrice,
                description,
                payId
        );
        boolean isSaved = paymentPlanModel.savePaymentPlan(paymentPlanDto);
        if (isSaved) {
            loadNextPaymentPlanId();
            txtAmount.setText("");
            txtRate.setText("");
            txtRatePrice.setText("");
            txtDescription.setText("");
            cmbPayId.getSelectionModel().clearSelection();
            new Alert(Alert.AlertType.INFORMATION, "PaymentPlan Saved").show();
            loadTableData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Save fail").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String payPlanId = lblPaymentPlanId.getText();
        Double amount = Double.valueOf(txtAmount.getText());
        int rate = Integer.parseInt(txtRate.getText());
        Double ratePrice = Double.valueOf(txtRatePrice.getText());
        String description = txtDescription.getText();
        String payId = cmbPayId.getValue();


        PaymentPlanDto paymentPlanDto = new PaymentPlanDto(
                payPlanId,
                amount,
                rate,
                ratePrice,
                description,
                payId
        );
        boolean isUpdated = paymentPlanModel.updatePaymentPlan(paymentPlanDto);
        if (isUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "PaymentPlan Updated").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Update fail").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String payPlanId = lblPaymentPlanId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = paymentPlanModel.deletePaymentPlan(payPlanId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "PaymentPlan deleted").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete paymentPlan...!").show();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        payplanId.setCellValueFactory(new PropertyValueFactory<>("payplanId"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        rate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        ratePrice.setCellValueFactory(new PropertyValueFactory<>("ratePrice"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        payId.setCellValueFactory(new PropertyValueFactory<>("payId"));

        try{
            refreshPage();
        }catch(Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail Paymentplan id").show();
        }
    }

    public void refreshOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    public void onclickTable(MouseEvent mouseEvent) {
        PaymentPlanTM paymentPlanTM = tblPaymentPlan.getSelectionModel().getSelectedItem();
        if (paymentPlanTM != null) {
            lblPaymentPlanId.setText(paymentPlanTM.getPayplanId());
            cmbPayId.getSelectionModel().select(paymentPlanTM.getPayId());
            txtAmount.setText(String.valueOf(paymentPlanTM.getAmount()));
            txtRate.setText(String.valueOf(paymentPlanTM.getRate()));
            txtRatePrice.setText(String.valueOf(paymentPlanTM.getRatePrice()));
            txtDescription.setText(paymentPlanTM.getDescription());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    private void loadPayId() throws SQLException, ClassNotFoundException {
        ArrayList<String> paymentId = paymentPlanModel.getAllPayId();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(paymentId);
        cmbPayId.setItems(observableList);
    }
}
