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
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lk.ijse.gdse.finalproject.db.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private static final double DEFAULT_WIDTH = 1380;
    private static final double DEFAULT_HEIGHT = 775;
    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignin;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void loginOnAction(ActionEvent actionEvent) {
        String userName = txtUserName.getText();
        String userPassword = txtPassword.getText();
        try {
            checkCredential(userName, userPassword);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "OOPS! something went wrong").show();
        }

    }

    private void checkCredential(String userName, String userPassword) throws SQLException, IOException, ClassNotFoundException {
        String sql = "select username, userpassword from signup where username = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, userName);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            String dbPasssword = resultSet.getString(2);

            if(dbPasssword.equals(userPassword)) {
                navigateToTheDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR, "Password is incorrect!").show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "username not found!").show();
        }


    }
    private void navigateToTheDashboard() throws IOException {
        navigateTo("/view/HomePage.fxml");    }

    public void signinOnAction(ActionEvent actionEvent) throws IOException {
        navigateToSingIn("/view/SignIn.fxml");
    }

    public void navigateToSingIn(String fxlmpath) throws IOException {
        mainAnchor.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource(fxlmpath));
        mainAnchor.getChildren().add(load);
    }
    public void navigateTo(String fxlmpath) {
        try {
            mainAnchor.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxlmpath));
            Parent load = loader.load();

            Stage currentStage = (Stage) mainAnchor.getScene().getWindow();

            currentStage.setWidth(DEFAULT_WIDTH);
            currentStage.setHeight(DEFAULT_HEIGHT);

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            currentStage.setX((screenBounds.getWidth() - DEFAULT_WIDTH) / 2);
            currentStage.setY((screenBounds.getHeight() - DEFAULT_HEIGHT) / 2);

            Scene newScene = new Scene(load, DEFAULT_WIDTH, DEFAULT_HEIGHT);
            currentStage.setScene(newScene);
            currentStage.setResizable(false);
            currentStage.show();
        }catch (IOException e){
            new Alert(Alert.AlertType.ERROR,"Fail UI").show();
        }
    }
}
