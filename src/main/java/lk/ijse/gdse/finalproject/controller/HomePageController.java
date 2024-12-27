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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    private static final double DEFAULT_WIDTH = 900;
    private static final double DEFAULT_HEIGHT = 639;
    public Button btnStudents;
    public Button btnBooking;
    public Button btnCourses;
    public Button btnPayment;
    public Button btnInstructors;
    public Button btnLessons;
    public Button btnMaintainers;
    public Button btnVehicles;
    public Button btnSalary;
    public Button btnLogout;
    public Button btnSetting;
    public Label lblHomePage;
    public Rectangle rtgDashboard;
    public Label lblDashboard;
    public Label lblStudents;
    public Label lblInstructors;
    public Label lblBooking;
    public Label lblPayments;
    public Label lblCourses;
    public Label lblLessons;
    public Label lblMaintainer;
    public Label lblVehicles;
    public Label lblSalary;
    public Label lblSetting;
    public Label lblLogout;

    @FXML
    private Button btnDashboard;

    @FXML
    private AnchorPane loardAnchor;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    void dashboardOnAction(ActionEvent event) throws IOException {
        applyButtonStyles(lblDashboard,btnDashboard);
        lblHomePage.setText("DASHBOARD");
        navigateTo("/view/Dashboard.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            navigateTo("/view/Dashboard.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void navigateTo(String fxmlpath) throws IOException {
        loardAnchor.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlpath));
        loardAnchor.getChildren().add(load);
    }

    public void studentsOnAction(ActionEvent actionEvent) throws IOException {
        applyButtonStyles(lblStudents,btnStudents);
        lblHomePage.setText("MANAGE STUDENTS");
        navigateTo("/view/Students.fxml");
    }



    public void coursesOnAction(ActionEvent actionEvent) throws IOException {
        applyButtonStyles(lblCourses,btnCourses);
        lblHomePage.setText("MANAGE COURSES");
        navigateTo("/view/Courses.fxml");

    }



    public void instructorsOnAction(ActionEvent actionEvent) throws IOException {
        applyButtonStyles(lblInstructors,btnInstructors);
        lblHomePage.setText("MANAGE INSTRUCTORS");
        navigateTo("/view/Instructors.fxml");

    }

    public void lessonsOnAction(ActionEvent actionEvent) throws IOException {
        applyButtonStyles(lblLessons,btnLessons);
        lblHomePage.setText("MANAGE LESSONS");
        navigateTo("/view/Lessons.fxml");

    }

    public void maintainersOnAction(ActionEvent actionEvent) throws IOException {
        applyButtonStyles(lblMaintainer,btnMaintainers);
        lblHomePage.setText("MANAGE MAINTAINERS");
        navigateTo("/view/Maintainers.fxml");

    }

    public void vehicleOnAction(ActionEvent actionEvent) throws IOException {
        applyButtonStyles(lblVehicles,btnVehicles);
        lblHomePage.setText("MANAGE VEHICLES");
        navigateTo("/view/Vehicle.fxml");

    }

    public void salaryOnAction(ActionEvent actionEvent) throws IOException {
        applyButtonStyles(lblSalary,btnSalary);
        lblHomePage.setText("MANAGE SALARY");
        navigateTo("/view/Salary.fxml");

    }

    public void navigateToLogin(String fxmlpath) throws IOException {
        try {
            mainAnchor.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlpath));
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
    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        applyButtonStyles(lblLogout,btnLogout);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();
        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES){
            navigateToLogin("/view/LogIn.fxml");
        }
    }

    public void settingOnAction(ActionEvent actionEvent) throws IOException {
        applyButtonStyles(lblSetting,btnSetting);
        lblHomePage.setText("SETTING");
        navigateTo("/view/Setting.fxml");
    }

    public void bookingOnAction(ActionEvent actionEvent) throws IOException {
        applyButtonStyles(lblBooking,btnBooking);
        lblHomePage.setText("MANAGE BOOKING");
        navigateTo("/view/Booking.fxml");
    }

    public void paymentsOnAction(ActionEvent actionEvent) throws IOException {
        applyButtonStyles(lblPayments,btnPayment);
        lblHomePage.setText("MANAGE PAYMENTS");
        navigateTo("/view/Payment.fxml");
    }
    private void applyButtonStyles(Label activeLabel, Button activeButton) {
        String inactiveStyle = "-fx-background-color: #ffffff; -fx-background-radius: 45; -fx-text-fill: #330a27;";
        String activeStyle = "-fx-background-color: #9e8496; -fx-background-radius: 45; -fx-text-fill: #330a27;";

        activeLabel.setStyle(activeStyle);
        activeButton.setStyle(activeStyle);

        Label[] labels = {lblDashboard, lblStudents, lblCourses, lblInstructors, lblLessons, lblMaintainer, lblVehicles, lblSalary, lblSetting, lblBooking, lblPayments, lblLogout};
        Button[] buttons = {btnDashboard, btnStudents, btnCourses, btnInstructors, btnLessons, btnMaintainers, btnVehicles, btnSalary, btnSetting, btnBooking, btnPayment, btnLogout};

        for (Label label : labels) {
            if (label != activeLabel) {
                label.setStyle(inactiveStyle);
            }
        }

        for (Button button : buttons) {
            if (button != activeButton) {
                button.setStyle(inactiveStyle);
            }
        }
    }
}
