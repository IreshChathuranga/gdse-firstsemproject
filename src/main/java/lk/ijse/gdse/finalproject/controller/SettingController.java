package lk.ijse.gdse.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lk.ijse.gdse.finalproject.dto.SettingDto;
import lk.ijse.gdse.finalproject.model.SettingModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
    public Button btnEdit;
    public TextField txtName;
    public TextField txtUserName;
    public TextField txtContactNumber;
    public TextField txtAddress;
    public TextField txtPassword;

    SettingModel settingModel = new SettingModel();
    public void editOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String name = txtName.getText();
        String username = txtUserName.getText();
        int contactNumber = Integer.parseInt(txtContactNumber.getText());
        String userAddress = txtAddress.getText();
        String userPassword = txtPassword.getText();

        SettingDto settingDto = new SettingDto(
                name,
                username,
                contactNumber,
                userAddress,
                userPassword
        );

        boolean isEdited = settingModel.editSignup(settingDto);
        if(isEdited){
            new Alert(Alert.AlertType.INFORMATION, "Singup edit").show();
        }else{
            new Alert(Alert.AlertType.ERROR, "Edit fail").show();
        }
    }
    private void loadSignupData() throws SQLException, ClassNotFoundException {
        SettingDto settingDto = settingModel.getSignupDetails();
        if (settingDto != null) {
            txtName.setText(settingDto.getName());
            txtUserName.setText(settingDto.getUserName());
            txtContactNumber.setText(String.valueOf(settingDto.getContactNumber()));
            txtAddress.setText(settingDto.getAddress());
            txtPassword.setText(settingDto.getPassword());
        } else {
            System.out.println("No signup data found.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadSignupData();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
