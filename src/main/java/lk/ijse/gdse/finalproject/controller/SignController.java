package lk.ijse.gdse.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lk.ijse.gdse.finalproject.dto.SigninDto;
import lk.ijse.gdse.finalproject.model.SigninModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SignController implements Initializable {
    @FXML
    private Button btnSignup;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private TextField txtAddress;


    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNumber;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        navigateTo("/view/LogIn.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void navigateTo(String fxmlpath) throws IOException {
        mainAnchor.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlpath));
        mainAnchor.getChildren().add(load);
    }
    SigninModel signinModel = new SigninModel();

    public void signinOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        String name=txtName.getText();
        String userName=txtUserName.getText();
        int contactNumber= Integer.parseInt(txtNumber.getText());
        String userAddress=txtAddress.getText();
        String userPassword=txtPassword.getText();

        SigninDto signinDto= new SigninDto(
                name,
                userName,
                contactNumber,
                userAddress,
                userPassword
        );

        boolean isSaved=signinModel.saveAdmin(signinDto);
        if(isSaved){
            txtName.setText("");
            txtUserName.setText("");
            txtNumber.setText("");
            txtAddress.setText("");
            txtPassword.setText("");
        }
        navigateTo("/view/LogIn.fxml");
    }

    public void adminOnKeyReleased(KeyEvent keyEvent) {
        Pattern userPattern = Pattern.compile("^[A-Za-z ]+$");
        if (!userPattern.matcher(txtName.getText()).matches()) {
            addError(txtName);
            btnSignup.setDisable(true);
        }else{
            removeError(txtName);
            btnSignup.setDisable(false);
        }
    }

    public void numberOnKeyReleased(KeyEvent keyEvent) {
        Pattern userPattern = Pattern.compile("^\\d{10}$");
        if (!userPattern.matcher(txtNumber.getText()).matches()) {
            addError(txtNumber);
            btnSignup.setDisable(true);
        }else{
            removeError(txtNumber);
            btnSignup.setDisable(false);
        }
    }

    public void addressOnKeyReleased(KeyEvent keyEvent) {
        Pattern userPattern = Pattern.compile("^[A-Za-z0-9\\s,.\\-\\/]{5,100}$");
        if (!userPattern.matcher(txtAddress.getText()).matches()) {
            addError(txtAddress);
            btnSignup.setDisable(true);
        }else{
            removeError(txtAddress);
            btnSignup.setDisable(false);
        }
    }

    public void uerNameOnKeyReleased(KeyEvent keyEvent) {
        Pattern userPattern = Pattern.compile("^[A-Za-z][A-Za-z0-9._]{2,15}(?<![_.])$");
        if (!userPattern.matcher(txtUserName.getText()).matches()) {
            addError(txtUserName);
            btnSignup.setDisable(true);
        }else{
            removeError(txtUserName);
            btnSignup.setDisable(false);
        }
    }

    public void passwordOnKeyReleased(KeyEvent keyEvent) {
        Pattern userPattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!])[A-Za-z\\d@#$%^&+=!]{8,20}$");


        if (!userPattern.matcher(txtPassword.getText()).matches()) {
            addError(txtPassword);
            btnSignup.setDisable(true);
        }else{
            removeError(txtPassword);
            btnSignup.setDisable(false);
        }
    }
    private void addError(TextField textField) {
        textField.setStyle("-fx-border-color: red;  -fx-border-radius: 40; -fx-background-color: #7E6174; -fx-background-radius: 40 ");
    }
    private void removeError(TextField textField) {
        textField.setStyle("-fx-border-color:  #7E6174;  -fx-border-radius: 40;-fx-background-color: #7E6174; -fx-background-radius: 40");
    }

}
